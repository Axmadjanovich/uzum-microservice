package uz.nt.fileservice.rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.fileservice.service.Fileservices;

import java.io.IOException;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileResources {

    private final Fileservices fileservices;

    //TODO har kuni 00:00 da ishlab, product-service dan kam qolgan mahsulotlar ro'yxatini tortib keladi va
    // uploads papkani ichida yangi product-reports papka ochib, shuni ichiga har kunlik Excel fayllar kun bo'yicha nomlanib
    // tashlab boriladi

    //TODO rasmni 3 xil kattalikda saqlash
//    @PostMapping
//    public ResponseDto<Integer> uploadFile(@RequestPart("file") MultipartFile file){
//        return fileservices.fileUpload(file);
//    }

    @GetMapping
    public ResponseDto<byte[]> getFileById(@RequestParam Integer fileId) throws IOException {
        return fileservices.getFileById(fileId);
    }


    @PostMapping()
    public ResponseDto<Integer> uploadFile(@RequestPart("file") MultipartFile file){
        return fileservices.fileUpload2(file);
    }

}