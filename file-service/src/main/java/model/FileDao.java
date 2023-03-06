package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class FileDao {
    @Id
    @GeneratedValue(generator = "imageIdSeq")
    @SequenceGenerator(name = "imageIdSeq", sequenceName = "image_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String url;
    private String extension;
    private LocalDateTime createdAt;
}
