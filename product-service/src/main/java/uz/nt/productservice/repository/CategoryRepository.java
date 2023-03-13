package uz.nt.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.productservice.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
