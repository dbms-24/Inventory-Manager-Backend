package com.dbms.backend.models.user;


public record UserDetails(int id, String phone_number, String password, String role, String name){
  public UserDetails{
         if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (phone_number == null || phone_number.isBlank()) {
            throw new IllegalArgumentException("Phone Number cannot be null or empty");
        }
  }
}
