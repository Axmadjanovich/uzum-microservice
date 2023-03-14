package uz.nt.productservice.rest;

import dto.ResponseDto;
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

    @PostMapping
    public ResponseDto<UnitDto> addNewUnit(@RequestBody @Valid UnitDto unitDto){
        return unitService.addNewUnit(unitDto);
    }
    @GetMapping
    public ResponseDto<List<UnitDto>> getAllUnits(){
        return unitService.getAllUnits();
    }
    @PatchMapping
    public ResponseDto<UnitDto> updateUnit(@RequestBody UnitDto unitDto){
        return unitService.updateUnit(unitDto);
    }
    @DeleteMapping
    public ResponseDto<UnitDto> deleteUnit(@RequestParam  Integer id){
        return unitService.deleteUnit(id);
    }

    @GetMapping("by-id")
    public ResponseDto<UnitDto> getById(@RequestParam Integer id){
        return unitService.getById(id);
    }
}
