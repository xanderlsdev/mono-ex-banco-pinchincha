package com.bancopichincha.exchangeapi.exchange_support_api.service;

import com.bancopichincha.exchangeapi.exchange_support_api.model.ExchangeRateAudit;
import com.bancopichincha.exchangeapi.exchange_support_api.service.ExchangeRateAuditService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    private final ExchangeRateAuditService exchangeRateAuditService;
    private final ObjectMapper objectMapper;

    public RabbitMQListener(ExchangeRateAuditService exchangeRateAuditService, ObjectMapper objectMapper) {
        this.exchangeRateAuditService = exchangeRateAuditService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "auditQueue")
    public void receiveMessage(String message) {
        try {
            // Convertimos el mensaje JSON a objeto ExchangeRateAudit
            ExchangeRateAudit audit = objectMapper.readValue(message, ExchangeRateAudit.class);
            System.out.println("📩 Mensaje recibido: " + audit);
            
            // Guardar en base de datos
            exchangeRateAuditService.saveAudit(audit)
                .subscribe(result -> System.out.println("✅ Guardado en BD: " + result));
            
        } catch (Exception e) {
            System.err.println("❌ Error procesando mensaje: " + e.getMessage());
        }
    }
}