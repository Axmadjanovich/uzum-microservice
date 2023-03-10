package uz.nt.salesservice.rest;

import dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.salesservice.dto.SalesDto;
import uz.nt.salesservice.service.SalesService;

import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesResources {

    private final SalesService salesService;

    @Operation(method = "Add new Sales",
                description = "Enter SalesDto here to create a new sales",
                summary = "You can create new sales in this method")
    @PostMapping
    public ResponseDto<SalesDto> addSales(@Valid @RequestBody SalesDto salesDto){
        return salesService.addSales(salesDto);
    }

    @PutMapping
    public ResponseDto<SalesDto> update(@RequestBody SalesDto salesDto){
        return salesService.update(salesDto);
    }
    @GetMapping("/all")
    public ResponseDto<List<SalesDto>> getAllSales(){
        return salesService.getAllSales();
    }
    @GetMapping()
    public ResponseDto<List<SalesDto>> getExpiredOneDay(){
        return salesService.getExpiredOneDay();
    }

    @GetMapping("/{id}")
    public ResponseDto<SalesDto> getById(@PathVariable Integer id){
        return salesService.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<SalesDto> deleteById(@PathVariable Integer id){
        return  salesService.deleteById(id);
    }

    @DeleteMapping
    public ResponseDto<List<SalesDto>> deleteAll(){
        return salesService.deleteAll();
    }
}
