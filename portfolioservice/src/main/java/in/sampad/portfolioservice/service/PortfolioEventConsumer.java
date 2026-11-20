package in.sampad.portfolioservice.service;

import in.sampad.portfolioservice.entity.Holdings;
import in.sampad.portfolioservice.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PortfolioEventConsumer {

    private final PortfolioRepository repository;
    @KafkaListener(topics = "order.executed")
    public void consumeOrderExecuted(
            @Payload Map<String,Object>payload
            ){
        try {
            String userId=(String) payload.get("userId");
            String symbol=(String) payload.get("symbol");
            String type=(String) payload.get("type");

            int quantity=Integer.parseInt(
                    payload.get("quantity").toString()
            );

            BigDecimal price=new BigDecimal(
                    payload.get("price").toString()
            );

            if("BUY".equals(type)){
                addHolding(userId,symbol,quantity,price);
            } else if ("SELL".equals(type)) {
                removeHolding(userId,symbol,quantity);
            }

        }catch (Exception e){
            log.error("Error updating portfolio::"+e.getMessage());
        }
    }

    private void addHolding(String userId,String symbol,int quantity, BigDecimal price){
        Holdings holdings=repository.findByUserIdAndSymbol(userId,symbol)
                .orElse(null);

        if(holdings==null){
            holdings=new Holdings();

            holdings.setUserId(userId);
            holdings.setSymbol(symbol);
            holdings.setCompanyName(symbol);
            holdings.setQuantity(quantity);
            holdings.setAverageBuyPrice(price);
            holdings.setTotalInvested(
                    price.multiply(BigDecimal.valueOf(quantity))
            );
        }else{

            int newQuantity=holdings.getQuantity()+quantity;

            BigDecimal newInvested=holdings.getTotalInvested()
                    .add(price.multiply(BigDecimal.valueOf(quantity)));

            BigDecimal newAvgPrice=newInvested
                    .divide(BigDecimal.valueOf(newQuantity),2,
                            RoundingMode.HALF_UP);

            holdings.setQuantity(newQuantity);
            holdings.setTotalInvested(newInvested);
            holdings.setAverageBuyPrice(newAvgPrice);

        }

        repository.save(holdings);

        log.info("------------------New holding saved--------------");
    }

    private void removeHolding(String userId,String symbol, int quantity){
        Holdings holdings=repository.findByUserIdAndSymbol(userId,symbol)
                .orElseThrow(()->new RuntimeException("Holding not found"));


        if(holdings.getQuantity()<quantity){
            throw new RuntimeException("Insufficient share to sell");
        }

        int newQuantity=holdings.getQuantity()-quantity;

        if(newQuantity==0){
            repository.delete(holdings);
            log.info("Holding remove successfully");
        }else {
            BigDecimal newInvested=holdings.getAverageBuyPrice()
                    .multiply(BigDecimal.valueOf(newQuantity));

            holdings.setQuantity(newQuantity);
            holdings.setTotalInvested(newInvested);

            repository.save(holdings);

            log.info("Holding reduced successfully");
        }


    }
}
