package com.bancopichincha.exchangeapi.exchange_support_api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class ExchangeRateAudit implements Serializable{

    @Id
    private Long id;
    private String nombre;
    private BigDecimal montoInicial;
    private BigDecimal montoFinal;
    private BigDecimal tipoCambio;

    @Builder.Default
    private LocalDateTime fechaRegistro = LocalDateTime.now(); 
}