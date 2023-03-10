package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.model.Users;

import java.util.Optional;

@Repository

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findFirstByPhoneNumber(String phoneNumber);
    Optional<Users> findFirstByEmail(String email);
    @Query(name = "setEnabledTrue")
    void setUserTrueByEmail(@Param("email") String email);
}
