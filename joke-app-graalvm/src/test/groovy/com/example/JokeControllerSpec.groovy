package com.example

import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext
import com.amazonaws.serverless.proxy.model.AwsProxyRequest
import com.amazonaws.serverless.proxy.model.AwsProxyResponse
import com.amazonaws.services.lambda.runtime.Context
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.function.aws.proxy.MicronautLambdaHandler
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class JokeControllerSpec extends Specification {

    @Shared
    @AutoCleanup
    MicronautLambdaHandler handler = new MicronautLambdaHandler()

    @Shared
    Context lambdaContext = new MockLambdaContext()

    @Shared
    ObjectMapper objectMapper = handler.applicationContext.getBean(ObjectMapper)

    void 'get random nerdy joke'() {
        given:
        AwsProxyRequest request = new AwsProxyRequestBuilder('/jokes/category/nerdy', HttpMethod.GET.toString())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .build()

        when:
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext)

        then:
        HttpStatus.OK.code == response.statusCode
        response.body

        when:
        println "response.body = " + response.body
        Map jokeResponse = objectMapper.readValue(response.body, Map<String, Object>)

        then:
        jokeResponse
        jokeResponse.type == 'success'
        jokeResponse.value
        jokeResponse.factId
    }

    void 'get a joke by id'() {
        given:
        AwsProxyRequest request = new AwsProxyRequestBuilder('/jokes/566', HttpMethod.GET.toString())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .build()

        when:
        AwsProxyResponse response = handler.handleRequest(request, lambdaContext)

        then:
        HttpStatus.OK.code == response.statusCode
        response.body
        response.body == '{"type":"success","factId":566,"value":"Chuck Norris could use anything in java.util.* to kill you, including the javadocs."}'
    }

}
