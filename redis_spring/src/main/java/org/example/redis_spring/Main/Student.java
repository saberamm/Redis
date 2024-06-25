package org.example.redis_spring.Main;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Getter
@RedisHash("student")
public class Student implements Serializable {

    @TimeToLive
    private long timeToLive;

    @Id
    private Long id;

    @Indexed
    private String name;

    @Indexed
    private String nationalCode;
}
