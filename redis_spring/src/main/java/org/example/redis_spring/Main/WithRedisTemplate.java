package org.example.redis_spring.Main;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class WithRedisTemplate implements CommandLineRunner {

    private final RedisTemplate<String, Object> redisTemplate;

    @SneakyThrows
    @Override
    public void run(final String... args) {
        Student student = Student.builder()
                .id(1403L)
                .nationalCode("11111111")
                .name("tehran jug")
                .build();

        cacheData("WithRedisTemplate", student);

        getData("WithRedisTemplate");
    }

    public void cacheData(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void getData(String key) {
        System.out.println(redisTemplate.opsForValue().get(key));
    }
}
