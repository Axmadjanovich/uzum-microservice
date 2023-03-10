package uz.nt.userservice.repository;

import org.springframework.data.repository.CrudRepository;
import uz.nt.userservice.model.UserVerification;

public interface UserVerificationRepository extends CrudRepository<UserVerification, String> {
}
