package uz.nt.salesservice.rest;

import dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            method = "Add new Sales",
            description = "Enter SalesDto here to create a new sales",
            summary = "You can create new sales in this method",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Sales info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PostMapping
    public ResponseDto<SalesDto> addSales(@Valid @RequestBody SalesDto salesDto){
        return salesService.addSales(salesDto);
    }

    @Operation(
            method = "Update Sales",
            description = "The entered Sales can be updated by id",
            summary = "Update Sales",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Sales info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Sales not found")}
    )
    @PatchMapping
    public ResponseDto<SalesDto> update(@RequestBody SalesDto salesDto){
        return salesService.update(salesDto);
    }
    @Operation(
            method = "Get all Sales",
            description = "All Sales",
            summary = "All Sales",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Sales info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Sales not found")}
    )
    @GetMapping("/all")
    public ResponseDto<List<SalesDto>> getAllSales(){
        return salesService.getAllSales();
    }
    @Operation(
            method = "Get all expired one day Sales",
            description = "All expired one day Sales",
            summary = "All expired one day Sales",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Sales info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Sales not found")}
    )
    @GetMapping("/expired-one-day")
    public ResponseDto<List<SalesDto>> getExpiredOneDay(){
        return salesService.getExpiredOneDay();
    }

    @Operation(
            summary = "Get Sales by id",
            method = "Get Sales by id",
            description = "Get Sales by id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Sales info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Sales not found")}
    )
    @GetMapping("/{id}")
    public ResponseDto<SalesDto> getById(@PathVariable Integer id){
        return salesService.getById(id);
    }

    @Operation(
            method = "Delete Sales by id",
            description = "Delete Sales by id",
            summary = "Delete Sales by id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Sales info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Sales not found")}
    )
    @DeleteMapping("/{id}")
    public ResponseDto<SalesDto> deleteById(@PathVariable Integer id){
        return  salesService.deleteById(id);
    }

    @Operation(
            method = "Delete all Sales",
            description = "Delete all Sales",
            summary = "Delete all Sales",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Sales info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @DeleteMapping
    public ResponseDto<List<SalesDto>> deleteAll(){
        return salesService.deleteAll();
    }
}
