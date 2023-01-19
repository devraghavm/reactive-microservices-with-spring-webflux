package com.raghav.webfluxdemo.webtestclient;

import com.raghav.webfluxdemo.config.RequestHandler;
import com.raghav.webfluxdemo.config.RouterConfig;
import com.raghav.webfluxdemo.dto.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class Lec05RouterFunctionTest {
    private WebTestClient client;

    private WebTestClient externalClient;

//    @Autowired
//    private RouterConfig routerConfig;

    @Autowired
    private ApplicationContext ctx;

    @MockBean
    private RequestHandler handler;

    @BeforeAll
    public void setClient() {
//        this.client = WebTestClient.bindToRouterFunction(routerConfig.highLevelRouterFunction()).build();
        this.client = WebTestClient.bindToApplicationContext(ctx).build();
        // For Integration Test and connecting to external server use below
        this.externalClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

    @Test
    public void test() {
        Mockito.when(handler.squareHandler(Mockito.any())).thenReturn(ServerResponse.ok().bodyValue(new Response(225)));
        this.client
                .get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(r -> Assertions.assertThat(r.getOutput()).isEqualTo(225));
    }
}
