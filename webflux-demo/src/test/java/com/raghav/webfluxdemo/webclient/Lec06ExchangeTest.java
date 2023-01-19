package com.raghav.webfluxdemo.webclient;

import com.raghav.webfluxdemo.dto.InputFieldValidationResponse;
import com.raghav.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec06ExchangeTest extends BaseTest {
    @Autowired
    private WebClient webClient;

    @Test
    public void badRequestTest() {
        Mono<Object> responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{number}/throw", 25)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println(err.getMessage()));

        StepVerifier.create(responseMono)
                    .expectNextCount(1)
                    .verifyComplete();

    }

    private Mono<Object> exchange(ClientResponse cr) {
        if (cr.statusCode().is4xxClientError()) {
            return cr.bodyToMono(InputFieldValidationResponse.class);
        } else {
            return cr.bodyToMono(Response.class);
        }
    }
}
