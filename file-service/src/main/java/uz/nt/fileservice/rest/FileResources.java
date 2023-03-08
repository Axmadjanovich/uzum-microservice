package uz.nt.fileservice.rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.fileservice.service.Fileservices;

@RestController
@RequestMapping("file")
@RequiredArgsConstructor
public class FileResources {

    private final Fileservices fileservices;

    @PostMapping
    public ResponseDto<Integer> uploadFile(@RequestBody MultipartFile file){
        return fileservices.fileUpload(file);
    }

}
