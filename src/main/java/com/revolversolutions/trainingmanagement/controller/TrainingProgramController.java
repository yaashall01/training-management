package com.revolversolutions.trainingmanagement.controller;

import com.revolversolutions.trainingmanagement.dto.ResponseTrainingProgramPage;
import com.revolversolutions.trainingmanagement.dto.TrainingProgramDTO;
import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.enums.ProgramType;
import com.revolversolutions.trainingmanagement.service.TrainingProgramService;
import com.revolversolutions.trainingmanagement.serviceImpl.TrainingProgramServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/program")
@AllArgsConstructor
public class TrainingProgramController {
    private final TrainingProgramService trainingProgramService;

    @PostMapping
    public ResponseEntity<TrainingProgramDTO> createProgram(@RequestBody TrainingProgramDTO programDTO){
        TrainingProgramDTO createdProgram = trainingProgramService.createTrainingProgram(programDTO);
        return new ResponseEntity<>(createdProgram , HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrainingProgramDTO> getProgramById(@PathVariable("id") String programId){
        TrainingProgramDTO programDTO = trainingProgramService.getTrainingProgramById(programId);
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
        @PathVariable("id") String programId
    ){
        TrainingProgramDTO updatedProgram = trainingProgramService.updateTrainingProgram(programId,programDTO);
        return new ResponseEntity<>(updatedProgram , HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable("id") String programId){
        trainingProgramService.deleteTrainingProgram(programId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteTrainingPrograms(@RequestBody List<Long> ids){
        trainingProgramService.deleteTrainingProgramsByIds(ids);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{programId}/images")
    public ResponseEntity<List<FileDB>> getProgramImages(@PathVariable String programId){
        List<FileDB> images = trainingProgramService.getProgramImages(programId);
        return ResponseEntity.ok(images);
    }
    @PostMapping("upload/{programId}/image")
    public ResponseEntity<String> uploadProgramImage(
            @PathVariable String programId,
            @RequestParam("file") MultipartFile file) {
        try {
            trainingProgramService.uploadProgramImage(programId, file);
            return ResponseEntity.status(HttpStatus.OK).body("Program image uploaded successfully");
        }catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading program image");
        }
    }

    @PostMapping("upload/{programId}/images")
    public ResponseEntity<String> uploadProgramImages(@PathVariable String programId,
                                                      @RequestParam("files") List<MultipartFile> files ){
        try {
            trainingProgramService.uploadProgramImages(programId, files);
            return ResponseEntity.status(HttpStatus.OK).body("Program images uploaded successfully");
        }catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading program image");
        }
    }


}
