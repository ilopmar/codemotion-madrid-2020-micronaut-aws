# Micronaut Function JVM

## Create the application

```shell script
mn create-function-app com.example.joke --features=aws-lambda --jdk 11 --test spock
```

## Deploy to AWS

- Create fatjar: `./gradlew shadowJar`
- Create a new AWS Lambda function using Java 11 (Corretto)
- Define handler `com.example.JokeRequestHandler::execute`
- Create test event: `{"category":"nerdy"}`
