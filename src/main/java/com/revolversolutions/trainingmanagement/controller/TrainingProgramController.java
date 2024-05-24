package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.dto.ResponseTrainingProgramPage;
import com.revolversolutions.trainingmanagement.dto.TrainingProgramDTO;
import com.revolversolutions.trainingmanagement.enums.ProgramType;
import com.revolversolutions.trainingmanagement.serviceImpl.TrainingProgramServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/program")
@AllArgsConstructor
@CrossOrigin(
        origins = {"*"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.DELETE,RequestMethod.POST,RequestMethod.PUT}
)
public class TrainingProgramController {
    private final TrainingProgramServiceImpl trainingProgramService;

@PostMapping
    public ResponseEntity<TrainingProgramDTO> createProgram(@RequestBody TrainingProgramDTO programDTO){
        TrainingProgramDTO createdProgram = trainingProgramService.createTrainingProgram(programDTO);
        return new ResponseEntity<>(createdProgram , HttpStatus.CREATED);
    }
@GetMapping("/{id}")
    public ResponseEntity<TrainingProgramDTO> getProgramById(@PathVariable("id") Long id){
        TrainingProgramDTO programDTO = trainingProgramService.getTrainingProgramById(id);
        return new ResponseEntity<>(programDTO , HttpStatus.OK);
    }
@GetMapping
    public ResponseEntity<ResponseTrainingProgramPage> getPrograms(
        @RequestParam(name = "pageIndex" ,defaultValue = "0" ,required = false) int pageNo,
        @RequestParam(name = "pageSize" ,defaultValue = "20" ,required = false) int pageSize,
        @RequestParam(name = "sortBy" ,defaultValue = "id" ,required = false) String sortBy,
        @RequestParam(name = "sortDir" ,defaultValue = "asc" ,required = false) String sortDir,
        @RequestParam(name = "term" ,defaultValue = "" ,required = false) String term,
        @RequestParam(name = "programType" ,required = false) String programType

){
    ResponseTrainingProgramPage page = trainingProgramService.getAllTrainingPrograms(pageNo,pageSize,sortBy,sortDir,term , programType);
    return new ResponseEntity<>(page,HttpStatus.OK);
    }
@PutMapping("/{id}")
    public ResponseEntity<TrainingProgramDTO> updateProgram(
        @Valid @RequestBody TrainingProgramDTO programDTO,
        @PathVariable("id") Long id
    ){
    TrainingProgramDTO updatedProgram = trainingProgramService.updateTrainingProgram(id,programDTO);
    return new ResponseEntity<>(updatedProgram , HttpStatus.OK);
    }
@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable("id") Long id){
        trainingProgramService.deleteTrainingProgram(id);
        return ResponseEntity.noContent().build();
    }
@DeleteMapping
public ResponseEntity<Void> deleteTrainingPrograms(@RequestBody List<Long> ids){
    trainingProgramService.deleteTrainingProgramsByIds(ids);
    return ResponseEntity.noContent().build();
}
}
