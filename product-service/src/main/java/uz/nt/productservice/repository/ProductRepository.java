package uz.nt.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.nt.productservice.dto.ProductDto;
import uz.nt.productservice.models.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> getAllByAmountLessThanEqual(int amount);

    @Query(value = "select * from product t where (t.category_id, t.price) in (select p.category_id, max(p.price) from product p\n" +
            "group by p.category_id)",
            nativeQuery = true)
    List<Product> getExpensiveProducts();

}
