package uz.nt.userservice.service.impl;


import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import uz.nt.userservice.client.EmailClient;
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
import java.util.Random;

import static validator.AppStatusMessages.*;
import static validator.AppStatusCodes.*;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper userMapper;

    private final EmailClient emailClient;
    private final UserVerificationRepository userVerificationRepository;

    private String code;

    @Override
    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Optional<Users> firstByEmail = usersRepository.findFirstByEmail(dto.getEmail());

        if (firstByEmail.isEmpty()) {
            code = getCode();
            if (emailClient.sendEmail(dto.getEmail(), code).isSuccess()) {
                usersRepository.save(userMapper.toEntity(dto));
                userVerificationRepository.save(new UserVerification(dto.getEmail(), code));

                return ResponseDto.<UsersDto>builder()
                        .message("Verification code has just been sent")
                        .data(dto)
                        .code(OK_CODE)
                        .success(true)
                        .build();
            }

                return ResponseDto.<UsersDto>builder()
                        .code(UNEXPECTED_ERROR_CODE)
                        .data(dto)
                        .message("Failure in connecting with email service")
                        .build();
        }
        else {
            if (firstByEmail.isPresent() && firstByEmail.get().getEnabled()){
                return ResponseDto.<UsersDto>builder()
                        .code(UNEXPECTED_ERROR_CODE)
                        .message("User has already been registered")
                        .build();
            }
        }
        return ResponseDto.<UsersDto>builder()
                .code(UNEXPECTED_ERROR_CODE)
                .message("Verify your email address with the following link")
                .build();

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

        if (userFromPSQL.isEmpty() && userFromRedis.isEmpty()) {
            return ResponseDto.<Void>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        if (userFromPSQL.isPresent() && userFromPSQL.get().getEnabled()) {
            return ResponseDto.<Void>builder()
                    .code(UNEXPECTED_ERROR_CODE)
                    .message(DUPLICATE_ERROR)
                    .build();
        }

        if (userFromRedis.isEmpty() && userFromPSQL.get().getEnabled() == false) {
            return resendCode(email);
        }

        if (!userFromRedis.get().getCode().equals(code)) {
            return ResponseDto.<Void>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message("Provided code is not valid!")
                    .build();
        }

        if (usersRepository.setUserTrueByEmail(email) == 0) {
            return ResponseDto.<Void>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR)
                    .build();
        }
        userVerificationRepository.delete(new UserVerification(email, code));
        return ResponseDto.<Void>builder()
                .code(OK_CODE)
                .message("Successfully verified")
                .build();

    }
    public ResponseDto<Void> resendCode(String email) {

        Optional<UserVerification> userFromRedis = userVerificationRepository.findById(email);
        Optional<Users> userFromPSQL = usersRepository.findFirstByEmail(email);

        // umuman register qimagan
        if (userFromPSQL.isEmpty() && userFromRedis.isEmpty()) {
            return ResponseDto.<Void>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        // Databaseda bor va true
        if (userFromPSQL.isPresent() && userFromPSQL.get().getEnabled()) {
            return ResponseDto.<Void>builder()
                    .code(UNEXPECTED_ERROR_CODE)
                    .message(DUPLICATE_ERROR)
                    .build();
        }

        // Databaseda bor va enabled false lekin redisda yo'q
        if (userFromPSQL.isPresent() && userFromPSQL.get().getEnabled() == false && userFromRedis.isEmpty()) {
            if (emailClient.sendEmail(email, code).isSuccess()) {
                userVerificationRepository.save(new UserVerification(email, code));
                return ResponseDto.<Void>builder()
                        .message("Email verification has just been resent")
                        .code(OK_CODE)
                        .build();
            }
        }
        return ResponseDto.<Void>builder()
                .code(UNEXPECTED_ERROR_CODE)
                .message("Could not resend the verification email")
                .build();
    }

    public String getCode() {

        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";


        String values = Capital_chars + Small_chars + numbers;

        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            code += values.charAt(random.nextInt(values.length()));
        }
        return code;
    }
}
