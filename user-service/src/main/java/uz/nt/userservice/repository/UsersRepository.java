package uz.nt.userservice.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.model.Users;

import java.util.Optional;

@Repository

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findFirstByPhoneNumber(String phoneNumber);
    Optional<Users> findFirstByEmail(String email);
    @Modifying
    @Transactional
    @Query(value = "update users set enabled = true where email = :email", nativeQuery = true)
    int setUserTrueByEmail(@Param("email") String email);
}
