package uz.nt.fileservice.config;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uz.nt.fileservice.client.ProductClient;
import uz.nt.fileservice.service.impl.FileServiceImpl;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.models.Product;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleJob {

    private final FileServiceImpl fileService;

    private final ProductClient productClient;

    @Scheduled(cron = "0 0 0 * * *")
    public void report() throws IOException {
        ResponseDto<List<ProductDto>> products = productClient.getProductsForReport();
        fileService.reportProducts(products.getData());
    }
}
