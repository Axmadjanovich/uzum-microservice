package uz.nt.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.productservice.models.Units;

public interface UnitRepository extends JpaRepository<Units, Integer> {
}
