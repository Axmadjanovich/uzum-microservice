package uz.nt.productservice.service;

import dto.ResponseDto;
import uz.nt.productservice.dto.UnitDto;

import java.util.List;

public interface UnitService {
    ResponseDto<UnitDto> addNewUnit(UnitDto unitDto);

    ResponseDto<List<UnitDto>> getAllUnits();

    ResponseDto<UnitDto> updateUnit(UnitDto unitDto);

    ResponseDto<UnitDto> deleteUnit(Integer id);

    ResponseDto<UnitDto> getById(Integer id);
}
