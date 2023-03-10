package uz.nt.orderservice.model;

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
public class OrderItems {
    @Id
    @GeneratedValue(generator = "order_items_id")
    @SequenceGenerator(name = "order_items_id", sequenceName = "order_items_id", allocationSize = 1)
    private Integer id;
    private Integer productId;
    @ManyToOne
    private Order order;
    private Double amount;
}
