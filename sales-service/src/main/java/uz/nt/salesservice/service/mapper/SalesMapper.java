package uz.nt.salesservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.salesservice.dto.SalesDto;
import uz.nt.salesservice.model.Sales;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    SalesDto toDto(Sales sales);

    Sales toEntity(SalesDto dto);

}
