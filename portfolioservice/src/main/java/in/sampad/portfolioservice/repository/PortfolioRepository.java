package in.sampad.portfolioservice.repository;

import in.sampad.portfolioservice.entity.Holdings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Holdings,String> {

    List<Holdings>findByUserId(String userId);
    Optional<Holdings> findByUserIdAndSymbol(String userId, String symbol);
}
