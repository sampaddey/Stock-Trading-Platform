package in.sampad.marketdataservice.repository;

import in.sampad.marketdataservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketDataRepository extends JpaRepository<Stock, String> {

    List<Stock>findByActiveTrue();
}
