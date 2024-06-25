package org.example.redis_spring.Main;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@RequiredArgsConstructor
public class WithAnnotation implements CommandLineRunner {
    private final WithAnnotation main;
    private static final AtomicBoolean isExecuted = new AtomicBoolean(false);

    @SneakyThrows
    @Override
    public void run(final String... args) {
        if (isExecuted.compareAndSet(false, true)) {
            long startTime = System.currentTimeMillis();
            String string = main.cacheData("hello world");
            long endTime = System.currentTimeMillis();
            System.out.println("First call duration: " + (endTime - startTime) + "ms");
            System.out.println("First call result: " + string);
        }
    }

    @Cacheable(value = "string", key = "#WithAnnotation")
    @SneakyThrows
    public String cacheData(String WithAnnotation) {
        Thread.sleep(3000);
        return WithAnnotation;
    }
}
