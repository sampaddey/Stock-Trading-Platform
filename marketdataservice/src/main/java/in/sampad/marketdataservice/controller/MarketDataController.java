package in.sampad.marketdataservice.controller;

import in.sampad.marketdataservice.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
@Slf4j
public class MarketDataController {

    private final MarketDataService marketDataService;
    @GetMapping("/stocks")
    public ResponseEntity<?>getAllStocks(){
        return ResponseEntity.ok(
                marketDataService.getAllPrices()
        );
    }
    @GetMapping("/stocks/{symbol}")
    public ResponseEntity<?>getStockPrice(
            @PathVariable String symbol
    ){
        return ResponseEntity.ok(
                marketDataService.getPrice(symbol.toUpperCase())
        );
    }
    @GetMapping("/stocks/list")
    public ResponseEntity<?>getStockList(){
        return ResponseEntity.ok(
                marketDataService.getAllStocks()
        );
    }


}
