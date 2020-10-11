# Micronaut APP deployed as AWS Lambda with Proxy Gateway with GraalVM

It is possible to deploy the application manually (see [`joke-function-graalvm` README](https://github.com/ilopmar/codemotion-madrid-2020-micronaut-aws/blob/master/joke-function-graalvm/README.md))
but then you also need to create the AWS Gateway Proxy manually.
It is easier to do it using Cloud Formation:


```shell script
./gradlew assemble
sdk use java 20.2.0.r11-grl
gu install native-image
native-image -cp build/libs/joke-app-graalvm-0.1-all.jar
zip -j build/function.zip bootstrap joke

# Or with Docker, see https://github.com/ilopmar/codemotion-madrid-2020-micronaut-aws/blob/master/joke-function-graalvm/README.md


export S3_BUCKET=USE-YOUR-OWN-BUCKET
export STACK_NAME=USE-YOUR-OWN-STACK-NAME
export AWS_ACCESS_KEY_ID=xxxxx
export AWS_SECRET_ACCESS_KEY=xxxx
export AWS_DEFAULT_REGION=eu-central-1

aws cloudformation package --template-file sam-native.yaml --output-template-file build/output-sam.yaml --s3-bucket $S3_BUCKET
aws cloudformation deploy --template-file build/output-sam.yaml --stack-name $STACK_NAME --capabilities CAPABILITY_IAM

API_ENDPOINT=`aws cloudformation describe-stacks --stack-name $STACK_NAME | jq -r '.Stacks[0] .Outputs[0] .OutputValue'`

curl $API_ENDPOINT/jokes/category/nerdy
curl $API_ENDPOINT/jokes/566

# To delete everything
aws cloudformation delete-stack --stack-name $STACK_NAME
```
