package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.dto.ResponseTrainingProgramPage;
import com.revolversolutions.trainingmanagement.dto.TrainingProgramDTO;
import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.entity.TrainingProgram;
import com.revolversolutions.trainingmanagement.exception.ResourceNotFoundException;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TrainingProgramServiceImpl implements TrainingProgramService {

    private final TrainingProgramRepository trainingProgramRepository;
    private final TrainingProgramDTOMapper trainingProgramMapper;
    private final FileStorageService storageService;

    @Override
    public TrainingProgramDTO createTrainingProgram(TrainingProgramDTO programDTO) {
        TrainingProgram trainingProgram = trainingProgramMapper.toEntity(programDTO);
        TrainingProgram createdProgram = trainingProgramRepository.save(trainingProgram);
        log.info("Training Program created successfully");
        return trainingProgramMapper.toDto(createdProgram);
    }

    @Override
    public TrainingProgramDTO getTrainingProgramById(String id) {
        TrainingProgram trainingProgram = getProgramEntityById(id);
        log.info("Fetching Program by id: {} " , id);
        return trainingProgramMapper.toDto(trainingProgram);
    }

    @Override
    public TrainingProgram getProgramEntityById(String programId) {
        TrainingProgram trainingProgram = trainingProgramRepository.findByProgramId(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with id: " + programId));
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
    public TrainingProgramDTO updateTrainingProgram(String programId , TrainingProgramDTO programDTO) {
        TrainingProgram programToUpdate = getProgramEntityById(programId);
        programToUpdate.setTitle(programDTO.getTitle());
        programToUpdate.setContent(programDTO.getContent());
        programToUpdate.setDescription(programDTO.getDescription());
        programToUpdate.setDuration(programDTO.getDuration());
        programToUpdate.setFees(programDTO.getFees());
        programToUpdate.setProgramType(programDTO.getProgramType());

        TrainingProgram updatedProgram = trainingProgramRepository.save(programToUpdate);
        log.info("Program with id: {} has been updated successfully", programId);
        return trainingProgramMapper.toDto(updatedProgram);
    }

    @Override
    public void deleteTrainingProgram(String programId) {
    TrainingProgram programToDelete = getProgramEntityById(programId);
    trainingProgramRepository.delete(programToDelete);
        log.info("Program with id: {} has been deleted successfully", programId);
    }

    @Transactional
    @Override
    public void deleteTrainingProgramsByIds(List<Long> ids) {
        trainingProgramRepository.deleteByProgramIdIn(ids);
    }

    @Override
    public List<FileDB> getProgramImages(String programId){
        TrainingProgram program = trainingProgramRepository.findByProgramId(programId)
                .orElseThrow(()->new ResourceNotFoundException("Program not found with id " + programId));
        return program.getProgramImages();
    }

    @Override
    public void uploadProgramImages(String userId, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("Uploaded files list is empty or null");
        }

        TrainingProgram program = trainingProgramRepository.findByProgramId(userId)
                .orElseThrow(() -> new RuntimeException("Program not found with userId " + userId));

        List<FileDB> programImages = program.getProgramImages();

        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("One of the uploaded files is empty or null");
            }
            try {
                FileDB fileDB = storageService.store(file);
                if (fileDB == null) {
                    throw new RuntimeException("Error storing file, fileDB is null");
                }
                programImages.add(fileDB);
            } catch (IOException e) {
                throw new RuntimeException("Error storing file", e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Uploaded file is not a valid image", e);
            }
        }

        trainingProgramRepository.save(program);
    }

    @Override
    public void uploadProgramImage(String programId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty or null");
        }

        TrainingProgram program = trainingProgramRepository.findByProgramId(programId)
                .orElseThrow(() -> new RuntimeException("Program not found with userId " + programId));

        try {
            FileDB fileDB = storageService.store(file);
            if (fileDB == null) {
                throw new RuntimeException("Error storing file, fileDB is null");
            }

            program.getProgramImages().add(fileDB);
            trainingProgramRepository.save(program);

        } catch (IOException e) {

            throw new RuntimeException("Error storing file", e);

        } catch (IllegalArgumentException e) {

            throw new RuntimeException("Uploaded file is not a valid image", e);
        }
    }
}
