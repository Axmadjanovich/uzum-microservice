package uz.nt.salesservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sales {

    @Id
    @GeneratedValue(generator = "sales_seq")
    @SequenceGenerator(name = "sales_seq", sequenceName = "sales_id_seq", allocationSize = 1)
    private Integer id;

    private Integer productId;

    private Integer price;

    private LocalDate expressionDate;

}
