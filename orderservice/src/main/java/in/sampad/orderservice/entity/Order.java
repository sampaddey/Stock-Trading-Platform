package in.sampad.orderservice.entity;

import in.sampad.orderservice.domain.OrderStatus;
import in.sampad.orderservice.domain.OrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String symbol;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private BigDecimal price;

    private BigDecimal totalAmount;

    private String failureReason;
    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime executedAt;


}
