package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisMessageBroker {

    private final String hostname;
    private final int port;

    public RedisMessageBroker(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void storeAndPublish(final String channel, final String message) {
        try (Jedis jedis = new Jedis(hostname, port)) {

            jedis.lpush("tempMessages", message);

            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                    String delayedMessage = jedis.rpop("tempMessages");
                    jedis.publish(channel, delayedMessage);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    public void subscribe(final String channel, JedisPubSub listener) {
        Thread subscriberThread = new Thread(() -> {
            try (Jedis jedis = new Jedis(hostname, port)) {
                jedis.subscribe(listener, channel);
            } catch (Exception e) {
                System.out.println("Subscription error: " + e.getMessage());
            }
        });
        subscriberThread.start();
    }

    public static void main(String[] args) {
        RedisMessageBroker broker = new RedisMessageBroker("localhost", 6379);


        var listener = new SimpleMessageListener();
        broker.subscribe("testChannel", listener);


        broker.storeAndPublish("testChannel", "Hello from Redis!");


        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.exit(0);
    }

    public static class SimpleMessageListener extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            System.out.println("Received message on channel " + channel + ": " + message);
        }
    }
}
