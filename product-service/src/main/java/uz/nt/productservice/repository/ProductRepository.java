package uz.nt.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.productservice.models.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> getAllByAmountLessThanEqual(int amount);
}
