package pl.filip.tosql.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String addres;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDateTime create;

    @OneToMany(fetch = FetchType.LAZY,
        mappedBy = "producer")
    private List<Product> product;

}
