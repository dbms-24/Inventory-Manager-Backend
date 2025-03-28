package com.dbms.backend.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.backend.models.user.UserDetails;
import com.dbms.backend.models.user.UserDisplayDetails;
import com.dbms.backend.service.user.UserService;
import com.dbms.backend.utils.GetTokenInfo;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController{
  
    @Autowired
    UserService userService;
    
    @Autowired
    GetTokenInfo getTokenInfo;

    @GetMapping("/get_info")
    public ResponseEntity<UserDisplayDetails> healthy (HttpServletRequest request){
        try{
        int id = Integer.parseInt(getTokenInfo.getId(request));
        return ResponseEntity.ok(userService.getUserById(id));
        }catch(Exception e){
        return ResponseEntity.status(500).body(null);
      }
    }


    @PostMapping("/add_user")
    public ResponseEntity<Boolean> addUserByAdmin(@RequestBody UserDetails userDetails) {
        try{
            userService.addUserByAdmin(userDetails);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<Boolean> deleteUserByAdmin(@PathVariable("id") int id) {
        try{
            userService.deleteUserByAdmin(id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @GetMapping("/get_users")
    public ResponseEntity<List<UserDisplayDetails>> getUsersDisplay() {
        try{
            return ResponseEntity.ok(userService.getUsersDisplay());
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
