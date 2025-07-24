# Apigee Connect to Redis

This directory contains the Java source code and pom.xml file required to compile a simple Java callout for Apigee,
this callout is mainly for a sample to connect the Redis using Jedis.

You can directly download the [bundle](bundle/), and upload it as a proxy bundle. Or you can clone
the [callout](callout/) directory, following steps below to build the jar file and deploy proxy.

## Building

1. Download all files.

2. Set the workspace under redisconnect/callout, run
   ```
   mvn clean package
   ```

3. Upload the jar file into the API Proxy via the Apigee API Proxy Editor.

4. Create a JavaCallout policy in Apigee. It should look like this:
   ```xml
   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
   <JavaCallout continueOnError="false" enabled="true" name="Java-RedisConnect">
   <DisplayName>Java-RedisConnect</DisplayName>
   <Properties>
     <Property name="hostIP">10.96.5.201</Property> <!--Use your own redis endpoint IP.-->
   </Properties>
   <ClassName>com.cathay.RedisConnect</ClassName>
   <ResourceURL>java://RedisConnect-1.0.jar</ResourceURL>
   </JavaCallout> 
   ```

5. Deploy the proxy using the Google Cloud Console.
   
6. Use a client application, such as cURL or Postman, to send HTTP requests to the proxy.
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

## Note

### Connecting to Private Redis Endpoints ([RFC 1918](https://datatracker.ietf.org/doc/html/rfc1918))

#### Problem

When using this Java Callout to connect to a Redis instance on a private network (e.g., 10.x.x.x, 192.168.x.x), you will encounter an error like "Permission denied (\"java.net.SocketPermission\" \"10.99.107.100:6379\" \"connect,resolve\")".

#### Cause

This is an expected security feature of Apigee. The Java security manager blocks socket connections from Callouts to private IP addresses by default. You can read more in the official [Apigee Java permission reference](https://cloud.google.com/apigee/docs/api-platform/reference/java-permission-reference).

#### Solution

To enable the connection, you must apply a custom Java policy file that grants SocketPermission to your Redis endpoint.

Locate the Policy File: A template is provided in the [java-permission](java-permission/) directory. It contained all permissions that jedis need.

Customize the File: You must edit a java policy file to specify the exact IP address and port of your Redis instance.

Apply the file: You must call the Apigee API to add the policy to your runtime following steps in document [Adding a custom JavaCallout security policy](https://cloud.google.com/apigee/docs/api-platform/develop/adding-custom-java-callout-security-policy).