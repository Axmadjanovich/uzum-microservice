package uz.nt.userservice.service;

import dto.ResponseDto;
import org.springframework.hateoas.EntityModel;
import uz.nt.userservice.dto.UsersDto;

public interface UsersService {
    public ResponseDto<UsersDto> addUser(UsersDto dto);
    public ResponseDto<UsersDto> updateUser(UsersDto usersDto);
    public ResponseDto<EntityModel<UsersDto>> getUserByPhoneNumber(String phoneNumber);
    public ResponseDto<UsersDto> getById(Integer id);

}