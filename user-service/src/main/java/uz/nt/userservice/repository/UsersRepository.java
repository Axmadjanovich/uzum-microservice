package uz.nt.userservice.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.userservice.dto.UsersDto;
import uz.nt.userservice.model.Users;

import java.util.Optional;
import java.util.stream.Stream;

@Repository

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findFirstByPhoneNumber(String phoneNumber);
    Optional<Users> findFirstByEmail(String email);
    Stream<Users> findAllByEnabledNull();
//    @Procedure(procedureName = "insert_data")
    @Modifying
    @Transactional
    @Query(value = "call insert_data(:count)", nativeQuery = true)
    int insertRecordsIntoUsers(@Param("count") Integer count);
}
