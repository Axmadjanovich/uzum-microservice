package uz.nt.salesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.salesservice.model.Sales;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {
    Optional<Sales> findFirstById(Integer id);
    List<Sales> findAllByExpirationDateIsBefore(LocalDateTime localDateTime);
}
