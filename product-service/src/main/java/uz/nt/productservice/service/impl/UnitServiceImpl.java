package uz.nt.productservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.productservice.dto.UnitDto;
import uz.nt.productservice.repository.UnitRepository;
import uz.nt.productservice.service.UnitService;
import uz.nt.productservice.service.mapper.UnitMapper;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import java.util.List;

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
    public ResponseDto<UnitDto> updateUnit(UnitDto unitDto) {
        return null;
    }
}
