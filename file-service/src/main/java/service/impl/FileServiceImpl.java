package service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.FileDao;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import repository.FileRepository;
import service.FileService;

import java.io.File;
import dto.ResponseDto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    public ResponseDto<FileDao> saveFile(MultipartFile file) {
        FileDao newFile = new FileDao();
        newFile.setName(file.getOriginalFilename());
        newFile.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        newFile.setCreatedAt(LocalDateTime.now());

        try {
            String filePath;
            Files.copy(file.getInputStream(), Path.of(filePath = filePath("upload",newFile.getExtension())));
            newFile.setUrl(filePath);
            fileRepository.save(newFile);

            return ResponseDto.<FileDao>builder()
                    .data(newFile)
                    .message("OK")
                    .success(true)
                    .build();
        } catch (IOException e) {
            log.error("Error while saving file: {}", e.getMessage());
            return ResponseDto.<FileDao>builder()
                    .code(2)
                    .data(newFile)
                    .message("Error while saving file: " + e.getMessage())
                    .build();
        }
    }

    public static synchronized String filePath(String folder, String ext){
        LocalDate localDate = LocalDate.now();
        String path = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        File file = new File(folder + "/" + path);
        if (!file.exists()){
            file.mkdirs();
        }
        return file.getPath() + "\\"+ System.currentTimeMillis() + ext;
    }
}
