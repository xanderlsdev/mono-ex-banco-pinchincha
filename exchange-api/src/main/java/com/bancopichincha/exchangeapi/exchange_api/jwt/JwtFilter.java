package com.bancopichincha.exchangeapi.exchange_api.jwt;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtFilter implements WebFilter {

  private final JwtUtil jwtUtil;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    String path = exchange.getRequest().getPath().value();

    System.out.println("Request headers: " + path);
    // Permitir acceso a la ruta de login sin token
    if (path.equals("/auth/login")) {
      System.out.println("Allowing access to login endpoint");
      return chain.filter(exchange);
    }

    String authHeader = exchange
      .getRequest()
      .getHeaders()
      .getFirst(HttpHeaders.AUTHORIZATION);

    System.out.println(authHeader);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      System.out.println("No Authorization header or invalid format");
      // No interrumpimos aquí, dejamos que Spring Security maneje esto
      return chain.filter(exchange);
    }

    String token = authHeader.substring(7); // Eliminar "Bearer "

    if (jwtUtil.validateToken(token)) {
      String email = jwtUtil.getEmailFromToken(token);
      System.out.println("Usuario autenticado: " + email);

      // Añadir email al atributo de la petición para uso posterior
      ServerHttpRequest mutatedRequest = exchange
        .getRequest()
        .mutate()
        .header("X-User-Name", email)
        .build();

      // Crear objeto Authentication para Spring Security
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        email,
        null,
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
      );

      // Continuar con la cadena de filtros y establecer el contexto de seguridad
      return chain
        .filter(exchange.mutate().request(mutatedRequest).build())
        .contextWrite(
          ReactiveSecurityContextHolder.withAuthentication(authentication)
        );
    }

    // Si el token no es válido, dejamos que Spring Security maneje el error
    System.out.println("Invalid token");
    return chain.filter(exchange);
  }
}
