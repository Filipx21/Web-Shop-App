package pl.filip.tosql.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,
        mappedBy = "order")
    private List<ProductInOrder> products = new ArrayList<>();

    @OneToOne
    @NotNull
    private User user;

    @Min(0)
    @NotEmpty
    private BigDecimal orderAmount;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate createTime;

}
