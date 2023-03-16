package uz.nt.userservice;

import dto.ResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import uz.nt.userservice.dto.UsersDto;
import uz.nt.userservice.service.impl.UsersServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static validator.AppStatusCodes.*;
import static validator.AppStatusMessages.*;

@SpringBootTest
class UserServiceApplicationTests {
    @Autowired
    private UsersServiceImpl usersServiceImpl;

    @Test
    @DisplayName("check getUserByPhoneNumber with valid number")
    void getUserByPhoneNumber() {
        ResponseDto<EntityModel<UsersDto>> userDto = usersServiceImpl.getUserByPhoneNumber("+9981234");

        String firstName = userDto.getData().getContent().getFirstName();
        String lastName = userDto.getData().getContent().getLastName();
        String middleName = userDto.getData().getContent().getMiddleName();
        String email = userDto.getData().getContent().getEmail();
        String gender = userDto.getData().getContent().getGender();

        assertEquals(firstName, "John", "firstName is not John");
        assertEquals(lastName, "Doe", "lastName is not Doe");
        assertEquals(middleName, "Santos", "middleName is not Santos");
        assertEquals(email, "JohnDoe@gmail.com", "email is not JohnDoe@gmail.com");
        assertEquals(gender, "male", "gender is not male");
        assertEquals(userDto.getCode(), OK_CODE, "userDto.getCode() is not equals OK_CODE");
        assertTrue(userDto.isSuccess(), "userDto.isSuccess() is false");
        assertEquals(userDto.getMessage(), OK, "userDto.getMessage() is not equals OK");
    }

    @Test
    @DisplayName("check getUserByPhoneNumber with invalid number")
    void getUserByPhoneNumberWithInvalidPhoneNumber() {
        ResponseDto<EntityModel<UsersDto>> userDto = usersServiceImpl.getUserByPhoneNumber("+777");

        assertNull(userDto.getData(), "userDto.getData is not null");
    }

}
