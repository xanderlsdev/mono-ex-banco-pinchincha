package com.bancopichincha.exchangeapi.exchange_support_api.service;

import com.bancopichincha.exchangeapi.exchange_support_api.model.ExchangeRateAudit;
import com.bancopichincha.exchangeapi.exchange_support_api.repository.ExchangeRateAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExchangeRateAuditService {

  private final ExchangeRateAuditRepository exchangeRateauditRepository;

  public Mono<ExchangeRateAudit> saveAudit(
    ExchangeRateAudit exchangeRateaudit
  ) {
    return 
      // Mono
      // .fromCallable(() -> {
      //   try {
      //     System.out.println("Durmiendo el hilo...");
      //     Thread.sleep(10000); // 10 segundos de espera
      //   } catch (InterruptedException e) {
      //     Thread.currentThread().interrupt();
      //   }
      //   return exchangeRateaudit;
      // })
      // .flatMap(audit -> exchangeRateauditRepository.save(audit))
      exchangeRateauditRepository.save(exchangeRateaudit)
      .onErrorResume(
        TransientDataAccessResourceException.class,
        error -> {
          // Manejo específico para TransientDataAccessResourceException
          System.out.println("Error transient: " + error.getMessage());
          return Mono.error(
            new Exception("No se pudo crear el usuario", error)
          );
        }
      )
      .onErrorResume(
        Exception.class,
        error -> {
          // Manejo genérico para otros errores
          System.out.println("Error excepcion: " + error.getMessage());
          return Mono.error(
            new Exception("Error inesperado al crear el usuario", error)
          );
        }
      );
  }

  public Flux<ExchangeRateAudit> getAudits() {
    return exchangeRateauditRepository.findAll();
  }

  public Mono<ExchangeRateAudit> getAuditsByUser(Long id) {
    return exchangeRateauditRepository
      .findById(id)
      .onErrorResume(
        TransientDataAccessResourceException.class,
        error -> {
          // Manejo específico para TransientDataAccessResourceException
          return Mono.error(
            new Exception("No se pudo encontrar el usuario", error)
          );
        }
      )
      .onErrorResume(
        Exception.class,
        error -> {
          // Manejo genérico para otros errores
          return Mono.error(
            new Exception("Error inesperado al buscar el usuario", error)
          );
        }
      );
  }
}
