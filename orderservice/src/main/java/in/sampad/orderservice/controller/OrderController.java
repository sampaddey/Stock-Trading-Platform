package in.sampad.orderservice.controller;

import in.sampad.orderservice.dto.OrderRequest;
import in.sampad.orderservice.entity.Order;
import in.sampad.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/place-order")
    public ResponseEntity<?>placeOrder(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody OrderRequest request
            ){
        request.setUserId(userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.placeOrder(request));
    }
    @GetMapping("/get-order")
    public ResponseEntity<?>getOrder(
            @PathVariable String orderId,
            @RequestHeader("X-User-Id") String userId
    ){
        Order order=orderService.getOrder(orderId);

        if(!order.getUserId().equals(userId)){
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(order);
    }
    @GetMapping("/my-orders")
    public ResponseEntity<?>getMyOrders(
            @RequestHeader("X-User-Id") String userId
    ){
        return ResponseEntity.ok(orderService.getMyOrder(userId));
    }

}
