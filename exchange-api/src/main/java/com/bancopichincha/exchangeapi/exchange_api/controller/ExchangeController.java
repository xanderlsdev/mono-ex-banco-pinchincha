package com.bancopichincha.exchangeapi.exchange_api.controller;

import com.bancopichincha.exchangeapi.exchange_api.model.ExchangeRateAudit;
import com.bancopichincha.exchangeapi.exchange_api.model.ExchangeRequest;
import com.bancopichincha.exchangeapi.exchange_api.model.ExchangeResponse;
import com.bancopichincha.exchangeapi.exchange_api.service.ExcharngeService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

  private final ExcharngeService exchangeService;

  @PostMapping("/convert")
  public Mono<ExchangeResponse> convert(
    @RequestBody ExchangeRequest request,
    ServerWebExchange exchange
  ) {
    final double tipoCambio = 3.85;

    double montoFinal = request.getMonto() * tipoCambio;

    ExchangeResponse response = new ExchangeResponse(
      request.getMonto(),
      montoFinal,
      request.getMonedaOrigen(),
      request.getMonedaDestino()
    );

    String userName = exchange
      .getRequest()
      .getHeaders()
      .getFirst("X-User-Name");

    ExchangeRateAudit exchangeRateAudit = new ExchangeRateAudit();
    exchangeRateAudit.setNombre(
      userName != null ? userName : "Banco Pichincha"
    );
    exchangeRateAudit.setMontoInicial(new BigDecimal(request.getMonto()));
    exchangeRateAudit.setMontoFinal(new BigDecimal(montoFinal));
    exchangeRateAudit.setTipoCambio(new BigDecimal(tipoCambio));
    exchangeRateAudit.setFechaRegistro(LocalDateTime.now());

    // Mono
    //   .fromRunnable(() ->
    //     exchangeService.sendRequest(exchangeRateAudit).subscribe()
    //   ) // Se ejecuta en segundo plano
    //   .subscribeOn(Schedulers.boundedElastic())
    //   .subscribe();

    exchangeService.sendToQueue(exchangeRateAudit);

    return Mono.just(response);

    // return exchangeService.sendRequest(exchangeRateAudit).then(Mono.just(response));
  }
}
