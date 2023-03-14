package uz.nt.salesservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.salesservice.dto.SalesDto;
import uz.nt.salesservice.model.Sales;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    @Mapping(target = "expirationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    SalesDto toDto(Sales sales);

    @Mapping(target = "expirationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Sales toEntity(SalesDto dto);

}
