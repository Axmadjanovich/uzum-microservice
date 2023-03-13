package uz.nt.salesservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.nt.salesservice.dto.SalesDto;
import uz.nt.salesservice.model.Sales;
import uz.nt.salesservice.repository.SalesRepository;
import uz.nt.salesservice.service.SalesService;
import uz.nt.salesservice.service.mapper.SalesMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public ResponseDto<List<SalesDto>> getAllSales() {
        return ResponseDto.<List<SalesDto>>builder()
                .data(salesRepository.findAll().stream().map(salesMapper::toDto).toList())
                .build();
    }

//    @Override
//    public ResponseDto<Page<EntityModel<SalesDto>>> geAllSales(Integer page, Integer size) {
//        long count = salesRepository.count();
//
//        PageRequest pageRequest = PageRequest.of(
//                (count / size) <= page ?
//                        (count % size == 0 ? (int) (count / size) - 1
//                                : (int) (count / size))
//                        : page,
//                size,
//                Sort.by("expressionDate").descending()
//        );
//        Page<EntityModel<SalesDto>> salesDto = salesRepository.findAll(pageRequest)
//                .map(s -> {
//                    EntityModel<SalesDto> entityModel = EntityModel.of(salesMapper.toDto(s));
//                    try{
//                        entityModel.add(linkTo(SalesRepository.class
//                                .getDeclaredMethod("getBYId", Integer.class, HttpServletRequest.class))
//                                .withSelfRel()
//                                .expand());
//                    } catch (NoSuchMethodException e) {
//                        throw new RuntimeException(e);
//                    }
//                    return entityModel;
//                });
//
//        return ResponseDto.<List<SalesDto>>builder()
//                .message(OK)
//                .code(OK_CODE)
//                .success(true)
//                .data(salesDto)
//                .build();
//    }

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
        salesRepository.deleteById(id);
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
        List<Sales> salesList = salesRepository.findAll().stream().toList();
        salesRepository.deleteAll();
        return ResponseDto.<List<SalesDto>>builder()
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .data(salesList.stream().map(salesMapper::toDto).toList())
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
            sales.setExpirationDate(salesDto.getExpirationDate());
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
        }catch (Exception e){
            return ResponseDto.<SalesDto>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<SalesDto>> getExpiredOneDay() {
        Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        return ResponseDto.<List<SalesDto>>builder()
                .data(salesRepository.findAllByExpirationDateIsBefore(date).stream().map(salesMapper::toDto).toList())
                .build();
    }
}
