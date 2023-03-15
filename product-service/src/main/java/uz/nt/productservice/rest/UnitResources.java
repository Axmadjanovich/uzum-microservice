package uz.nt.productservice.rest;

import dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.productservice.dto.UnitDto;
import uz.nt.productservice.service.UnitService;

import java.util.List;

@RestController
@RequestMapping("unit")
@RequiredArgsConstructor
public class UnitResources {
    private final UnitService unitService;

    @Operation(
            summary = "Add new Unit",
            method = "Add new Unit",
            description = "Need to send UnitDto to this endpoint to create new unit",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Unit info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PostMapping
    public ResponseDto<UnitDto> addNewUnit(@RequestBody @Valid UnitDto unitDto){
        return unitService.addNewUnit(unitDto);
    }
    @Operation(
            summary = "Get all Units",
            method = "Get Unit",
            description = "Get all units",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Unit info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Unit not found")}
    )
    @GetMapping
    public ResponseDto<List<UnitDto>> getAllUnits(){
        return unitService.getAllUnits();
    }
    @Operation(
            summary = "Update Unit",
            method = "Update Unit",
            description = "Need to send UnitDto to this endpoint to unit",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Unit info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Unit not found")}
    )
    @PatchMapping
    public ResponseDto<UnitDto> updateUnit(@RequestBody UnitDto unitDto){
        return unitService.updateUnit(unitDto);
    }
    @Operation(
            summary = "Delete",
            method = "Delete unit",
            description = "Delete unit",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Unit info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Unit not found")}
    )
    @DeleteMapping
    public ResponseDto<UnitDto> deleteUnit(@RequestParam  Integer id){
        return unitService.deleteUnit(id);
    }
    @Operation(
            summary = "Get Unit by id",
            method = "Get Unit by id",
            description = "Get unit by id",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Unit info",
                    content = @Content(mediaType = "application/json")),
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Unit not found")}
    )
    @GetMapping("by-id")
    public ResponseDto<UnitDto> getById(@RequestParam Integer id){
        return unitService.getById(id);
    }
}