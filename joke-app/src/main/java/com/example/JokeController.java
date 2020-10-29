package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/jokes")
public class JokeController {

    private final IcndbClient client;

    public JokeController(IcndbClient client) {
        this.client = client;
    }

    @Get("/category/{category}")
    public Joke getRandomJoke(String category) {
        return client.getRandomJoke(category).orElse(null);
    }

    @Get("/{jokeId}")
    public Joke getJokeById(String jokeId) {
        return client.findJokeById(jokeId).orElse(null);
    }

}
