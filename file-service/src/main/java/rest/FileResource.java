package rest;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import model.FileDao;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.impl.FileServiceImpl;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileResource {

    private final FileServiceImpl fileService;

    @PostMapping
    public ResponseDto<FileDao> uploadImage(@RequestBody MultipartFile file){
        return fileService.saveFile(file);
    }
    @GetMapping
    public String send(){
        return "Hello";
    }
}
