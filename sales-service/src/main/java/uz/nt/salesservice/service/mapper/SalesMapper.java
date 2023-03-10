package uz.nt.salesservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.salesservice.dto.SalesDto;
import uz.nt.salesservice.model.Sales;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    @Mapping(target = "expressionDate", dateFormat = "dd.MM.yyyy")
    SalesDto toDto(Sales sales);

    @Mapping(target = "expressionDate", dateFormat = "dd.MM.yyyy")
    Sales toEntity(SalesDto dto);

}
