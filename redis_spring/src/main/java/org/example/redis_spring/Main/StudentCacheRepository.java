package org.example.redis_spring.Main;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCacheRepository extends CrudRepository<Student, Long> {

    Student findByName(String name);
}
