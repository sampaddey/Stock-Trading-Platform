package in.sampad.marketdataservice.config;

import in.sampad.marketdataservice.entity.Stock;
import in.sampad.marketdataservice.repository.MarketDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private final MarketDataRepository repository;
    @Override
    public void run(String... args) throws Exception {


        if(repository.count()>0){
            log.info("Stocks already initiated - skipping");
            return;
        }

        log.info("Initialing stock data...");

//        List<Stock>stocks=List.of(
//            new Stock("APPL","Apple Inc", BigDecimal.valueOf(189.50),
//                    "NASDAQ","USD",true)
//        )
        List<Stock> stocks = List.of(
                // ---------- NASDAQ ----------
                new Stock("AAPL", "Apple Inc", BigDecimal.valueOf(189.50), "NASDAQ", "USD", true),
                new Stock("MSFT", "Microsoft Corporation", BigDecimal.valueOf(415.20), "NASDAQ", "USD", true),
                new Stock("GOOGL", "Alphabet Inc", BigDecimal.valueOf(172.30), "NASDAQ", "USD", true),
                new Stock("AMZN", "Amazon.com Inc", BigDecimal.valueOf(185.60), "NASDAQ", "USD", true),
                new Stock("NVDA", "NVIDIA Corporation", BigDecimal.valueOf(125.40), "NASDAQ", "USD", true),
                new Stock("META", "Meta Platforms Inc", BigDecimal.valueOf(505.75), "NASDAQ", "USD", true),
                new Stock("TSLA", "Tesla Inc", BigDecimal.valueOf(248.90), "NASDAQ", "USD", true),
                new Stock("AVGO", "Broadcom Inc", BigDecimal.valueOf(1380.00), "NASDAQ", "USD", true),
                new Stock("COST", "Costco Wholesale Corporation", BigDecimal.valueOf(840.30), "NASDAQ", "USD", true),
                new Stock("NFLX", "Netflix Inc", BigDecimal.valueOf(640.10), "NASDAQ", "USD", true),
                new Stock("ADBE", "Adobe Inc", BigDecimal.valueOf(480.25), "NASDAQ", "USD", true),
                new Stock("AMD", "Advanced Micro Devices Inc", BigDecimal.valueOf(158.70), "NASDAQ", "USD", true),
                new Stock("PEP", "PepsiCo Inc", BigDecimal.valueOf(170.45), "NASDAQ", "USD", true),
                new Stock("CSCO", "Cisco Systems Inc", BigDecimal.valueOf(48.90), "NASDAQ", "USD", true),
                new Stock("INTC", "Intel Corporation", BigDecimal.valueOf(31.20), "NASDAQ", "USD", true),
                new Stock("QCOM", "Qualcomm Inc", BigDecimal.valueOf(205.35), "NASDAQ", "USD", true),
                new Stock("TXN", "Texas Instruments Inc", BigDecimal.valueOf(195.60), "NASDAQ", "USD", true),
                new Stock("INTU", "Intuit Inc", BigDecimal.valueOf(645.80), "NASDAQ", "USD", true),
                new Stock("AMGN", "Amgen Inc", BigDecimal.valueOf(310.15), "NASDAQ", "USD", true),
                new Stock("HON", "Honeywell International Inc", BigDecimal.valueOf(205.40), "NASDAQ", "USD", true),
                new Stock("AMAT", "Applied Materials Inc", BigDecimal.valueOf(225.90), "NASDAQ", "USD", true),
                new Stock("SBUX", "Starbucks Corporation", BigDecimal.valueOf(92.30), "NASDAQ", "USD", true),
                new Stock("ISRG", "Intuitive Surgical Inc", BigDecimal.valueOf(395.70), "NASDAQ", "USD", true),
                new Stock("BKNG", "Booking Holdings Inc", BigDecimal.valueOf(3850.00), "NASDAQ", "USD", true),
                new Stock("GILD", "Gilead Sciences Inc", BigDecimal.valueOf(68.40), "NASDAQ", "USD", true),
                new Stock("ADP", "Automatic Data Processing Inc", BigDecimal.valueOf(245.20), "NASDAQ", "USD", true),
                new Stock("MDLZ", "Mondelez International Inc", BigDecimal.valueOf(70.15), "NASDAQ", "USD", true),
                new Stock("VRTX", "Vertex Pharmaceuticals Inc", BigDecimal.valueOf(455.60), "NASDAQ", "USD", true),
                new Stock("REGN", "Regeneron Pharmaceuticals Inc", BigDecimal.valueOf(980.50), "NASDAQ", "USD", true),
                new Stock("LRCX", "Lam Research Corporation", BigDecimal.valueOf(920.00), "NASDAQ", "USD", true),
                new Stock("PANW", "Palo Alto Networks Inc", BigDecimal.valueOf(310.40), "NASDAQ", "USD", true),
                new Stock("MU", "Micron Technology Inc", BigDecimal.valueOf(128.75), "NASDAQ", "USD", true),
                new Stock("KLAC", "KLA Corporation", BigDecimal.valueOf(780.30), "NASDAQ", "USD", true),
                new Stock("SNPS", "Synopsys Inc", BigDecimal.valueOf(560.90), "NASDAQ", "USD", true),
                new Stock("CDNS", "Cadence Design Systems Inc", BigDecimal.valueOf(285.15), "NASDAQ", "USD", true),
                new Stock("ABNB", "Airbnb Inc", BigDecimal.valueOf(145.60), "NASDAQ", "USD", true),
                new Stock("MRVL", "Marvell Technology Inc", BigDecimal.valueOf(72.80), "NASDAQ", "USD", true),
                new Stock("ORLY", "O'Reilly Automotive Inc", BigDecimal.valueOf(1050.00), "NASDAQ", "USD", true),
                new Stock("MAR", "Marriott International Inc", BigDecimal.valueOf(245.30), "NASDAQ", "USD", true),
                new Stock("CSX", "CSX Corporation", BigDecimal.valueOf(34.50), "NASDAQ", "USD", true),
                new Stock("PYPL", "PayPal Holdings Inc", BigDecimal.valueOf(64.20), "NASDAQ", "USD", true),
                new Stock("CRWD", "CrowdStrike Holdings Inc", BigDecimal.valueOf(330.75), "NASDAQ", "USD", true),
                new Stock("FTNT", "Fortinet Inc", BigDecimal.valueOf(62.40), "NASDAQ", "USD", true),
                new Stock("ADSK", "Autodesk Inc", BigDecimal.valueOf(240.10), "NASDAQ", "USD", true),
                new Stock("WDAY", "Workday Inc", BigDecimal.valueOf(255.90), "NASDAQ", "USD", true),
                new Stock("DDOG", "Datadog Inc", BigDecimal.valueOf(122.30), "NASDAQ", "USD", true),
                new Stock("ZS", "Zscaler Inc", BigDecimal.valueOf(190.60), "NASDAQ", "USD", true),
                new Stock("TEAM", "Atlassian Corporation", BigDecimal.valueOf(175.20), "NASDAQ", "USD", true),
                new Stock("EBAY", "eBay Inc", BigDecimal.valueOf(55.80), "NASDAQ", "USD", true),
                new Stock("ROST", "Ross Stores Inc", BigDecimal.valueOf(142.70), "NASDAQ", "USD", true),

                // ---------- NYSE ----------
                new Stock("JPM", "JPMorgan Chase & Co", BigDecimal.valueOf(198.40), "NYSE", "USD", true),
                new Stock("V", "Visa Inc", BigDecimal.valueOf(272.50), "NYSE", "USD", true),
                new Stock("MA", "Mastercard Inc", BigDecimal.valueOf(455.30), "NYSE", "USD", true),
                new Stock("UNH", "UnitedHealth Group Inc", BigDecimal.valueOf(490.20), "NYSE", "USD", true),
                new Stock("JNJ", "Johnson & Johnson", BigDecimal.valueOf(147.80), "NYSE", "USD", true),
                new Stock("WMT", "Walmart Inc", BigDecimal.valueOf(67.90), "NYSE", "USD", true),
                new Stock("XOM", "Exxon Mobil Corporation", BigDecimal.valueOf(115.60), "NYSE", "USD", true),
                new Stock("PG", "Procter & Gamble Co", BigDecimal.valueOf(163.25), "NYSE", "USD", true),
                new Stock("HD", "The Home Depot Inc", BigDecimal.valueOf(345.10), "NYSE", "USD", true),
                new Stock("CVX", "Chevron Corporation", BigDecimal.valueOf(157.30), "NYSE", "USD", true),
                new Stock("LLY", "Eli Lilly and Company", BigDecimal.valueOf(780.40), "NYSE", "USD", true),
                new Stock("BAC", "Bank of America Corporation", BigDecimal.valueOf(39.70), "NYSE", "USD", true),
                new Stock("KO", "The Coca-Cola Company", BigDecimal.valueOf(62.50), "NYSE", "USD", true),
                new Stock("ABBV", "AbbVie Inc", BigDecimal.valueOf(171.90), "NYSE", "USD", true),
                new Stock("MRK", "Merck & Co Inc", BigDecimal.valueOf(128.20), "NYSE", "USD", true),
                new Stock("ORCL", "Oracle Corporation", BigDecimal.valueOf(135.45), "NYSE", "USD", true),
                new Stock("CRM", "Salesforce Inc", BigDecimal.valueOf(255.80), "NYSE", "USD", true),
                new Stock("MCD", "McDonald's Corporation", BigDecimal.valueOf(268.30), "NYSE", "USD", true),
                new Stock("DIS", "The Walt Disney Company", BigDecimal.valueOf(98.60), "NYSE", "USD", true),
                new Stock("WFC", "Wells Fargo & Company", BigDecimal.valueOf(59.40), "NYSE", "USD", true),
                new Stock("ABT", "Abbott Laboratories", BigDecimal.valueOf(104.70), "NYSE", "USD", true),
                new Stock("TMO", "Thermo Fisher Scientific Inc", BigDecimal.valueOf(575.20), "NYSE", "USD", true),
                new Stock("ACN", "Accenture plc", BigDecimal.valueOf(310.60), "NYSE", "USD", true),
                new Stock("NKE", "Nike Inc", BigDecimal.valueOf(95.15), "NYSE", "USD", true),
                new Stock("VZ", "Verizon Communications Inc", BigDecimal.valueOf(40.85), "NYSE", "USD", true),
                new Stock("T", "AT&T Inc", BigDecimal.valueOf(17.60), "NYSE", "USD", true),
                new Stock("PFE", "Pfizer Inc", BigDecimal.valueOf(28.90), "NYSE", "USD", true),
                new Stock("IBM", "International Business Machines Corporation", BigDecimal.valueOf(172.40), "NYSE", "USD", true),
                new Stock("CAT", "Caterpillar Inc", BigDecimal.valueOf(335.70), "NYSE", "USD", true),
                new Stock("GE", "General Electric Company", BigDecimal.valueOf(165.30), "NYSE", "USD", true),
                new Stock("BA", "The Boeing Company", BigDecimal.valueOf(182.50), "NYSE", "USD", true),
                new Stock("GS", "The Goldman Sachs Group Inc", BigDecimal.valueOf(455.90), "NYSE", "USD", true),
                new Stock("MS", "Morgan Stanley", BigDecimal.valueOf(96.80), "NYSE", "USD", true),
                new Stock("UPS", "United Parcel Service Inc", BigDecimal.valueOf(138.20), "NYSE", "USD", true),
                new Stock("LOW", "Lowe's Companies Inc", BigDecimal.valueOf(225.60), "NYSE", "USD", true),
                new Stock("NEE", "NextEra Energy Inc", BigDecimal.valueOf(72.30), "NYSE", "USD", true),
                new Stock("RTX", "RTX Corporation", BigDecimal.valueOf(100.45), "NYSE", "USD", true),
                new Stock("DE", "Deere & Company", BigDecimal.valueOf(385.10), "NYSE", "USD", true),
                new Stock("LMT", "Lockheed Martin Corporation", BigDecimal.valueOf(455.70), "NYSE", "USD", true),
                new Stock("AXP", "American Express Company", BigDecimal.valueOf(235.40), "NYSE", "USD", true),
                new Stock("BLK", "BlackRock Inc", BigDecimal.valueOf(790.20), "NYSE", "USD", true),
                new Stock("SCHW", "The Charles Schwab Corporation", BigDecimal.valueOf(73.60), "NYSE", "USD", true),
                new Stock("C", "Citigroup Inc", BigDecimal.valueOf(62.80), "NYSE", "USD", true),
                new Stock("F", "Ford Motor Company", BigDecimal.valueOf(12.40), "NYSE", "USD", true),
                new Stock("GM", "General Motors Company", BigDecimal.valueOf(45.30), "NYSE", "USD", true),
                new Stock("UBER", "Uber Technologies Inc", BigDecimal.valueOf(68.90), "NYSE", "USD", true),
                new Stock("SPGI", "S&P Global Inc", BigDecimal.valueOf(425.50), "NYSE", "USD", true),
                new Stock("PM", "Philip Morris International Inc", BigDecimal.valueOf(98.70), "NYSE", "USD", true),
                new Stock("MMM", "3M Company", BigDecimal.valueOf(102.30), "NYSE", "USD", true),
                new Stock("COP", "ConocoPhillips", BigDecimal.valueOf(118.90), "NYSE", "USD", true)
        );

        repository.saveAll(stocks);

        log.info("Initialized {} loaded successfully",stocks.size());


    }
}
