package in.sampad.marketdataservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stocks")
public class Stock {
    @Id
    @Column(nullable = false,unique = true)
    private String symbol;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private BigDecimal initialPrice;
    @Column(nullable = false)
    private String exchange;
    @Column(nullable = false)
    private String currency;
    private boolean active=true;
}
