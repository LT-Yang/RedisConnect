package com.tommy;

import com.apigee.flow.execution.spi.Execution;
import com.apigee.flow.execution.ExecutionContext;
import com.apigee.flow.execution.ExecutionResult;
import com.apigee.flow.message.MessageContext;
import java.util.Map;

import redis.clients.jedis.Jedis;


public class RedisConnect implements Execution {


    // Using property element
    private Map <String,String> properties; // read-only

    public RedisConnect(Map <String,String> properties) {
        this.properties = properties;
    }

    public ExecutionResult execute(MessageContext messageContext, ExecutionContext executionContext) {

        // Using value of header, change "hostIP" to "this.properties.get("hostIP")", and comment next line
        String hostIP = messageContext.getVariable("private.hostIP");

        try (Jedis jedis = new Jedis(hostIP, 6379)) {

            // Authenticate to Redis if needed
            jedis.auth("foobared");

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


