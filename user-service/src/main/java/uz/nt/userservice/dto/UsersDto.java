package uz.nt.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotNull(message = AppStatusMessages.NULL_VALUE)
    @NotEmpty
    private String phoneNumber;

    @NotNull(message = AppStatusMessages.NULL_VALUE)
    @NotEmpty
    private String firstName;

    @NotEmpty
    @NotNull(message = AppStatusMessages.NULL_VALUE)
    private String lastName;

    private String middleName;

    @NotEmpty
    @NotNull(message = AppStatusMessages.NULL_VALUE)
    @Email(message = AppStatusMessages.NOT_VALID_EMAIL)
    private String email;

    private String gender;
    private Date birthDate;

    @NotNull(message = AppStatusMessages.NULL_VALUE)
    @NotEmpty
    private String password;
    private String role;

}
