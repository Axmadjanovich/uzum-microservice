package uz.nt.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import validator.AppStatusMessages;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Integer id;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String phoneNumber;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String firstName;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String lastName;
    private String middleName;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    @Email(message = AppStatusMessages.NOT_VALID_EMAIL)
    private String email;
    private String gender;
    private Date birthDate;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String password;
    private String role;
}
