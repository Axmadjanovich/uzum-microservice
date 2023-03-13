package uz.nt.productservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Units {
    @Id
    @GeneratedValue(generator = "unit_id_seq")
    @SequenceGenerator(name = "unit_id_seq", sequenceName = "unit_id_seq", allocationSize = 1)
    private Integer id;
    private String shortName;
    private String name;
    private String description;
}
