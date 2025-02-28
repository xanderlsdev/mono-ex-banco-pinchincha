package com.bancopichincha.exchangeapi.exchange_support_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bancopichincha.exchangeapi.exchange_support_api.model.ExchangeRateAudit;
import com.bancopichincha.exchangeapi.exchange_support_api.service.ExchangeRateAuditService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ExchangeRateAuditController {

  private final ExchangeRateAuditService exchangeRateauditAuditService;

  @GetMapping
  public Flux<ExchangeRateAudit> getAllPosts() {
    return exchangeRateauditAuditService.getAudits();
  }

  @GetMapping("/{id}")
  public Mono<ExchangeRateAudit> getPostById(@PathVariable Long id) {
    return exchangeRateauditAuditService
      .getAuditsByUser(id)
      .switchIfEmpty(
        Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST))
      );
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ExchangeRateAudit> createPost(@RequestBody ExchangeRateAudit user) {
    return exchangeRateauditAuditService
      .saveAudit(user)
      .onErrorResume(
        Exception.class,
        e -> {
          return Mono.error(
            new ResponseStatusException(HttpStatus.BAD_REQUEST)
          );
        }
      );
  }
}
