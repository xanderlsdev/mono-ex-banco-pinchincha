package com.bancopichincha.exchangeapi.exchange_api.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import lombok.Data;

@Data
public class ExchangeRateAudit {

    private Long id;
    private String nombre;
    private BigDecimal montoInicial;
    private BigDecimal montoFinal;
    private BigDecimal tipoCambio;

    private LocalDateTime fechaRegistro = LocalDateTime.now(); 
}