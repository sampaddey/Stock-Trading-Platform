package in.sampad.portfolioservice.controller;

import in.sampad.portfolioservice.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    @GetMapping("/me")
    public ResponseEntity<?>getMyPortfolio(
            @RequestHeader("X-User-Id") String userId
    ){
        return ResponseEntity.ok(
                portfolioService.getPortfolio(userId)
        );
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?>getPortfolio(
            @PathVariable String userId
    ){
        return ResponseEntity.ok(portfolioService.getPortfolio(userId));
    }


}
