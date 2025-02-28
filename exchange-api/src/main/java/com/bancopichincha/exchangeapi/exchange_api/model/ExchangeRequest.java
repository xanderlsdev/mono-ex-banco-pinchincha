package com.bancopichincha.exchangeapi.exchange_api.model;

import lombok.Data;

@Data
public class ExchangeRequest {
    private double monto;
    private String monedaOrigen;
    private String monedaDestino;
}
