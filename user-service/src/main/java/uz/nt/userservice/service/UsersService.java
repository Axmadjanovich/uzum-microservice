package uz.nt.userservice.service;

import dto.ResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.userservice.dto.UsersDto;
import uz.nt.userservice.exceptions.DatabaseConnectionException;
import uz.nt.userservice.exceptions.EmailServiceConnectionException;

import java.net.ConnectException;
import java.util.List;

public interface UsersService {
    ResponseDto<UsersDto> addUser(UsersDto dto) throws ConnectException, EmailServiceConnectionException, DatabaseConnectionException;

    ResponseDto<UsersDto> updateUser(UsersDto usersDto);

    ResponseDto<EntityModel<UsersDto>> getUserByPhoneNumber(String phoneNumber);

    ResponseDto<UsersDto> getById(Integer id);

    ResponseDto<Void> verify(String email, String code) throws DatabaseConnectionException;

    ResponseDto<Void> resendCode(String email) throws DatabaseConnectionException;

    ResponseDto<List<UsersDto>> getAllActiveUsers() throws DatabaseConnectionException;
}