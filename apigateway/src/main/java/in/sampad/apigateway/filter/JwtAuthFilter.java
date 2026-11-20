package in.sampad.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Base64;

@Component
@Slf4j
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    @Value("${jwt.secret}")
    private String secret;

    public JwtAuthFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request= exchange.getRequest();

            String path=request.getPath().toString();

            //skip JWT for public url

            if(isPublicEndpoint(path)){
                log.debug("Public endpoint -- skipping jwt::"+path);

                return chain.filter(exchange);

            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            //if(!request.getHeaders().containsKey())

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            try {
                Claims claims=extractClaims(token);

                String userId=claims.get(
                        "userId",String.class

                );
                String email=claims.getSubject();

                log.debug("UserId: {} email: {}",userId,email);

                ServerHttpRequest modifiedRequest=request
                        .mutate()
                        .header("X-User-Id",userId)
                        .build();

                return chain.filter(
                        exchange.mutate()
                                .request(modifiedRequest)
                                .build()
                );
            }catch (Exception e){
                log.warn("Invalid JWT token");

                return onError(exchange,"Invalid JWT token",HttpStatus.UNAUTHORIZED);
            }



        });
    }

    private boolean isPublicEndpoint(String path) {
        return path.contains("/register")||
                path.contains("/login")||
                path.contains("/health")||
                path.contains("/ws");
    }

    private Claims extractClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[]keyBytes= Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatusCode status) {
        log.warn("JWT filter rejected request: {}", message);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config{

    }

}
