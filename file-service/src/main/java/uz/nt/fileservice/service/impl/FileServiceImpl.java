package uz.nt.fileservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ooxml.POIXMLProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.fileservice.model.File;
import uz.nt.fileservice.repository.FileRepository;
import uz.nt.fileservice.service.Fileservices;
import uz.nt.productservice.dto.ProductDto;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
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
            Files.copy(file.getInputStream(), Path.of(filePath = filePath("images", fileEntity.getExt(), true)));
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

    public static String filePath(String folder, String ext, boolean uuidActive) {
        java.io.File file = new java.io.File("upload/" + folder + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName;
        if (uuidActive) {
            fileName = UUID.randomUUID().toString();
        } else {
            fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return file.getPath() + "\\" + fileName + ext;
    }

    @Override
    public void reportProducts(List<ProductDto> productDtoList) throws IOException {

        FileOutputStream outputStream = new FileOutputStream(filePath("product-report", ".xlsx", false));

        excelWriter.writeHeaderLine();
        excelWriter.writeDataLines(productDtoList);
        excelWriter.workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
        excelWriter.workbook.close();

    }

    @Override
    public ResponseDto<Integer> fileUpload2(MultipartFile file) {
        File fileEntity = new File();
        fileEntity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        fileEntity.setCreatedAt(LocalDateTime.now());

        try {
            String filePath;
            Files.copy(file.getInputStream(), Path.of(filePath = filePath("images/large", fileEntity.getExt(), true)));
            fileEntity.setPath(filePath);
            File savedFile = fileRepository.save(fileEntity);

            saveMediumSize(file);

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

    @Override
    public ResponseDto<byte[]> getFileById(Integer fileId) throws IOException {
        if (fileId == null){
            return ResponseDto.<byte[]>builder()
                    .message(AppStatusMessages.NULL_VALUE)
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .build();
        }
        Optional<File> optional = fileRepository.findById(fileId);

        if (optional.isEmpty()){
            return ResponseDto.<byte[]>builder()
                    .message(AppStatusMessages.NOT_FOUND)
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .build();
        }

        FileInputStream image = new FileInputStream(optional.get().getPath());

        byte[] file = image.readAllBytes();

        return ResponseDto.<byte[]>builder()
                .message(AppStatusMessages.OK)
                .code(AppStatusCodes.OK_CODE)
                .data(file)
                .success(true)
                .build();
    }

    private void saveMediumSize(MultipartFile file) throws IOException {
        File medium = new File();
        medium.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        medium.setCreatedAt(LocalDateTime.now());
//
//        BufferedImage image = ImageIO.read(file.getInputStream());
//
//        BufferedImage resized = resize(image, 50, 50);
//
        String filePathMedium = filePath("images/medium", medium.getExt(), true);
//
//        java.io.File output = new java.io.File(filePathMedium);
//
//        boolean write = ImageIO.write(resized, medium.getExt(), output);
//
//        medium.setPath(filePathMedium);
//
//        fileRepository.save(medium);
        BufferedImage bufferedImage= ImageIO.read(file.getInputStream());
        BufferedImage bufferedImage1 = resizeImage(bufferedImage, 100, 100);
        ImageIO.write(bufferedImage1, "gif", new java.io.File(filePathMedium));
        ImageIO.write(bufferedImage1, "jpg", new java.io.File(filePathMedium));
        ImageIO.write(bufferedImage1, "bmp", new java.io.File(filePathMedium));
        ImageIO.write(bufferedImage1, "jpeg", new java.io.File(filePathMedium));

        medium.setPath(filePathMedium);

        fileRepository.save(medium);

    }
    private  BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    private BufferedImage resizeImage (BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        return resizedImage;
    }
}
