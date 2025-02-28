package com.bancopichincha.exchangeapi.exchange_api.service;

import com.bancopichincha.exchangeapi.exchange_api.model.ExchangeRateAudit;
import com.bancopichincha.exchangeapi.exchange_api.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExcharngeService {

  private final WebClient webClient;
  private final RabbitTemplate rabbitTemplate;
  private final ObjectMapper objectMapper;

  public ExcharngeService(
    WebClient.Builder webClientBuilder,
    RabbitTemplate rabbitTemplate,
    ObjectMapper objectMapper
  ) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    this.rabbitTemplate = rabbitTemplate;
    this.objectMapper = objectMapper;
  }

  public void sendToQueue(ExchangeRateAudit audit) {
    try {
      String message = objectMapper.writeValueAsString(audit);
      rabbitTemplate.convertAndSend("auditQueue", message);
      System.out.println("Mensaje enviado a RabbitMQ: " + message);
    } catch (JsonProcessingException e) {
      System.err.println("Error serializando objeto: " + e.getMessage());
    }
  }

  public Mono<User> sendRequest(ExchangeRateAudit request) {
    return webClient
      .post()
      .uri("/")
      .bodyValue(request)
      .retrieve()
      .bodyToMono(User.class)
      .doOnNext(response ->
        System.out.println("Respuesta recibida: " + response)
      )
      .doOnError(error -> System.out.println("Error: " + error.getMessage()));
  }
}
