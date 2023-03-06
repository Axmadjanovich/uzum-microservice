package uz.nt.fileservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.fileservice.dto.FileDto;
import uz.nt.fileservice.model.File;
import uz.nt.fileservice.repository.FileRepository;
import uz.nt.fileservice.service.Fileservices;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements Fileservices {

    private final FileRepository fileRepository;
    @Override
    public ResponseDto<String> fileUpload(MultipartFile file) {
        File fileEntity = new File();
        fileEntity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        fileEntity.setCreatedAt(LocalDateTime.now());

        try {
            String filePath;
            Files.copy(file.getInputStream(), Path.of(filePath = filePath("upload",fileEntity.getExt())));
            fileEntity.setPath(filePath);
            fileRepository.save(fileEntity);

            return ResponseDto.<String>builder()
                    .data(filePath)
                    .message("OK")
                    .success(true)
                    .build();
        } catch (IOException e) {
            log.error("Error while saving file: {}", e.getMessage());
            return ResponseDto.<String>builder()
                    .code(2)
                    .message("Error while saving file: " + e.getMessage())
                    .build();
        }
    }
    public static synchronized String filePath(String folder,String ext){
        LocalDate localDate = LocalDate.now();
        String path = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        java.io.File file = new java.io.File(folder + "/"+ path);
        if (!file.exists()){
            file.mkdirs();
        }
        return file.getPath() + "\\"+ System.currentTimeMillis() + ext;
    }
}
