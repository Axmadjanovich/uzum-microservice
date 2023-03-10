package uz.nt.userservice.service.impl;

import dto.ErrorDto;
import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import uz.nt.userservice.client.EmailClient;
import uz.nt.userservice.config.PasswordGenerator;
import uz.nt.userservice.dto.UsersDto;
import uz.nt.userservice.model.UserVerification;
import uz.nt.userservice.model.Users;
import uz.nt.userservice.repository.UserVerificationRepository;
import uz.nt.userservice.repository.UsersRepository;
import uz.nt.userservice.service.UsersService;
import uz.nt.userservice.service.mapper.UsersMapper;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import java.util.Optional;

import static validator.AppStatusMessages.*;
import static validator.AppStatusCodes.*;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper userMapper;

    private final EmailClient emailClient;
    private final UserVerificationRepository userVerificationRepository;

    private final UserVerification userVerification;
    private final PasswordGenerator passwordGenerator;
    private String email;
    private String code;

    @Override
    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Optional<Users> firstByEmail = usersRepository.findFirstByEmail(dto.getEmail());

        if(firstByEmail.isEmpty()){
            email = dto.getEmail();
            code = getCode();
            usersRepository.save(userMapper.toEntity(dto));

            if(emailClient.sentEmail(email,code).isSuccess()){
                userVerification.setEmail(email);
                userVerification.setCode(code);
                userVerificationRepository.save(userVerification);
                return ResponseDto.<UsersDto>builder()
                        .message("Send verification code your email")
                        .data(dto)
                        .code(OK_CODE)
                        .success(true)
                        .build();
            }else{
                return ResponseDto.<UsersDto>builder()
                        .code(UNEXPECTED_ERROR_CODE)
                        .data(dto)
                        .message("Don't send code your email")
                        .build();
            }

        }else{
              if(!firstByEmail.isEmpty() && firstByEmail.get().getEnabled()){
                  return ResponseDto.<UsersDto>builder()
                          .code(UNEXPECTED_ERROR_CODE)
                          .message("This email already exists")
                          .build();
              }else{
                  return ResponseDto.<UsersDto>builder()
                          .code(UNEXPECTED_ERROR_CODE)
                          .message("This email already exists but don't verify")
                          .build();
              }
        }


        //TODO AppMonsters: User emailiga xabar yuborish.


    }

    @Override
    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null) {
            return ResponseDto.<UsersDto>builder()
                    .message("UserID is null")
                    .code(-2)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findById(usersDto.getId());

        if (userOptional.isEmpty()) {
            return ResponseDto.<UsersDto>builder()
                    .message("User with ID " + usersDto.getId() + " is not found")
                    .code(-1)
                    .data(usersDto)
                    .build();
        }
        Users user = userOptional.get();
        if (usersDto.getGender() != null) {
            user.setGender(usersDto.getGender());
        }
        if (usersDto.getEmail() != null) {
            user.setEmail(usersDto.getEmail());
        }
        if (usersDto.getLastName() != null) {
            user.setLastName(usersDto.getLastName());
        }
        if (usersDto.getBirthDate() != null) {
            user.setBirthDate(usersDto.getBirthDate());
        }
        if (usersDto.getMiddleName() != null) {
            user.setMiddleName(usersDto.getMiddleName());
        }
        if (usersDto.getFirstName() != null) {
            user.setFirstName(usersDto.getFirstName());
        }
        if (usersDto.getPhoneNumber() != null) {
            user.setPhoneNumber(usersDto.getPhoneNumber());
        }

        try {
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .success(true)
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .code(1)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<EntityModel<UsersDto>> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumber(phoneNumber)
                .map(u -> ResponseDto.<EntityModel<UsersDto>>builder()
                        .data(EntityModel.of(userMapper.toDto(u),
                                Link.of("http://localhost:9000/user", "edit"),
                                Link.of("http://localhost:9000/user/" + u.getId(), "delete")))
                        .success(true)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<EntityModel<UsersDto>>builder()
                        .message("User with phone number " + phoneNumber + " is not found")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<UsersDto> getById(Integer id) {
        return usersRepository.findById(id)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .success(true)
                        .message(AppStatusMessages.OK)
                        .data(userMapper.toDto(u))
                        .build()
                ).orElse(ResponseDto.<UsersDto>builder()
                        .message(NOT_FOUND)
                        .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                        .build());

    }

    @Override
    public ResponseDto<Void> verify(String email, String code) {
        Optional<UserVerification> userFromRedis = userVerificationRepository.findById(email);
        Optional<Users> userFromPSQL = usersRepository.findFirstByEmail(email);

        if (userFromPSQL.isEmpty() && userFromRedis.isEmpty()){
            return ResponseDto.<Void>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        if (userFromPSQL.isPresent() && userFromPSQL.get().getEnabled()){
            return ResponseDto.<Void>builder()
                    .code(UNEXPECTED_ERROR_CODE)
                    .message(DUPLICATE_ERROR)
                    .build();
        }

        if (userFromRedis.isEmpty() && userFromPSQL.get().getEnabled() == false){
            resendCode(email);
        }

        if (!userFromRedis.get().getCode().equals(code)){
            return ResponseDto.<Void>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message("Provided code is not valid!")
                    .build();
        }

        try {
            usersRepository.setUserTrueByEmail(email);
            return ResponseDto.<Void>builder()
                    .code(OK_CODE)
                    .message("Successfully verified")
                    .build();
        }catch (Exception e){
            return ResponseDto.<Void>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR)
                    .build();
        }
    }

    private String getCode(){
       return passwordGenerator.geek_Password();
    }

    public ResponseDto<Void> resendCode(String email) {
        Optional<Users> optional = usersRepository.findFirstByEmail(email);
        if(!optional.isEmpty()){
            if(userVerificationRepository.existsById(email)){
                return ResponseDto.<Void>builder()
                        .message("Already send code your email")
                        .code(UNEXPECTED_ERROR_CODE)
                        .build();
            }else {
                if (emailClient.sentEmail(email, code).isSuccess()) {
                    this.email = email;
                    code = getCode();
                    userVerification.setEmail(this.email);
                    userVerification.setCode(code);
                    userVerificationRepository.save(userVerification);
                    return ResponseDto.<Void>builder()
                            .message("Send verification code your email")
                            .code(OK_CODE)
                            .build();
                }
                else{
                    return ResponseDto.<Void>builder()
                            .code(UNEXPECTED_ERROR_CODE)
                            .message("Don't send code your email")
                            .build();
                }
            }
        }else{
            return ResponseDto.<Void>builder()
                    .message(NOT_FOUND)
                    .code(UNEXPECTED_ERROR_CODE)
                    .build();
        }

    }
}
