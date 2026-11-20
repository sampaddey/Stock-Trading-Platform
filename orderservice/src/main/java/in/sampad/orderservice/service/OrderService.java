package in.sampad.orderservice.service;

import in.sampad.orderservice.client.MarketDataClient;
import in.sampad.orderservice.client.UserServiceClient;
import in.sampad.orderservice.domain.OrderStatus;
import in.sampad.orderservice.domain.OrderType;
import in.sampad.orderservice.dto.OrderRequest;
import in.sampad.orderservice.entity.Order;
import in.sampad.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MarketDataClient marketDataClient;
    private final UserServiceClient userServiceClient;

    private static final String ORDER_EXECUTED_TOPIC="order.executed";
    private static final String ORDER_FAILED_TOPIC="order.failed";

    private final KafkaTemplate<String,Object>kafkaTemplate;

    public Order placeOrder(OrderRequest request){
        log.info("Placing the order");

        Map<String,Object>priceData=marketDataClient.getStockPrice(request.getSymbol());

        BigDecimal currentPrice=new BigDecimal(
                priceData.get("price").toString()
        );

        BigDecimal totalAmount=currentPrice.multiply(
                BigDecimal.valueOf(request.getQuantity())
        );

        Order order=new Order();

        order.setUserId(request.getUserId());
        order.setSymbol(request.getSymbol());
        order.setType(request.getType());
        order.setQuantity(request.getQuantity());
        order.setPrice(currentPrice);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder=orderRepository.save(order);

        try {
            if(request.getType()== OrderType.BUY){
                userServiceClient.deductFund(
                        request.getUserId(), totalAmount
                );
            }else{
                userServiceClient.creditFund(
                        request.getUserId(), totalAmount);
            }

            savedOrder.setStatus(OrderStatus.EXECUTED);
            savedOrder.setExecutedAt(LocalDateTime.now());
            orderRepository.save(savedOrder);

            publishOrderEvent(ORDER_EXECUTED_TOPIC,savedOrder,null);
        }catch (Exception e){

            savedOrder.setStatus(OrderStatus.FAILED);
            savedOrder.setFailureReason(e.getMessage());
            orderRepository.save(savedOrder);

            log.error("Order is failed::"+e.getMessage());

            publishOrderEvent(ORDER_FAILED_TOPIC,savedOrder,e.getMessage());

        }



        return savedOrder;


    }

    public Order getOrder(String orderId){
        return orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Oder not found"));
    }

    public List<Order>getMyOrder(String userId){
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }



    private void publishOrderEvent(String topic, Order savedOrder, String reason) {


        Map<String,Object>orderEvent=new HashMap<>();

        orderEvent.put("orderId",savedOrder.getId());
        orderEvent.put("userId",savedOrder.getUserId());
        orderEvent.put("symbol",savedOrder.getSymbol());
        orderEvent.put("type",savedOrder.getType().name());
        orderEvent.put("quantity",savedOrder.getQuantity());
        orderEvent.put("price",savedOrder.getPrice());
        orderEvent.put("totalAmount",savedOrder.getPrice());
        orderEvent.put("status",savedOrder.getStatus().name());
        if(reason!=null){
            orderEvent.put("reason",reason);
        }

        kafkaTemplate.send(topic,savedOrder.getId(),orderEvent);

        log.info("Event published for order done");

    }
}
