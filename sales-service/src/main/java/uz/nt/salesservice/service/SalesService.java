package uz.nt.salesservice.service;

import dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import uz.nt.salesservice.dto.SalesDto;

import java.util.List;
@Service
public interface SalesService {
    ResponseDto<SalesDto> addSales(SalesDto salesDto);

    ResponseDto<Page<EntityModel<SalesDto>>> getAllSales(Integer page, Integer size);

    ResponseDto<SalesDto> getById(Integer id);

    ResponseDto<SalesDto> deleteById(Integer id);

    ResponseDto<List<SalesDto>> deleteAll();

    ResponseDto<SalesDto> update(SalesDto salesDto);

    ResponseDto<List<SalesDto>> getExpiredOneDay();
}
