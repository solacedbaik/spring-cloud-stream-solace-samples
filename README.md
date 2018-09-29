# spring-cloud-stream-solace-samples
Spring Cloud Stream samples demonstrating various message exchange patterns using the Solace SCS binder.  These samples have been adapted from the ones noted in the [Baeldung Blog](https://www.baeldung.com/spring-cloud-stream).  Specifically, the samples are configured to work with the [Spring Cloud Stream Binder for Solace PubSub+](https://github.com/SolaceProducts/spring-cloud-stream-binder-solace).  You can find additional samples using the Solace SCS binder in the official [Solace Sample Repo for SCS](https://github.com/SolaceLabs/solace-samples-spring-cloud-stream).

## Examples include:

1. basic-message-converter:  Sample processor app that takes a message and formats it for logging and sends to an output channel.  Uses the default SCS Processor interface.

2. conditional-router: Custom processor using non-default named channels to conditionally route messages depending on the value of the input.

3.  conditional-declarative-router:  Another custom processor that conditionally routes but uses annotation-based header filters for declarative routing instead of programmatic checking of conditions.  Note that payload-based routing is not supported by the SCS version tested with these samples.

4.  solace-controller:  REST controller that accepts HTTP input and publishes a message to the SCS transport (in this case, Solace) using REST input.  Can also accept input from an input channel.

### Common Setup

Update the application.yml file to use your Solace configuration (username, credentials, connection info, etc.).  Feel free to update other settings (e.g. HTTP listener port, channel binding info) as you wish.

### Building and running the application

1. Run `mvn clean package`
2. Run `java -jar target/{PROJECT-NAME}.jar`

Replace {PROJECT-NAME} with the actual generated jar name.

### Testing

Unit tests are provided with each of the samples, however, running them in STS will not use the Solace binder.  You can test the apps by starting each one using the Boot Dashboard, and sending test messages as noted below.
 
1. basic-message-converter

Create a POST request to the broker using the following:

* URL (adjust as needed):
`http://192.168.65.3:9000/myInput)`
or
`http://192.168.65.3:9000/a/b/c)`

* Header:
`Content-Type: application/json`

* JSON Body (for example):
`{ "message": "some data"}`

* Result: The converted message should appear on topic `myOutput` formatted along the lines of:
`[1]: {"message": "some data"}`

2. conditional-router:

Create a POST request to the broker using the following:

* URL (adjust as needed):
`http://192.168.65.3:9000/testInput`

* Header:
`Content-Type: application/json`

* JSON Body (for example):
`1` or
`10` (or above)

* Result: The same message should appear on topic `anOutput` or `anotherOutput` depending on whether the body is < 10 or not.

3.  conditional-declarative-router:

Create a POST request to the broker using the following:

* URL (adjust as needed):
`http://192.168.65.3:9000/someInput`

* Headers:
`Content-Type: application/json`

`Solace-User-Property-type: eagle` or
`Solace-User-Property-type: bogey`

* JSON Body (for example):
`123`

* Result: The same message should appear on topic `highOutput` or `lowOutput` depending on the header used above.

4.  solace-controller:

Create a POST request to the broker using the following:

* URL (adjust as needed):
`http://192.168.65.3:9000/restInput`

* Header:
`Content-Type: application/json`

* JSON Body (for example):
`Somebody`

* Result: You should see a response message published on the topic `restOutput` similar to the following:

`{"id":1, "content":"Hello, \"Somebody\"!"}`

Alternatively, you can do a GET request to the microservice itself (Spring Boot Web sevice):

`http://localhost:9999/greeting?name=Somebody`

NOTE: You may have to login with username `user` and the auto-generated password provided on startup.

You should get an HTTP reply with the same content noted above, in addition to the message being published to `restOutput`.  

## Resources

For more information about the Spring Cloud Streams Binder for Solace PubSub+:

- [Github Source - Spring Cloud Streams Binder for Solace PubSub+](https://github.com/SolaceProducts/spring-cloud-stream-binder-solace/)

For more information about Spring Cloud Streams try these resources:

- [Spring Docs - Spring Cloud Stream Reference Guide](https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/)
- [GitHub Samples - Spring Cloud Stream Sample Applications](https://github.com/spring-cloud/spring-cloud-stream-samples)
- [Github Source - Spring Cloud Stream Source Code](https://github.com/spring-cloud/spring-cloud-stream)

For more information about Solace technology in general please visit these resources:

- The Solace Developer Portal website at: http://dev.solace.com
- Understanding [Solace technology](http://dev.solace.com/tech/)
- Ask the [Solace community](http://dev.solace.com/community/)
