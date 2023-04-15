package uz.nt.productservice.clients;

import dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.productservice.dto.ProductDto;

import java.util.List;

public class FallbackService implements FileClient {
    @Override
    public ResponseDto<Integer> uploadFile(MultipartFile file) {
        return ResponseDto.<Integer>builder()
                .data(0)
                .message("Fallback response")
                .build();
    }

    @Override
    public ResponseDto<Integer> sendProductsWithLessAmount(List<ProductDto> productList) {
        return null;
    }
}
