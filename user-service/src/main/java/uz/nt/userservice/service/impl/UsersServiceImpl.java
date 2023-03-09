package uz.nt.userservice.service.impl;

import dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import uz.nt.userservice.dto.UsersDto;
import uz.nt.userservice.model.Users;
import uz.nt.userservice.repository.UsersRepository;
import uz.nt.userservice.service.UsersService;
import uz.nt.userservice.service.mapper.UsersMapper;
import validator.AppStatusCodes;
import validator.AppStatusMessages;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final UsersMapper userMapper;

    @Override
    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Users users = userMapper.toEntity(dto);
        usersRepository.save(users);
        //TODO AppMonsters: User emailiga xabar yuborish.

        return ResponseDto.<UsersDto>builder()
                .success(true)
                .data(userMapper.toDto(users))
                .message("OK")
                .build();
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
                        .message(AppStatusMessages.NOT_FOUND)
                        .code(AppStatusCodes.NOT_FOUND_ERROR_CODE)
                        .build());

    }
}
