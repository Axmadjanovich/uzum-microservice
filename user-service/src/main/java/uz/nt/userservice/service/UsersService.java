package uz.nt.userservice.service;

import dto.ResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.userservice.dto.UsersDto;

public interface UsersService {
    public ResponseDto<UsersDto> addUser(UsersDto dto);
    public ResponseDto<UsersDto> updateUser(UsersDto usersDto);
    public ResponseDto<EntityModel<UsersDto>> getUserByPhoneNumber(String phoneNumber);
    public ResponseDto<UsersDto> getById(Integer id);
    public ResponseDto<Void> verify(String email, String code);
    public ResponseDto<Void> resendCode(String email);

}