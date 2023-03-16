package uz.nt.userservice.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.model.Users;

import java.util.Optional;
import java.util.stream.Stream;

@Repository

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findFirstByPhoneNumber(String phoneNumber);
    Optional<Users> findFirstByEmail(String email);

    Stream<Users> findAllByEnabledIsFalse();
}
