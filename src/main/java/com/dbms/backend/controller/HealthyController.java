package com.dbms.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.backend.models.user.UserDisplayDetails;
import com.dbms.backend.service.user.UserService;
import com.dbms.backend.utils.GetTokenInfo;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RequestMapping("/")
public class HealthyController {
  @Autowired
  GetTokenInfo getTokenInfo;
  
  @Autowired
  UserService userService;

  @GetMapping
    public ResponseEntity<UserDisplayDetails> healthy(HttpServletRequest request) {
        try{
        int id = Integer.parseInt(getTokenInfo.getId(request));
        return ResponseEntity.ok(userService.getUserById(id));
        }catch(Exception e){
        return ResponseEntity.status(500).body(null);
      }
    }
  @GetMapping("/health")
    public ResponseEntity<String> serverHealthStatus(HttpServletRequest request) {
        try{
          return ResponseEntity.ok("Healthy Server");
        }catch(Exception e){
          return ResponseEntity.status(500).body("Server Unhealthy");
      }
    }
}
