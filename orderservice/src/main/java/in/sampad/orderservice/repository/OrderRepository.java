package in.sampad.orderservice.repository;

import in.sampad.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findByUserIdOrderByCreatedAtDesc(String userId);
}
