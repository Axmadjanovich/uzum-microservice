package uz.nt.fileservice.rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.fileservice.service.Fileservices;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileResources {

    private final Fileservices fileservices;

    @PostMapping
    public ResponseDto<Integer> uploadFile(@RequestPart("file") MultipartFile file){
        return fileservices.fileUpload(file);
    }

}
