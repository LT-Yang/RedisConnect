package com.tommy;

import com.apigee.flow.execution.spi.Execution;
import com.apigee.flow.execution.ExecutionContext;
import com.apigee.flow.execution.ExecutionResult;
import com.apigee.flow.message.MessageContext;
import redis.clients.jedis.Jedis;

public class RedisConnect implements Execution {

    public ExecutionResult execute(MessageContext messageContext, ExecutionContext executionContext) {

        try (Jedis jedis = new Jedis("10.99.107.100", 6379)) {

            // Authenticate to Redis if needed
            jedis.auth("foobared");

            // Ping Redis to test connection
//            String response = jedis.ping();
//            if (!"PONG".equalsIgnoreCase(response)) {
//                messageContext.setVariable("redis.status", "ping failed");
//                return ExecutionResult.ABORT;
//            }

            // Read value from Redis
            String value = jedis.get("key1");

            // Save the result into Apigee context
            messageContext.setVariable("redis.key1.value", value);

            return ExecutionResult.SUCCESS;

        } catch (Exception e) {
            // Optional: log the error
            messageContext.setVariable("redis.error", e.getMessage());
            return ExecutionResult.ABORT;
        }
    }
}


