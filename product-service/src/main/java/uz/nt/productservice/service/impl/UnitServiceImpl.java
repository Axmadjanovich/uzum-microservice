package uz.nt.productservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.productservice.dto.UnitDto;
import uz.nt.productservice.models.Units;
import uz.nt.productservice.repository.UnitRepository;
import uz.nt.productservice.service.UnitService;
import uz.nt.productservice.service.mapper.UnitMapper;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import java.util.List;
import java.util.Optional;

import static validator.AppStatusCodes.*;
import static validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;
    @Override
    public ResponseDto<UnitDto> addNewUnit(UnitDto unitDto) {
        return ResponseDto.<UnitDto>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(unitMapper.toDto(unitRepository.save(unitMapper.toEntity(unitDto))))
                .build();
    }

    @Override
    public ResponseDto<List<UnitDto>> getAllUnits() {
        return ResponseDto.<List<UnitDto>>builder()
                .code(OK_CODE)
                .message(OK)
                .data(unitRepository.findAll().stream().map(unitMapper::toDto).toList())
                .success(true)
                .build();
    }

    @Override
    public ResponseDto<UnitDto> updateUnit(UnitDto dto) {
        if(dto.getId() == null){
            return ResponseDto.<UnitDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(dto)
                    .build();
        }

        Optional<Units> optionalUnits = unitRepository.findById(dto.getId());
        if (optionalUnits.isEmpty()){
            return ResponseDto.<UnitDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .success(false)
                    .data(dto)
                    .build();
        }
        Units units = optionalUnits.get();
        units.setId(dto.getId());
        if (dto.getName() != null){
            units.setName(dto.getName());
        }
        if (dto.getDescription() != null){
            units.setDescription(dto.getDescription());
        }
        if (dto.getShortName() != null){
            units.setShortName(dto.getShortName());
        }
        try {
            unitRepository.save(units);

            return ResponseDto.<UnitDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(unitMapper.toDto(units))
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<UnitDto>builder()
                    .data(unitMapper.toDto(units))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UnitDto> deleteUnit(Integer id) {
        return unitRepository.findById(id).map(units -> {
            unitRepository.delete(units);
            return ResponseDto.<UnitDto>builder()
                    .code(OK_CODE)
                    .message("Unit with ID " + id + " is deleted")
                    .success(true)
                    .data(unitMapper.toDto(units))
                    .build();
        }).orElse(
                ResponseDto.<UnitDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .success(false)
                        .build());
    }

    @Override
    public ResponseDto<UnitDto> getById(Integer id) {
        return unitRepository.findById(id).map(u -> ResponseDto.<UnitDto>builder()
                .code(OK_CODE)
                .message(OK)
                .success(true)
                .data(unitMapper.toDto(u))
                .build()).orElse(
                ResponseDto.<UnitDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .success(false)
                        .build()
        );
    }

}
