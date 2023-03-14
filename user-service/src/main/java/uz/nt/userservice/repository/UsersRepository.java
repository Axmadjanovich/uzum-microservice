package uz.nt.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.model.Users;

import java.util.Optional;

@Repository

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findFirstByPhoneNumber(String phoneNumber);
    Optional<Users> findFirstByEmail(String email);
    @Procedure(procedureName = "insert_data")
    void insertRecordsIntoUsers(Integer number);
}
