package com.llg.pets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class CustomWebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(customWebSocketHandler(), "/webSocketBySpring/customWebSocketHandler")
                .addInterceptors(new CustomWebSocketInterceptor())
                .setAllowedOrigins("*");
        webSocketHandlerRegistry
                .addHandler(customWebSocketHandler(), "/sockjs/webSocketBySpring/customWebSocketHandler")
                .addInterceptors(new CustomWebSocketInterceptor())
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Bean
    public WebSocketHandler customWebSocketHandler() {
        return new CustomWebSocketHandler();
    }
}
