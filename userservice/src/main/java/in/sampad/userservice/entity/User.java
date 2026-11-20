package in.sampad.userservice.entity;

import in.sampad.userservice.domain.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal walletBalance=BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private UserStatus status=UserStatus.ACTIVE;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
