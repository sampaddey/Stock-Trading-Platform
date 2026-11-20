//package in.sampad.marketdataservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
//
//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public RedisTemplate<String,Object>redisTemplate(
//            RedisConnectionFactory connectionFactory
//    ){
//        RedisTemplate<String,Object>template=new RedisTemplate<>();
//
//        template.setConnectionFactory(connectionFactory);
//        template.setKeySerializer(
//                new GenericJackson2JsonRedisSerializer()
//        );
//
//        return template;
//
//    }
//}

package in.sampad.marketdataservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        //GenericJacksonJsonRedisSerializer valueSerializer = GenericJacksonJsonRedisSerializer.builder().build();
        GenericJacksonJsonRedisSerializer valueSerializer =
                GenericJacksonJsonRedisSerializer.builder()
                        .enableUnsafeDefaultTyping()
                        .build();

        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);

        return template;
    }
}
