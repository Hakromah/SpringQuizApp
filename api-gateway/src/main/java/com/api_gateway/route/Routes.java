package com.api_gateway.route;


import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.client.HttpClient;


@Configuration
public class Routes {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("quiz", r -> r
                        .path("/quiz/**")
                        .uri("lb://quiz-service")
                )
                .route("question", r -> r
                        .path("/question/**")
                        .uri("lb://question-service")
                )
                .build();
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create()
                .resolver(DefaultAddressResolverGroup.INSTANCE);
    }

}
