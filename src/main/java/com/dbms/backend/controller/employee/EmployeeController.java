package com.dbms.backend.controller.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.backend.models.employee.EmployeeAttendance;
import com.dbms.backend.models.employee.EmployeeCompleteDetails;
import com.dbms.backend.models.employee.EmployeeDetails;
import com.dbms.backend.models.employee.EmployeeEmail;
import com.dbms.backend.models.employee.EmployeePhone;
import com.dbms.backend.service.employee.EmployeeService;
import com.dbms.backend.utils.GetTokenInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    // Employee Routes
    @Autowired
    GetTokenInfo getTokenInfo;
    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDetails>> getEmployee(HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            return ResponseEntity.ok(employeeService.getEmployee(user_id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> addEmployee(@RequestBody EmployeeDetails employeeDetails, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            employeeService.addEmployee(employeeDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }   

    @GetMapping("{id}")
    public ResponseEntity<EmployeeCompleteDetails> getEmployeeById(@PathVariable("id") int id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            EmployeeCompleteDetails employeeCompleteDetails = employeeService.getEmployeeDetailsById(id, user_id);
            if(employeeCompleteDetails == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(employeeCompleteDetails);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable("id") int id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            employeeService.deleteEmployeeById(id, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Boolean> updateEmployeeDetailsById(@PathVariable("id") int id, @RequestBody EmployeeDetails employeeDetails, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            employeeService.updateEmployeeDetailsById(id, employeeDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    // Attendance Routes 
    @GetMapping("{id}/attendance")
    public ResponseEntity<List<EmployeeAttendance>> getEmployeeAttendanceById(@PathVariable("id") int id) {
        try{
            return ResponseEntity.ok(employeeService.getEmployeeAttendanceById(id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("{id}/attendance")
    public ResponseEntity<Boolean> addEmployeeAttendanceById(@PathVariable("id") int id, @RequestBody EmployeeAttendance employeeAttendance) {
        try{
            employeeService.addEmployeeAttendanceById(id, employeeAttendance);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
    // Phone Routes

    @PostMapping("{id}/phone")
    public ResponseEntity<Boolean> addEmployeePhoneById(@PathVariable("id") int id, @RequestBody EmployeePhone employeePhone) {
        try{
            employeeService.addEmployeePhoneById(id, employeePhone);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @GetMapping("{id}/phone")
    public ResponseEntity<List<EmployeePhone>> getEmployeePhoneById(@PathVariable("id") int id) {
        try{
            return ResponseEntity.ok(employeeService.getEmployeePhoneById(id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("{id}/phone/{phone_id}")
    public ResponseEntity<Boolean> deleteEmployeePhoneById(@PathVariable("id") int emp_id, @PathVariable("phone_id") int phone_id) {
        try{
            employeeService.deleteEmployeePhoneById(emp_id, phone_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
    // Email Routes

    @PostMapping("{id}/email")
    public ResponseEntity<Boolean> addEmployeeEmailById(@PathVariable("id") int id, @RequestBody EmployeeEmail employeeEmail) {
        try{
            employeeService.addEmployeeEmailById(id, employeeEmail);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @GetMapping("{id}/email")
    public ResponseEntity<List<EmployeeEmail>> getEmployeeEmailById(@PathVariable("id") int id) {
        try{
            return ResponseEntity.ok(employeeService.getEmployeeEmailById(id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("{id}/email/{email_id}")
    public ResponseEntity<Boolean> deleteEmployeeEmailById(@PathVariable("id") int emp_id, @PathVariable("email_id") int email_id) {
        try{
            employeeService.deleteEmployeeEmailById(emp_id, email_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

}
