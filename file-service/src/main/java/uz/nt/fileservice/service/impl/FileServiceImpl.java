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
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static String filePath(String folder, String ext, boolean uuidActive) {
        java.io.File file = new java.io.File("upload"+ "/" + folder + "/");
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName;
        if (uuidActive) {
            fileName = UUID.randomUUID().toString();
        } else {
            fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return file.getPath() + "/" + fileName + ext;
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
    public ResponseDto<Integer> fileUpload(MultipartFile file) {
        File fileEntity = new File();
        fileEntity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        fileEntity.setCreatedAt(LocalDateTime.now());

        fileEntity.setPathlarge(saveLargeSize(file, fileEntity.getExt()));
        fileEntity.setPathmedium(saveMediumSize(file, fileEntity.getExt()));
        fileEntity.setPathsmall(saveSmallSize(file, fileEntity.getExt()));

        File savedFile = fileRepository.save(fileEntity);

        return ResponseDto.<Integer>builder()
                .data(savedFile.getId())
                .message("OK")
                .success(true)
                .build();

    }

    private String saveLargeSize(MultipartFile file, String ext) {
        String filePathLarge;
        try {
            Files.copy(file.getInputStream(), Path.of(filePathLarge = filePath("images/large", ext, true)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathLarge;
    }

    private String saveMediumSize(MultipartFile file, String ext) {
        String filePathMedium;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImage1 = resizeImage(bufferedImage, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
            ImageIO.write(bufferedImage1, ext.substring(1), new java.io.File(filePathMedium = filePath("images/medium", ext, true)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathMedium;
    }

    private String saveSmallSize(MultipartFile file, String ext) {
        String filePathSmall;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImage1 = resizeImage(bufferedImage, bufferedImage.getWidth() / 2 / 2, bufferedImage.getHeight() / 2 / 2);
            ImageIO.write(bufferedImage1, ext.substring(1), new java.io.File(filePathSmall = filePath("images/small", ext, true)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathSmall;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        return resizedImage;
    }

    @Override
    public ResponseDto<byte[]> getFileById(Integer fileId, String size) throws IOException {
        if (fileId == null || size == null) {
            return ResponseDto.<byte[]>builder()
                    .message(AppStatusMessages.NULL_VALUE)
                    .code(AppStatusCodes.VALIDATION_ERROR_CODE)
                    .build();
        }
        FileInputStream image = null;

        Optional<File> optional = fileRepository.findById(fileId);

        if (optional.isEmpty()) {
            return ResponseDto.<byte[]>builder()
                    .message(AppStatusMessages.NOT_FOUND)
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .build();
        }
        if (size.toUpperCase().equals("LARGE")){
            String pathlarge = optional.get().getPathlarge();
             image = new FileInputStream(pathlarge);
        }
        if (size.toUpperCase().equals("MEDIUM")) {
            String pathmedium = optional.get().getPathmedium();
             image = new FileInputStream(pathmedium);
        }
        if (size.toUpperCase().equals("SMALL")) {
            String pathsmall = optional.get().getPathsmall();
             image = new FileInputStream(pathsmall);
        }


        byte[] file = image.readAllBytes();

        return ResponseDto.<byte[]>builder()
                .message(AppStatusMessages.OK)
                .code(AppStatusCodes.OK_CODE)
                .data(file)
                .success(true)
                .build();
    }
}
