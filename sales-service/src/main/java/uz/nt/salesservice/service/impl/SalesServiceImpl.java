package uz.nt.salesservice.service.impl;

import dto.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import uz.nt.salesservice.dto.SalesDto;
import uz.nt.salesservice.model.Sales;
import uz.nt.salesservice.repository.SalesRepository;
import uz.nt.salesservice.rest.SalesResources;
import uz.nt.salesservice.service.SalesService;
import uz.nt.salesservice.service.mapper.SalesMapper;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLTimeoutException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static validator.AppStatusCodes.*;
import static validator.AppStatusMessages.*;


@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {
    private final SalesMapper salesMapper;
    private final SalesRepository salesRepository;
    @Override
    public ResponseDto<SalesDto> addSales(SalesDto salesDto) {
        return ResponseDto.<SalesDto>builder()
                .code(OK_CODE)
                .success(true)
                .message(OK)
                .data(salesMapper.toDto(salesRepository.save(salesMapper.toEntity(salesDto))))
                .build();
    }

//    @Override
//    public ResponseDto<List<SalesDto>> getAllSales() {
//        return ResponseDto.<List<SalesDto>>builder()
//                .data(salesRepository.findAll().stream().map(salesMapper::toDto).toList())
//                .code(OK_CODE)
//                .message(OK)
//                .success(true)
//                .build();
//    }

    @Override
    public ResponseDto<Page<EntityModel<SalesDto>>> getAllSales(Integer page, Integer size) {
        long count = salesRepository.count();

        PageRequest pageRequest = PageRequest.of(
                (count / size) <= page ?
                        (count % size == 0 ? (int) (count / size) - 1
                                : (int) (count / size))
                        : page,
                size,
                Sort.by("expirationDate").descending()
        );
        Page<EntityModel<SalesDto>> salesDto = salesRepository.findAll(pageRequest)
                .map(s -> {
                    EntityModel<SalesDto> entityModel = EntityModel.of(salesMapper.toDto(s));
                    try{
                        entityModel.add(linkTo(SalesResources.class
                                .getDeclaredMethod("getById", Integer.class))
                                .withSelfRel()
                                .expand(s.getId()));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    return entityModel;
                });

        return ResponseDto.<Page<EntityModel<SalesDto>>>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(salesDto)
                .build();
    }

    @Override
    public ResponseDto<SalesDto> getById(Integer id) {
        return salesRepository.findById(id)
                .map(p-> ResponseDto.<SalesDto>builder()
                            .message(OK)
                            .code(OK_CODE)
                            .success(true)
                            .data(salesMapper.toDto(p))
                        .build())
                .orElse(ResponseDto.<SalesDto>builder()
                            .message(NOT_FOUND)
                            .code(NOT_FOUND_ERROR_CODE)
                        .build());
    }

    @Override
    public ResponseDto<SalesDto> deleteById(Integer id) {
        Optional<Sales> sales = salesRepository.findFirstById(id);
        if(sales.isPresent()){
            salesRepository.deleteById(id);
        }
        return sales.map(
                s -> ResponseDto.<SalesDto>builder()
                            .code(OK_CODE)
                            .message(OK)
                            .data(salesMapper.toDto(s))
                            .success(true)
                        .build()
                ).orElse(ResponseDto.<SalesDto>builder()
                            .message(NOT_FOUND)
                            .code(NOT_FOUND_ERROR_CODE)
                        .build());
    }

    @Override
    public ResponseDto<List<SalesDto>> deleteAll() {
        List<Sales> all = salesRepository.findAll();
        if(all.isEmpty()){
            return ResponseDto.<List<SalesDto>>builder()
                    .message("Sales is empty")
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }
        salesRepository.deleteAll();
        return ResponseDto.<List<SalesDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(all.stream().map(salesMapper::toDto).toList())
                .build();
    }

    @Override
    public ResponseDto<SalesDto> update(SalesDto salesDto) {
        if(salesDto.getId() == null){
            return ResponseDto.<SalesDto>builder()
                    .message("Id is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<Sales> salesOptional = salesRepository.findFirstById(salesDto.getId());

        if(salesOptional.isEmpty()){
            return ResponseDto.<SalesDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }


        Sales sales = salesOptional.get();

        if(salesDto.getExpirationDate() != null){
            sales.setExpirationDate(
                    LocalDateTime.parse(salesDto.getExpirationDate(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if(salesDto.getPrice() != null){
            sales.setPrice(salesDto.getPrice());
        }

        if(salesDto.getProductId() != null){
            sales.setProductId(salesDto.getProductId());
        }


        try{
            salesRepository.save(sales);
            return ResponseDto.<SalesDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .data(salesMapper.toDto(sales))
                    .success(true)
                    .build();
        }catch (RuntimeException e){
            return ResponseDto.<SalesDto>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<SalesDto>> getExpiredOneDay() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return ResponseDto.<List<SalesDto>>builder()
                .data(salesRepository.findAllByExpirationDateIsBefore(localDateTime).stream().map(salesMapper::toDto).toList())
                .build();
    }
}
