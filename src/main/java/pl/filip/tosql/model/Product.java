package pl.filip.tosql.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(max = 500)
    private String descript;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @Min(0)
    private BigDecimal cost;

    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    private Producer producer;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate create;

}
