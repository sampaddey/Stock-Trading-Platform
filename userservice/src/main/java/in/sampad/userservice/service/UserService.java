package in.sampad.userservice.service;


import in.sampad.userservice.dto.AuthResponse;
import in.sampad.userservice.dto.LoginRequest;
import in.sampad.userservice.dto.RegisterRequest;
import in.sampad.userservice.dto.UserResponse;
import in.sampad.userservice.entity.User;
import in.sampad.userservice.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final KafkaTemplate<String,Object>kafkaTemplate;

    private static final String USER_REGISTERED_TOPIC="user.registered";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long jwtExpiration;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    public AuthResponse register(RegisterRequest request){
        log.info("Registering new User::"+request.getEmail());


        if(repository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        User user=new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setWalletBalance(
                request.getInitialDeposit()!=null?request.getInitialDeposit(): BigDecimal.valueOf(10000)
        );

        User savedUser=repository.save(user);

        log.info("User registered successfully::"+savedUser.getId());

        //publish user register event

        Map<String,Object>userRegisterEvent=new HashMap<>();

        userRegisterEvent.put("userId",savedUser.getId());
        userRegisterEvent.put("email",savedUser.getEmail());
        userRegisterEvent.put("firstName",savedUser.getFirstName());
        userRegisterEvent.put("lastName",savedUser.getLastName());
        userRegisterEvent.put("walletBalance",savedUser.getWalletBalance());

        kafkaTemplate.send(USER_REGISTERED_TOPIC,savedUser.getId(),userRegisterEvent);

        String token=generateToken(savedUser.getId(),savedUser.getEmail());

        String refreshToken=generateRefreshToken(savedUser.getId());

        return buildAuthResponse(savedUser,token,refreshToken);
    }

    public AuthResponse login(LoginRequest request){
        log.info("Login attempts::"+request.getEmail());

        User user=repository.findByEmail(request.getEmail())
                .orElseThrow(()->new RuntimeException(
                        "User not found"
                ));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        log.info("Log in successful::");

        String token=generateToken(user.getId(),user.getEmail());

        String refreshToken=generateRefreshToken(user.getId());

        return buildAuthResponse(user,token,refreshToken);
    }



    public UserResponse getUserById(String id){
        User user=repository.findById(id).orElseThrow(()->new RuntimeException(
                "User not found"
        ));

        return mapToResponse(user);
    }

    public UserResponse addFunds(String userId, BigDecimal amount){
        User user=repository.findById(userId).orElseThrow(()->new RuntimeException(
                "User not found"
        ));

        user.setWalletBalance(user.getWalletBalance().add(amount));

        User savedUser=repository.save(user);

        log.info("Fund added successfully");

        return mapToResponse(savedUser);

    }

    public UserResponse deductFunds(String userId, BigDecimal amount){
        User user=repository.findById(userId).orElseThrow(()->new RuntimeException(
                "User not found"
        ));

        if(user.getWalletBalance().compareTo(amount)<0){
            throw new RuntimeException("Insufficient balance");
        }

        user.setWalletBalance(user.getWalletBalance().subtract(amount));

        User savedUser=repository.save(user);

        log.info("Fund deducted successfully");

        return mapToResponse(savedUser);
    }

    public UserResponse creditFunds(String userId, BigDecimal amount){
        User user=repository.findById(userId).orElseThrow(()->new RuntimeException(
                "User not found"
        ));

        user.setWalletBalance(user.getWalletBalance().add(amount));

        User savedUser=repository.save(user);

        log.info("Fund credited successfully");

        return mapToResponse(savedUser);

    }

    private AuthResponse buildAuthResponse(User user, String token, String refreshToken) {

        AuthResponse response=new AuthResponse();
        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setWalletBalance(user.getWalletBalance());
        response.setAccessToken(token);
        response.setRefreshToken(refreshToken);
        return response;
    }

    private String generateToken(String userId,String email){
        return Jwts.builder()
                .claim("userId",userId)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()+jwtExpiration
                        )
                )
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateRefreshToken(String userId){
        return Jwts.builder()
                .claim("userId",userId)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()+refreshExpiration
                        )
                )
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey(){
        byte[]keyBytes= Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private UserResponse mapToResponse(User user) {

        UserResponse userResponse=new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setWalletBalance(user.getWalletBalance());
        userResponse.setStatus(user.getStatus());
        userResponse.setCreatedAt(user.getCreatedAt());
        return userResponse;

    }


}
