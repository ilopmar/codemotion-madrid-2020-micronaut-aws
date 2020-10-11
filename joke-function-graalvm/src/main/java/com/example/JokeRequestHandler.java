package com.example;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;

import javax.inject.Inject;

@Introspected
public class JokeRequestHandler extends MicronautRequestHandler<JokeRequest, Joke> {

    @Inject
    IcndbClient client;

    @Override
    public Joke execute(JokeRequest input) {
        return client.getRandomJoke(input.getCategory()).orElse(null);
    }
}
