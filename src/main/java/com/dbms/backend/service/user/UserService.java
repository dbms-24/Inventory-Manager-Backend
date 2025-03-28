package com.dbms.backend.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.backend.models.user.UserDetails;
import com.dbms.backend.models.user.UserDisplayDetails;
import com.dbms.backend.repo.user.UserRepo;

@Service
public class UserService{
    
    @Autowired
    UserRepo userRepo;

    public void addUserByAdmin(UserDetails userDetails) {
        try{
            userRepo.addUserByAdmin(userDetails);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

     public void updateUserNameById(int id, String name) {
        try{
            userRepo.updateUserNameById(id, name);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
       public void updateUserPasswordById(int id, String password) {
        try{
            userRepo.updateUserPasswordById(id, password);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }  
    public void deleteUserByAdmin(int id) {
        try{
            userRepo.deleteUserByAdmin(id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserDisplayDetails> getUsersDisplay() {
        try{
            List<UserDisplayDetails> userDisplayDetails = userRepo.getUsersDisplay();
            return userDisplayDetails;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserDisplayDetails getUserById(int id){
      try{
        return userRepo.getUserById(id);
      }catch(Exception e){
      throw new RuntimeException(e);
    }
    }

}
