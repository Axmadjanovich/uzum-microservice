package uz.nt.userservice.service;

import dto.ResponseDto;
import org.springframework.hateoas.EntityModel;
import uz.nt.userservice.dto.UsersDto;
import uz.nt.userservice.exceptions.ConnectionException;

import java.net.ConnectException;
import java.util.List;

public interface UsersService {
    ResponseDto<UsersDto> addUser(UsersDto dto) throws ConnectionException;

    ResponseDto<UsersDto> updateUser(UsersDto usersDto);

    ResponseDto<EntityModel<UsersDto>> getUserByPhoneNumber(String phoneNumber);

    ResponseDto<UsersDto> getById(Integer id);

    ResponseDto<Void> verify(String email, String code) throws ConnectionException;

    ResponseDto<Void> resendCode(String email) throws ConnectionException;

    ResponseDto<List<UsersDto>> getAllActiveUsers() throws ConnectionException;
}