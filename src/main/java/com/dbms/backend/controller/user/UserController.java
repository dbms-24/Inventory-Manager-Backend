package com.dbms.backend.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.backend.models.user.UserDetails;
import com.dbms.backend.service.auth.AuthenticationService;
import com.dbms.backend.service.user.UserService;
import com.dbms.backend.utils.GetTokenInfo;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController{

  @Autowired
  UserService userService;

  @Autowired
  GetTokenInfo getTokenInfo;
  
  @Autowired
  AuthenticationService authService;

    @PutMapping("/update_name")
    public ResponseEntity<Boolean> updateUserNameById(@RequestBody UserDetails userDetails, HttpServletRequest request) {
        try{
            int id = Integer.parseInt(getTokenInfo.getId(request));
            userService.updateUserNameById(id, userDetails.name());
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @PutMapping("/update_password")
    public ResponseEntity<Boolean> updateUserPasswordById(@RequestBody UserDetails userDetails, HttpServletRequest request) {
        try{
            int id = Integer.parseInt(getTokenInfo.getId(request));
            authService.updateUserPassword(id, userDetails.password());
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

}
