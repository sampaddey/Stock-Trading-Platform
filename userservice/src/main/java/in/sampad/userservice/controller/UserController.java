package in.sampad.userservice.controller;

import in.sampad.userservice.dto.LoginRequest;
import in.sampad.userservice.dto.RegisterRequest;
import in.sampad.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?>register(
            @Valid @RequestBody RegisterRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<?>login(
            @Valid @RequestBody LoginRequest request
            ){
        return ResponseEntity.ok(userService.login(request));
    }
    @GetMapping("/get-profile")
    public ResponseEntity<?>getMyProfile(
            @RequestHeader("X-User-Id") String userId
    ){
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<?>getUserById(
            @PathVariable String userId
    ){
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    @PostMapping("/{userId}/funds/add")
    public ResponseEntity<?>addFund(
            @PathVariable String userId,
            @RequestParam BigDecimal amount
            ){
        return ResponseEntity.ok(
                userService.addFunds(userId,amount)
        );
    }

    @PostMapping("/{userId}/funds/deduct")
    public ResponseEntity<?>deductFund(
            @PathVariable String userId,
            @RequestParam BigDecimal amount
    ){
        return ResponseEntity.ok(
                userService.deductFunds(userId,amount)
        );
    }

    @PostMapping("/{userId}/funds/credit")
    public ResponseEntity<?>creditFund(
            @PathVariable String userId,
            @RequestParam BigDecimal amount
    ){
        return ResponseEntity.ok(
                userService.creditFunds(userId,amount)
        );
    }
}
