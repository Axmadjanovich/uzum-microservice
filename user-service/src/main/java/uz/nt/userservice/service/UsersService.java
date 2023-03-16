package uz.nt.userservice.service;

import dto.ResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.userservice.dto.UsersDto;
import uz.nt.userservice.exceptions.DatabaseConnectionException;
import uz.nt.userservice.exceptions.EmailServiceConnectionException;

import java.net.ConnectException;

public interface UsersService {
    public ResponseDto<UsersDto> addUser(UsersDto dto) throws ConnectException, EmailServiceConnectionException, DatabaseConnectionException;
    public ResponseDto<UsersDto> updateUser(UsersDto usersDto);
    public ResponseDto<EntityModel<UsersDto>> getUserByPhoneNumber(String phoneNumber);
    public ResponseDto<UsersDto> getById(Integer id);
    public ResponseDto<Void> verify(String email, String code) throws DatabaseConnectionException;
    public ResponseDto<Void> resendCode(String email) throws DatabaseConnectionException;

}