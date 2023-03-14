package uz.nt.productservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(query = "select p from Product p where coalesce(:id, id) = id " +
        "and coalesce(:name, name) = name " +
        "and coalesce(:price, price) = price " +
        "and coalesce(:amount, amount) = amount " +
        "and coalesce(:description, description) = description " +
        "and coalesce(:categoryId, category.id) = category.id ", name = "findProductById")
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
    @ManyToOne
    private Category category;
    private Boolean isAvailable;
    @ManyToOne
    private Units units;
}

