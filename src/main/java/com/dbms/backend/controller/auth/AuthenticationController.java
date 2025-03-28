package com.dbms.backend.controller.auth;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.backend.models.auth.AuthenticationRequest;
import com.dbms.backend.models.auth.AuthenticationResponse;
import com.dbms.backend.models.auth.RegisterRequest;
import com.dbms.backend.service.auth.AuthenticationService;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    try{
    System.out.println("Request Body");
    System.out.println(request);
    return ResponseEntity.ok(service.register(request));
    }catch(Exception err){
      System.out.println(err);
    return ResponseEntity.status(500).body(null);
    }
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    try{
      return ResponseEntity.ok(service.authenticate(request));
    }catch(Exception e){
      return ResponseEntity.status(403).body(null);
    }
  }
}
