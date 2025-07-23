# Apigee Connect to Redis 

This directory contains the Java source code and pom.xml file required to
compile a Java callout for Apigee. This callout is mainly for a sample to connect the Redis using Jedis.

## Building:

1. Download all files. 

2. Set the workspace under redisconnect, run
   ```mvn clean package```

3. Upload the jar file into the API Proxy via the Apigee API Proxy Editor.

4. Create the JavaCallout policy. It should look like this:
   ```xml
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <JavaCallout continueOnError="false" enabled="true" name="Java-RedisConnect">
   <DisplayName>Java-RedisConnect</DisplayName>
   <Properties/>
   <ClassName>com.tommy.RedisConnect</ClassName>
   <ResourceURL>java://RedisConnect-1.0.jar</ResourceURL>
   </JavaCallout>
   ```

5. Use the Google Cloud Console UI to deploy the proxy.
   
6. Use a client to generate and send http requests to the proxy.
   ```
   curl -i https://my-endpoint.net/basePath
   ```




## Dependencies

- Apigee Edge expressions v1.0
- Apigee Edge message-flow v1.0
- Redis Clients Jedis v6.0.0

## Example Usage

```
curl -ik https://a.tommy4apigee.com/redis-test
HTTP/2 200
host: a.tommy4apigee.com
user-agent: curl/7.86.0
accept: */*
x-forwarded-for: 10.99.107.168
x-forwarded-proto: https
x-request-id: d13fdb80-984b-4dd4-8338-f8a97a098404
x-b3-traceid: 9b0df4cc7112287a500807b36bee8bcb
x-b3-spanid: 500807b36bee8bcb
x-b3-sampled: 0
content-type: text/plain
content-length: 23
date: Tue, 06 May 2025 07:06:52 GMT

The value of key1 is 4.
```
