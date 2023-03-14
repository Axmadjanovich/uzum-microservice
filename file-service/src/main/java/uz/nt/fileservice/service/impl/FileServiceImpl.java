package uz.nt.fileservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.fileservice.exceptions.ExcelWriterException;
import uz.nt.fileservice.exceptions.FileConvertingException;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements Fileservices {

    private final FileRepository fileRepository;

    private final ExcelWriter excelWriter;

    public String filePath(String folder, String ext, boolean uuidActive) {
        java.io.File file = new java.io.File("upload" + "/" + folder + "/");
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
        try {
            excelWriter.writeHeaderLine();
            excelWriter.writeDataLines(productDtoList);
            excelWriter.workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
            excelWriter.workbook.close();
        } catch (Exception e) {
            throw new ExcelWriterException(e.getMessage());
        }
    }

    @Override
    public ResponseDto<Integer> fileUpload(MultipartFile file) {
        File fileEntity = new File();
        fileEntity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        fileEntity.setCreatedAt(LocalDateTime.now());

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = null;
        try {
            futures = executorService.invokeAll(
                    Arrays.asList(
                            () -> saveLargeSize(file, fileEntity.getExt()),
                            () -> saveMediumSize(file, fileEntity.getExt()),
                            () -> saveSmallSize(file, fileEntity.getExt())
                    )
            );
        } catch (InterruptedException e) {
            throw new FileConvertingException("File converting exception: " + e.getMessage());
        }

        if (futures == null) {
            throw new FileConvertingException("Could not convert file");
        }

        futures.stream().map(result -> {
            try {
                return result.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new FileConvertingException(e.getMessage());
            }
        }).forEach(r -> {
            if (r.substring(14, 19).equalsIgnoreCase("LARGE")) {
                fileEntity.setPathLarge(r);
            } else if (r.substring(14, 20).equalsIgnoreCase("MEDIUM")) {
                fileEntity.setPathMedium(r);
            } else if (r.substring(14, 19).equalsIgnoreCase("SMALL")) {
                fileEntity.setPathSmall(r);
            }
        });

        executorService.shutdown();

        try {
            File savedFile = fileRepository.save(fileEntity);

            return ResponseDto.<Integer>builder()
                    .data(savedFile.getId())
                    .message("OK")
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<Integer>builder()
                    .code(AppStatusCodes.DATABASE_ERROR_CODE)
                    .message(AppStatusMessages.DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }

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
        String imagePath = "";

        Optional<File> optional = fileRepository.findById(fileId);

        if (optional.isEmpty()) {
            return ResponseDto.<byte[]>builder()
                    .message(AppStatusMessages.NOT_FOUND)
                    .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                    .build();
        }

        if (size.equalsIgnoreCase("LARGE")) {
            imagePath = optional.get().getPathLarge();
        }
        if (size.equalsIgnoreCase("MEDIUM")) {
            imagePath = optional.get().getPathMedium();
        }
        if (size.equalsIgnoreCase("SMALL")) {
            imagePath = optional.get().getPathSmall();
        }

        byte[] file = new FileInputStream(imagePath).readAllBytes();

        return ResponseDto.<byte[]>builder()
                .message(AppStatusMessages.OK)
                .code(AppStatusCodes.OK_CODE)
                .data(file)
                .success(true)
                .build();
    }
}
