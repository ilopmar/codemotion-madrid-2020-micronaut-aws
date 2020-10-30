# Micronaut APP deployed as AWS Lambda with API Proxy Gateway

This is a "normal" Micronaut application created with Micronaut 2.1.2 that can be run locally but also deployed to AWS
Lambda using API Proxy Gateway.

To create the application:

```shell script
mn create-app com.example.joke --features=aws-lambda --jdk 11 --test spock
```


## Run the application locally

`./gradlew run`

Please note that you can change the `runtime` used to run the application in `build.gradle`.


## Deploy the application

For deploying the application using Cloud Formation it is necessary to export some environment variables with the 
configuration and credentials:

```shell script
export S3_BUCKET=USE-YOUR-OWN-BUCKET
export STACK_NAME=USE-YOUR-OWN-STACK-NAME
export AWS_ACCESS_KEY_ID=xxxxx
export AWS_SECRET_ACCESS_KEY=xxxx
export AWS_DEFAULT_REGION=eu-central-1
```

The following sections describe how to package the app in different ways and deploy to AWS using Cloud Formation. Once
you deploy the application go to [testing the deployed application section](https://github.com/ilopmar/codemotion-madrid-2020-micronaut-aws#test-the-deployed-application)
to see how test it.


### Using JDK 11 as runtime

Build the fatjar:

```shell script
./gradlew assemble
```

Use the `sam-jvm.yaml` Cloud Formation template to deploy the application. Please note that the template uses `java11`
as runtime, and the handler is set to `io.micronaut.function.aws.proxy.MicronautLambdaHandler`.

```shell script
aws cloudformation package --template-file sam-jvm.yaml --output-template-file build/output-sam.yaml --s3-bucket $S3_BUCKET
aws cloudformation deploy --template-file build/output-sam.yaml --stack-name $STACK_NAME --capabilities CAPABILITY_IAM
```

### Using GraalVM as runtime (building native image locally)

Build the fatjar:

```shell script
./gradlew assemble
```

Use GraalVM SDK and build the native-image. Then package it along with `bootstrap` file into the zip that will be uploaded
to AWS.

```shell script
sdk use java 20.2.0.r11-grl
gu install native-image # this is only necessary the first time

native-image -cp build/libs/joke-app-0.1-all.jar -H:Name=joke -H:Class=io.micronaut.function.aws.runtime.MicronautLambdaRuntime

zip -j build/libs/joke-app-0.1-lambda.zip bootstrap joke
```

Deploy using Cloud Formation. The only difference with the previous deploy using JDK11 is that now we use the `sam-native.yaml`
template. It uses `provided` as runtime, and the handler is not used.


```shell script
aws cloudformation package --template-file sam-native.yaml --output-template-file build/output-sam.yaml --s3-bucket $S3_BUCKET
aws cloudformation deploy --template-file build/output-sam.yaml --stack-name $STACK_NAME --capabilities CAPABILITY_IAM
```

### Using GraalVM as runtime (building everything inside Docker)

The result of this approach is the same as the previous section. A Micronaut application built as a GraalVM native image
and deployed to AWS Lambda using the `provided` runtime. The difference is that everything is done inside Docker so you
don't need to install GraalVM locally.
This is also useful if you run Mac or Windows because you need a Linux-based image to deploy to AWS.  

To build the application:

```shell script
./gradlew buildNativeLambda
```

Note: If you modified the `runtime` in `build.gradle`, then run `./gradlew -Pmicronaut.runtime=lambda buildNativeLambda`

The output is the deployable zip `build/libs/joke-app-0.1-lambda.zip`

Using the sample Cloud Formation template as before:

```shell script
aws cloudformation package --template-file sam-native.yaml --output-template-file build/output-sam.yaml --s3-bucket $S3_BUCKET
aws cloudformation deploy --template-file build/output-sam.yaml --stack-name $STACK_NAME --capabilities CAPABILITY_IAM
```

## Test the deployed application

Once the application has been deployed successfully to AWS Lambda.

Get the endpoint in which it is available:

```shell script
API_ENDPOINT=`aws cloudformation describe-stacks --stack-name $STACK_NAME | jq -r '.Stacks[0] .Outputs[0] .OutputValue'`

# To display the endpoint
echo $API_ENDPOINT
```

Send request:

```shell script
curl $API_ENDPOINT/jokes/category/nerdy
curl $API_ENDPOINT/jokes/566
```

Finally, to delete everything:

```shell script
aws cloudformation delete-stack --stack-name $STACK_NAME
```

