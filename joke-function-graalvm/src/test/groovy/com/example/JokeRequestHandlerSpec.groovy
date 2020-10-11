package com.example

import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class JokeRequestHandlerSpec extends Specification {

    @Shared
    @AutoCleanup
    JokeRequestHandler jokeRequestHandler = new JokeRequestHandler()

    void 'test joke handler'() {
        given:
        JokeRequest jokeRequest = new JokeRequest()
        jokeRequest.category = 'nerdy'

        when:
        Joke joke = jokeRequestHandler.execute(jokeRequest)

        then:
        joke
        joke.factId
        joke.text
        joke.type == 'success'
    }
}
