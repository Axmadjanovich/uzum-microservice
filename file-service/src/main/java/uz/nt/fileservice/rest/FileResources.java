package uz.nt.fileservice.rest;

import dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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
    @Operation(
            method = "Add new File",
            description = "You should send image(Less than 1.5 mb and bigger than 1000 * 1000).It will save image in 3 different sizes(large,medium,small).",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Save file", content = @Content(mediaType = "application/json"))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PostMapping()
    public ResponseDto<Integer> uploadFile(@RequestPart("file") MultipartFile file){
        return fileservices.fileUpload(file);
    }

    @Operation(
            method = "Add new File",
            description = "You should send image id and image size.It will resend you image if found",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Find file", content = @Content(mediaType = "application/json"))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping
    public ResponseDto<byte[]> getFileById(@RequestParam Integer fileId,@RequestParam String size) throws IOException {
        return fileservices.getFileById(fileId, size);
    }


}