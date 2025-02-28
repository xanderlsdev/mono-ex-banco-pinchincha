package com.bancopichincha.exchangeapi.exchange_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeResponse {
    private double montoInicial;
    private double montoFinal;
    private String monedaOrigen;
    private String monedaDestino;
}
