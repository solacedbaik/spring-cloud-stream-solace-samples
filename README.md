# spring-cloud-stream-solace-samples
Spring Cloud Stream samples demonstrating various message exchange patterns using the Solace SCS binder.  These samples have been adapted from the ones noted in the [Baeldung Blog](https://www.baeldung.com/spring-cloud-stream).  Specifically, the samples are configured to work with the [Spring Cloud Stream Binder for Solace PubSub+](https://github.com/SolaceProducts/spring-cloud-stream-binder-solace).  You can find additional samples using the Solace SCS binder in the official [Solace Sample Repo for SCS](https://github.com/SolaceLabs/solace-samples-spring-cloud-stream).

## Examples include:

1. MessageConverter:  Sample processor app that takes a message and formats it for logging and sends to an output channel.  Uses the default SCS Processor interface.

2. MultipleOutputsService: Custom processor using non-default named channels to conditionally route messages depending on the value of the input.

3.  MultipleOutputsWithConditionsService:  Another custom processor that conditionally routes but uses annotation-based header filters for declarative routing instead of programmatic checking of conditions.  Note that payload-based routing is not supported by the SCS version tested with these samples.

4.  SolaceController:  REST controller that accepts HTTP input and publishes a message to the SCS transport (in this case, Solace) using REST input.  Can also accept input from an input channel.

### Common Setup

Update the application.yml file to use your Solace configuration (username, credentials, connection info, etc.).  Feel free to update other settings (e.g. HTTP listener port, channel binding info) as you wish.

### Building and running the application

1. Run `mvn clean package`
2. Run `java -jar target/{PROJECT-NAME}.jar`

Replace {PROJECT-NAME} with the actual generated jar name.

* NOTE:  This project should be split out into separate Spring Boot apps to allow for running each SCS app independently as would typically be done for microservices.  

### Testing

Since this project was based off the Baeldung sample, it kept the same approach of containing all SCS examples.  As such, you can use STS to easily run unit tests on each of the samples.

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
