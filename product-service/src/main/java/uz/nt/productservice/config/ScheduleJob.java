package uz.nt.productservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import uz.nt.productservice.clients.FileClient;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.repository.ProductRepository;
import uz.nt.productservice.service.mapper.ProductMapper;

import java.util.List;

@EnableAsync
@RequiredArgsConstructor
public class ScheduleJob {

    private final ProductRepository productRepository;
    private final FileClient fileClient;

    private final ProductMapper productMapper;
    @Scheduled(cron = "00 00 * * *")
    private void getLessProducts(){

        List<ProductDto> productDtoList = productRepository.getAllByAmountLessThanEqual(10).stream().map(productMapper::toDto).toList();
        fileClient.sendProductsWithLessAmount(productDtoList);
    }

}
