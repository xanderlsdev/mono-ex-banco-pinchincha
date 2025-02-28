package com.bancopichincha.exchangeapi.exchange_api.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String status;
}