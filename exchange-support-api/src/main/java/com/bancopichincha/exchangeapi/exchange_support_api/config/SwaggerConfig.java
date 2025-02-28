package com.bancopichincha.exchangeapi.exchange_support_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(
        new Info()
          .title("Exchange support API")
          .version("1.0")
          .description(
            "Aplicación de soporte para almacenar datos de cliente que realizan intercambios de monedas"
          )
          .contact(
            new Contact()
              .name("Xander LS")
              .email("https://xanderls.dev/")
              .url("xanderlsdev@gmail.com")
          )
      );
  }
}
