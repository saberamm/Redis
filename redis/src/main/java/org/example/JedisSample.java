package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.StreamEntryID;

import java.util.HashMap;
import java.util.Map;

public class JedisSample {
    public static void main(String[] args) {
        jedisOperations();
    }

    public static void jedisOperations() {

        JedisPool pool = new JedisPool("localhost", 6379);
        Jedis jedis = pool.getResource();
        {
            performRedisOperations(jedis);
        }
    }

    private static void performRedisOperations(Jedis jedis) {

        jedis.set("clientName", "Jedis");
        jedis.set("clientAge", "20");
        jedis.expire("clientName", 3600);
        System.out.println("Client name set with expiry: " + jedis.get("clientName"));


        jedis.rpush("frameworks", "Spring", "Hibernate", "Struts");
        System.out.println("Frameworks: " + jedis.lrange("frameworks", 0, 2));


        jedis.sadd("languages", "Java", "Python", "C++");
        System.out.println("Languages: " + jedis.smembers("languages"));


        jedis.hset("user#1", "name", "John");
        jedis.hset("user#1", "age", "30");
        System.out.println("User Info: " + jedis.hgetAll("user#1"));


        jedis.zadd("points", 100, "Alice");
        jedis.zadd("points", 200, "Bob");
        System.out.println("Top scores: " + jedis.zrevrangeByScore("points", 1000, 10));


        String streamKey = "myStream";
        Map<String, String> messageBody = new HashMap<>();
        messageBody.put("temperature", "20");
        messageBody.put("humidity", "50");

        StreamEntryID streamEntryID = StreamEntryID.NEW_ENTRY;
        String messageId = String.valueOf(jedis.xadd(streamKey, streamEntryID, messageBody));
        System.out.println("Message added with ID: " + messageId);


        System.out.println("Does 'clientName' exist? " + jedis.exists("clientName"));
        jedis.del("clientName");
        System.out.println("Deleted 'clientName', exists: " + jedis.exists("clientName"));
    }
}
