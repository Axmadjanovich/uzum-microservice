package uz.nt.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.productservice.models.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
