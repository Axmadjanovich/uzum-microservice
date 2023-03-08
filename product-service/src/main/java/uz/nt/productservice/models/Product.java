package uz.nt.productservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(query = "select p from Product p where coalesce(:id, id) = id " +
        "and coalesce(:name, name) = name " +
        "and coalesce(:price, price) = price " +
        "and coalesce(:amount, amount) = amount", name = "findProductById")
public class Product {
    @Id
    @GeneratedValue(generator = "productIdSeq")
    @SequenceGenerator(name = "productIdSeq", sequenceName = "product_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer price;
    private Integer amount;
    private String description;
    private Integer fileId;
    private Integer categoryId;
    private Boolean isAvailable;
}
//1. Product qo'shish
//2. Bor productni o'zgartirish
//3. Umumiy productlar ro'yxatini qaytarish
