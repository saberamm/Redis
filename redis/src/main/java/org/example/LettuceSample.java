package org.example;

import io.lettuce.core.Limit;
import io.lettuce.core.Range;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.HashMap;
import java.util.Map;

public class LettuceSample {
    public static void main(String[] args) {
        lettuceOperations();
    }

    public static void lettuceOperations() {
        try (RedisClient redisClient = RedisClient.create("redis://localhost:6379")) {
            try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
                performRedisOperations(connection.sync());
            } finally {
                redisClient.shutdown();
            }
        }
    }

    private static void performRedisOperations(RedisCommands<String, String> syncCommands) {

        syncCommands.set("clientName", "Lettuce");
        syncCommands.set("clientAge", "20");
        syncCommands.expire("clientName", 3600);
        System.out.println("Client name set with expiry: " + syncCommands.get("clientName"));


        syncCommands.rpush("frameworks", "Spring", "Hibernate", "Struts");
        System.out.println("Frameworks: " + syncCommands.lrange("frameworks", 0, 2));


        syncCommands.sadd("languages", "Java", "Python", "C++");
        System.out.println("Languages: " + syncCommands.smembers("languages"));


        syncCommands.hset("user#1", "name", "John");
        syncCommands.hset("user#1", "age", "30");
        System.out.println("User Info: " + syncCommands.hgetall("user#1"));


        syncCommands.zadd("scores", 100, "Alice");
        syncCommands.zadd("scores", 200, "Bob");
        System.out.println(
                "Top scores: " + syncCommands.zrevrangebyscore("scores", Range.create(10, 1000), Limit.create(0, 10)));


        String streamKey = "myStream";
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("temperature", "20");
        messageBody.put("humidity", "50");

        String messageId = syncCommands.xadd(streamKey, messageBody);
        System.out.println("Message added with ID: " + messageId);


        System.out.println("Does 'clientName' exist? " + syncCommands.exists("clientName"));
        syncCommands.del("clientName");
        System.out.println("Deleted 'clientName', exists: " + syncCommands.exists("clientName"));
    }
}
