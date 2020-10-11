package com.example;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import java.util.Optional;

@Client("https://api.icndb.com")
public interface IcndbClient {

    @Get("/jokes/random/?limitTo=[{category}]")
    Optional<Joke> getRandomJoke(@QueryValue String category);
}
