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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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

    @Override
    public ResponseDto<Integer> imageUpload(MultipartFile file) throws IOException {
        File lowImageEntity = new File();
        File medium ImageEntity = new File();
        File lowImageEntity = new File();

        fileEntity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        fileEntity.setCreatedAt(LocalDateTime.now());

        BufferedImage bufferedImage= ImageIO.read((java.io.File) file);

        BufferedImage low = resizeImage(bufferedImage, bufferedImage.getWidth()/3, bufferedImage.getHeight()/3);
        BufferedImage medium = resizeImage(bufferedImage, bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
        BufferedImage high = resizeImage(bufferedImage, bufferedImage.getWidth(), bufferedImage.getHeight());

        ImageIO.write(low, "gif", new java.io.File("/home/karimjon/Downloads/image.gif"));
        ImageIO.write(medium, "jpg", new java.io.File("/home/karimjon/Downloads/image.png"));
        ImageIO.write(high, "bmp", new java.io.File("/home/karimjon/Downloads/image.bmp"));

        try {
            String filePath;
            Files.copy(file.getInputStream(), Path.of(filePath = filePath("images/",fileEntity.getExt())));
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

    public static BufferedImage resizeImage (BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        return resizedImage;
    }
}
