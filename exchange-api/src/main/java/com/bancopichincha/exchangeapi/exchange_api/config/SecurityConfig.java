package com.bancopichincha.exchangeapi.exchange_api.config;

import com.bancopichincha.exchangeapi.exchange_api.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtFilter jwtFilter;

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(
    ServerHttpSecurity http
  ) {
    return http
      // Desactivar CSRF para APIs REST
      .csrf()
      .disable()
      // Desactivar formulario de login por defecto
      .formLogin()
      .disable()
      // Desactivar autenticación HTTP básica
      .httpBasic()
      .disable()
      // Deshabilitar el almacenamiento de sesión, ya que usamos JWT
      .securityContextRepository(
        NoOpServerSecurityContextRepository.getInstance()
      )
      // Añadir el filtro JWT antes del filtro de autenticación
      .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
      // Configurar reglas de autorización
      .authorizeExchange()
      // Permitir acceso sin autenticación a la ruta de login
      .pathMatchers(
        "/auth/login",
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/webjars/swagger-ui/**"
      )
      .permitAll()
      // Todas las demás rutas requieren autenticación
      .anyExchange()
      .authenticated()
      .and()
      // Manejar respuestas de error
      .exceptionHandling()
      .authenticationEntryPoint((exchange, ex) -> {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      })
      .accessDeniedHandler((exchange, denied) -> {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
      })
      .and()
      .build();
  }
}
