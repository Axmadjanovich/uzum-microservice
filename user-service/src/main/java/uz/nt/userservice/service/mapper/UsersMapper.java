package uz.nt.userservice.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.userservice.dto.UsersDto;
import uz.nt.userservice.model.Users;

@Mapper(componentModel = "spring")
public abstract class UsersMapper implements CommonMapper<UsersDto, Users>{
    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "enabled", expression = "java(false)")
    @Mapping(target = "role", ignore = true)
    public abstract Users toEntity(UsersDto dto);

    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    public abstract UsersDto toDto(Users entity);
}
