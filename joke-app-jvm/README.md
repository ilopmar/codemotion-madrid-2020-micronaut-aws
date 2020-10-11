# Micronaut APP deployed as AWS Lambda with Proxy Gateway

It is possible to deploy the function manually (see [`joke-function-jvm` README](https://github.com/ilopmar/codemotion-madrid-2020-micronaut-aws/blob/master/joke-function-jvm/README.md))
but then you also need to create the AWS Gateway Proxy manually.
It is easier to do it using Cloud Formation:


```shell script
export S3_BUCKET=USE-YOUR-OWN-BUCKET
export STACK_NAME=USE-YOUR-OWN-STACK-NAME
export AWS_ACCESS_KEY_ID=xxxxx
export AWS_SECRET_ACCESS_KEY=xxxx
export AWS_DEFAULT_REGION=eu-central-1

./gradlew shadowJar
aws cloudformation package --template-file sam-jvm.yaml --output-template-file build/output-sam.yaml --s3-bucket $S3_BUCKET
aws cloudformation deploy --template-file build/output-sam.yaml --stack-name $STACK_NAME --capabilities CAPABILITY_IAM

API_ENDPOINT=`aws cloudformation describe-stacks --stack-name $STACK_NAME | jq -r '.Stacks[0] .Outputs[0] .OutputValue'`

curl $API_ENDPOINT/jokes/category/nerdy
curl $API_ENDPOINT/jokes/566

# To delete everything
aws cloudformation delete-stack --stack-name $STACK_NAME
```
