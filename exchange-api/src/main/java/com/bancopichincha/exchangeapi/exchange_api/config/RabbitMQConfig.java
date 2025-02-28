package com.bancopichincha.exchangeapi.exchange_api.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue auditQueue() {
        return new Queue("auditQueue", true);
    }
}