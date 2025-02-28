package com.bancopichincha.exchangeapi.exchange_api.service;

import com.bancopichincha.exchangeapi.exchange_api.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private final WebClient webClient;

  public UserService(WebClient.Builder webClientBuilder) {
    this.webClient =
      webClientBuilder.baseUrl("https://gorest.co.in/public/v2").build();
  }

  public Mono<User> validarUser(String id) {
    return webClient
      .get()
      .uri("/users/{id}", id)
      .retrieve()
      .onStatus(status -> status.value() == 404, response -> Mono.empty()) // Si el ID no existe, retorna vacío
      .bodyToMono(User.class)
      .flatMap(user -> user.getId() == null ? Mono.empty() : Mono.just(user)) // Evita devolver un objeto con valores nulos
      .onErrorResume(
        Exception.class,
        error -> {
          return Mono.error(new Exception("Error al conectar con el servidor"));
        }
      );
  }

  public Mono<User> getClienteById(String id) {
    // En un caso real, esto podría conectarse a una base de datos
    return validarUser(id);
  }
}
