package com.revolversolutions.trainingmanagement.serviceImpl;

import com.revolversolutions.trainingmanagement.entity.FileDB;
import com.revolversolutions.trainingmanagement.repository.FileDBRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service @Slf4j
public class FileStorageService {

    private final FileDBRepository fileDBRepository;

    @Autowired
    public FileStorageService(FileDBRepository fileDBRepository) {
        this.fileDBRepository = fileDBRepository;
    }

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = FileDB.builder()
                .name(fileName)
                .type(file.getContentType())
                .data(file.getBytes())
                .build();
        log.info("FileDB: {}", fileDB);
        return fileDBRepository.save(fileDB);
    }

    public FileDB getFile(String id) {
        log.info("FileDB: {}", fileDBRepository.findById(id).get());
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        log.info("Fetching all files");
        return fileDBRepository.findAll().stream();
    }
}
