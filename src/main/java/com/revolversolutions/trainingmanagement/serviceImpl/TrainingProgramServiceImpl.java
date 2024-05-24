package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.dto.ResponseTrainingProgramPage;
import com.revolversolutions.trainingmanagement.dto.ReviewDTO;
import com.revolversolutions.trainingmanagement.dto.TrainingProgramDTO;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.enums.ProgramType;
import com.revolversolutions.trainingmanagement.mapper.TrainingProgramDTOMapper;
import com.revolversolutions.trainingmanagement.repository.TrainingProgramRepository;
import com.revolversolutions.trainingmanagement.service.TrainingProgramService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TrainingProgramServiceImpl implements TrainingProgramService {
    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainingProgramDTOMapper trainingProgramMapper;
    @Override
    public TrainingProgramDTO createTrainingProgram(TrainingProgramDTO programDTO) {
        TrainingProgram trainingProgram = trainingProgramMapper.toEntity(programDTO);
        TrainingProgram createdProgram = trainingProgramRepository.save(trainingProgram);
        log.info("Training Program created successfully");
        return trainingProgramMapper.toDto(createdProgram);
    }

    @Override
    public TrainingProgramDTO getTrainingProgramById(Long id) {
        TrainingProgram trainingProgram = getProgramEntityById(id);
        log.info("Fetching Program by id: {} " , id);
        return trainingProgramMapper.toDto(trainingProgram);
    }

    @Override
    public TrainingProgram getProgramEntityById(Long id) {
        TrainingProgram trainingProgram = trainingProgramRepository.findById(id)
                .orElse(null);
        return trainingProgram;
    }

    @Override
    public ResponseTrainingProgramPage getAllTrainingPrograms(int pageNo, int pageSize, String sortBy, String sortDir , String search , String programType) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Specification<TrainingProgram> spec = new Specification<TrainingProgram>() {
            @Override
            public Predicate toPredicate(Root<TrainingProgram> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction(); // Default predicate, always true

                if (search != null && !search.trim().isEmpty()) {
                    Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title").as(String.class)), "%" + search.toLowerCase() + "%");
                    Predicate descriptionPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description").as(String.class)), "%" + search.toLowerCase() + "%");
                    Predicate contentPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("content").as(String.class)), "%" + search.toLowerCase() + "%");
                    predicate = criteriaBuilder.or(titlePredicate, descriptionPredicate, contentPredicate);
                }

                if (programType != null && !programType.trim().isEmpty()) {
                    Predicate programTypePredicate = criteriaBuilder.equal(root.get("programType"), programType);
                    predicate = criteriaBuilder.and(predicate, programTypePredicate);
                }

                return predicate;
            }
        };

        Page<TrainingProgram> trainingProgramPage = trainingProgramRepository.findAll(spec , pageable);
        List<TrainingProgram> trainingProgramList = trainingProgramPage.getContent();
        List<TrainingProgramDTO> trainingProgramResponseList = trainingProgramList.stream()
                .map((board) -> trainingProgramMapper.toDto(board))
                .collect(Collectors.toList());
        ResponseTrainingProgramPage responseTrainingProgramPage = new ResponseTrainingProgramPage(
                trainingProgramResponseList,
                trainingProgramPage.getNumber(),
                trainingProgramPage.getSize(),
                trainingProgramPage.getTotalElements(),
                trainingProgramPage.getTotalPages(),
                trainingProgramPage.isLast()
        );
        log.info("Fetching Training Programs");
        return responseTrainingProgramPage;
    }

    @Override
    public TrainingProgramDTO updateTrainingProgram(Long id , TrainingProgramDTO programDTO) {
        TrainingProgram programToUpdate = getProgramEntityById(id);
        programToUpdate.setTitle(programDTO.getTitle());
        programToUpdate.setContent(programDTO.getContent());
        programToUpdate.setDescription(programDTO.getDescription());
        programToUpdate.setDuration(programDTO.getDuration());
        programToUpdate.setFees(programDTO.getFees());

        TrainingProgram updatedProgram = trainingProgramRepository.save(programToUpdate);
        log.info("Program with id: {} has been updated successfully", id);
        return trainingProgramMapper.toDto(updatedProgram);
    }

    @Override
    public void deleteTrainingProgram(Long id) {
    TrainingProgram programToDelete = getProgramEntityById(id);
    trainingProgramRepository.delete(programToDelete);
        log.info("Program with id: {} has been deleted successfully", id);
    }

    @Transactional
    @Override
    public void deleteTrainingProgramsByIds(List<Long> ids) {
        trainingProgramRepository.deleteByIdIn(ids);
    }

}
