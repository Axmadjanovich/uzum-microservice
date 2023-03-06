package uz.nt.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.nt.productservice.models.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product t where (t.category_id, t.price) in (select p.category_id, max(p.price) from product p\n" +
            "group by p.category_id)",
            nativeQuery = true)
    List<Product> getExpensiveProducts();

    @Query(value = "select t from Product t where (t.categoryId, t.price) in (select p.categoryId, max(p.price) from Product p\n" +
            "group by p.categoryId)")
    List<Product> getExpensiveProducts2();

    @Query(name = "findProductById")
    List<Product> findProductById(@Param("id") Integer id,
                                  @Param("name") String name,
                                  @Param("amount") Integer amount,
                                  @Param("price") Integer price);

    List<Product> findAllByAmountLessThanEqual(Integer amount);

}
