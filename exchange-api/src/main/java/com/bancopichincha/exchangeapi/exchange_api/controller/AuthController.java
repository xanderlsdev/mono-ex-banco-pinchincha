package com.bancopichincha.exchangeapi.exchange_api.controller;

import com.bancopichincha.exchangeapi.exchange_api.jwt.JwtUtil;
import com.bancopichincha.exchangeapi.exchange_api.model.AuthResponse;
import com.bancopichincha.exchangeapi.exchange_api.model.LoginRequest;
import com.bancopichincha.exchangeapi.exchange_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  @Autowired
  private UserService userService;

  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public Mono<ResponseEntity<AuthResponse>> auth(
    @RequestBody LoginRequest request
  ) {
    return userService
      .validarUser(request.getId())
      // Si el usuario existe, generamos el token
      .flatMap(user ->
        Mono.just(
          ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(user)))
        )
      )
      // Si el usuario no existe, 400
      .switchIfEmpty(
        Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST))
      );
  }
}
