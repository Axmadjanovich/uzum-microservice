package uz.nt.fileservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.fileservice.model.File;
import uz.nt.fileservice.repository.FileRepository;
import uz.nt.fileservice.service.Fileservices;
import uz.nt.productservice.dto.ProductDto;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements Fileservices {

    private final FileRepository fileRepository;

    private final ExcelWriter excelWriter;
    @Override
    public ResponseDto<Integer> fileUpload(MultipartFile file) {
        File fileEntity = new File();
        fileEntity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        fileEntity.setCreatedAt(LocalDateTime.now());

        try {
            String filePath;
            Files.copy(file.getInputStream(), Path.of(filePath = filePath("images",fileEntity.getExt())));
            fileEntity.setPath(filePath);
            File savedFile = fileRepository.save(fileEntity);

            return ResponseDto.<Integer>builder()
                    .data(savedFile.getId())
                    .message("OK")
                    .success(true)
                    .build();
        } catch (IOException e) {
            log.error("Error while saving file: {}", e.getMessage());
            return ResponseDto.<Integer>builder()
                    .code(2)
                    .message("Error while saving file: " + e.getMessage())
                    .build();
        }
    }

    public static String filePath(String folder,String ext){
        LocalDate localDate = LocalDate.now();
        String path = localDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        java.io.File file = new java.io.File("upload/"+ folder + "/"+ path);
        if (!file.exists()){
            file.mkdirs();
        }
        String uuid = UUID.randomUUID().toString();
        return file.getPath() + "\\"+ uuid + ext;
    }

    @Override
    public void reportProducts(List<ProductDto> productDtoList) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(filePath("product-report", ".xlsx"));
        excelWriter.writeHeaderLine();
        excelWriter.writeDataLines(productDtoList);
        excelWriter.workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        excelWriter.workbook.close();

    }
}
