package com.tommy;

import com.apigee.flow.execution.spi.Execution;
import com.apigee.flow.execution.ExecutionContext;
import com.apigee.flow.execution.ExecutionResult;
import com.apigee.flow.message.MessageContext;
import java.util.Map;

import redis.clients.jedis.Jedis;


public class RedisConnect implements Execution {


    @SuppressWarnings("FieldMayBeFinal")
    private Map <String,String> properties; // read-only

    public RedisConnect(Map <String,String> properties) {
        this.properties = properties;
    }

    public ExecutionResult execute(MessageContext messageContext, ExecutionContext executionContext) {

        // Method1. Using KVMO Policy to obtain the hostIP stored in KVM,
        // change "this.properties.get("hostIP")" to "hostIP" variable and uncomment next line.
        // String hostIP = messageContext.getVariable("private.hostIP");

        // Method2. Consuming value of property setting in the JavaCallout Policy
        try (Jedis jedis = new Jedis(this.properties.get("hostIP"), 6379)) {

            // Authenticate to Redis if needed
            jedis.auth("foobared");

            // Read value "key1" from Redis
            String value = jedis.get("key1");

            // Save the result into Apigee context
            messageContext.setVariable("redis.key1.value", value);

            return ExecutionResult.SUCCESS;

        } catch (Exception e) {
            messageContext.setVariable("redis.error", e.getMessage());
            return ExecutionResult.ABORT;
        }
    }
}


