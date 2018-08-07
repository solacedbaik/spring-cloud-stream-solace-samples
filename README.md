# spring-cloud-stream-solace
Spring Cloud Stream samples demonstrating various message exchange patterns using the Solace SCS binder.

*Examples include:*

1. MessageConverter:  Sample processor app that takes a message and formats it for logging and sends to an output channel.  Uses the default SCS Processor interface.

2. MultipleOutputsService: Custom processor using non-default named channels to conditionally route messages depending on the value of the input.

3.  MultipleOutputsWithConditionsService:  Another custom processor that conditional routes but uses annotation-based header filters for declarative routing instead of programmatic checking of conditions.  Note that payloads cannot be checked conditionally using this version of SCS.

4.  SolaceController:  REST controller that accepts HTTP input and publishes a message to the SCS transport (in this case, Solace) using REST input.  Can also accept input from an input channel.

## Running the application

TODO:  This project should be split out into separate Spring Boot apps to allow for running each SCS app independently and used in SCDF.  Since this project was based off the Baeldung sample, it kept the same approach of containing all SCS examples.  As such, you may need to use STS to easily test and run each of the samples.

### Testing

TODO ...