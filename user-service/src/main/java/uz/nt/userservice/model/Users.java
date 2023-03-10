package uz.nt.userservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "users")
@Getter
@Setter
@NamedQuery(query = "update Users set enabled = true where email = :email", name = "setEnabledTrue")
public class Users {
    @Id
    @GeneratedValue(generator = "userIdSequence")
    @SequenceGenerator(name = "userIdSequence", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String gender;
    private Date birthDate;
    private String password;
    private Boolean enabled = false;
    @Column(columnDefinition = "text default 'USER'")
    private String role = "USER";
}
