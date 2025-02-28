package com.bancopichincha.exchangeapi.exchange_support_api.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.bancopichincha.exchangeapi.exchange_support_api.model.ExchangeRateAudit;

@Repository
public interface ExchangeRateAuditRepository extends ReactiveCrudRepository<ExchangeRateAudit, Long> {
}
