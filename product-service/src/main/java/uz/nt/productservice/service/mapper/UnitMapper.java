package uz.nt.productservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.productservice.dto.UnitDto;
import uz.nt.productservice.models.Units;

@Mapper(componentModel = "spring")
public abstract class UnitMapper {
    public abstract UnitDto toDto(Units units);
    public abstract Units toEntity(UnitDto unitDto);
}
