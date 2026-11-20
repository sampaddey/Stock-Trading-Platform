package in.sampad.orderservice.dto;

import in.sampad.orderservice.domain.OrderType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private String userId;
    @NotBlank(message = "symbol is required")
    private String symbol;
    @NotNull(message = "order type is required")
    private OrderType type;
    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;

}
