package com.dbms.backend.service.auth;

import com.dbms.backend.config.JwtService;
import com.dbms.backend.models.user.User;
import com.dbms.backend.models.auth.AuthenticationRequest;
import com.dbms.backend.models.auth.AuthenticationResponse;
import com.dbms.backend.models.auth.RegisterRequest;
import com.dbms.backend.models.user.Role;
import com.dbms.backend.repo.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepo repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  

  public AuthenticationResponse register(RegisterRequest request) {
    var user = User.builder()
        .name(request.getName())
        .phone_number(request.getPhone_number())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    repository.addUser(user);
    var db_user = repository.findByPhone(request.getPhone_number());
    var jwtToken = jwtService.generateToken(db_user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    try{
     authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getPhone_number(),
            request.getPassword()
        )
    );
    }catch(Exception e){
      System.out.println("error Auth Manager");
      System.out.println(e);
    }
    var user = repository.findByPhone(request.getPhone_number());
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
  public void updateUserPassword(int id, String password){
    String hashedPassword = passwordEncoder.encode(password);
    repository.updateUserPasswordById(id, hashedPassword);
  }
}
