package uz.nt.salesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.salesservice.model.Sales;

import java.util.Optional;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {
    Optional<Sales> findFirstById(Integer id);
}
