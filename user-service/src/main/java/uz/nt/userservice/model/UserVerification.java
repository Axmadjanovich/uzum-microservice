package uz.nt.userservice.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@RedisHash(timeToLive = 60 * 60 * 2)
public class UserVerification {
    @Id
    private String email;
    private String code;
}
