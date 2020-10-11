# Micronaut Function GraalVM

## Create the application

```shell script
mn create-function-app com.example.joke --features=aws-lambda,graalvm --jdk 11 --test spock
```

## Deploy to AWS

### Building GraalVM native image locally

- Create fatjar:

`./gradlew shadowJar`

- Use GraalVM SDK:

`sdk use java 20.2.0.r11-grl`

- Install native-image (only first time):

`gu install native-image`

- Create native-image:

`native-image -cp build/libs/joke-function-graalvm-0.1-all.jar`

- Package the function:

`zip -j build/function.zip bootstrap joke`


### Building GraalVM native-image inside Docker

- Run `deploy.sh`


## Deploy

- Create a new AWS Lambda function using "Provide your own bootstrap on Amazon Linux 2"
- Upload `function.zip` file
- Ignore the handler, set the memory to 512 MB and change the runtime to "Custom runtime"
- Create test event from "Amazon API Gateway AWS Proxy" and modify only the `body` parameter to:

`"body": "{\"category\":\"nerdy\"}",`