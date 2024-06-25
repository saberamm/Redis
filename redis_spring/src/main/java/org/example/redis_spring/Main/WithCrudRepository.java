package org.example.redis_spring.Main;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
//@Component
public class WithCrudRepository implements CommandLineRunner {
    StudentCacheRepository repository;

    @SneakyThrows
    @Override
    public void run(final String... args) {
        Student student22 = Student.builder().id(1403L).nationalCode("11111111").name("WithCrudRepository").timeToLive(100000).build();
        repository.save(student22);
        System.out.println(repository.findByName("WithCrudRepository"));
    }
}
