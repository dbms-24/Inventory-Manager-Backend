package com.dbms.backend.service.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.dbms.backend.models.employee.EmployeeAttendance;
import com.dbms.backend.models.employee.EmployeeCompleteDetails;
import com.dbms.backend.models.employee.EmployeeDetails;
import com.dbms.backend.models.employee.EmployeeEmail;
import com.dbms.backend.models.employee.EmployeePhone;
import com.dbms.backend.repo.employee.EmployeeRepo;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;
    public List<EmployeeDetails> getEmployee(int user_id) {
        try{
            List<EmployeeDetails> employeeDetails = employeeRepo.getEmployeeDetails(user_id);
            return employeeDetails;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmployee(EmployeeDetails employeeDetails, int user_id) {
        try{
            employeeRepo.addEmployee(employeeDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EmployeeCompleteDetails getEmployeeDetailsById(int id, int user_id){
        try{
            return employeeRepo.getEmployeeDetailsById(id, user_id).orElse(null);
        }catch (Exception e) {
            System.out.println("At service layer");
            throw new RuntimeException(e);
        }
    }
    
    public void deleteEmployeeById(int id, int user_id) {
        try{
            employeeRepo.deleteEmployeeById(id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmployeeDetailsById(int id, EmployeeDetails employeeDetails, int user_id) {
        try{
            employeeRepo.updateEmployeeDetailsById(id, employeeDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Employee Attendance Service

    public List<EmployeeAttendance> getEmployeeAttendanceById(int id) {
        try{
            return employeeRepo.getEmployeeAttendanceById(id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmployeeAttendanceById(int id, EmployeeAttendance employeeAttendance) {
        try{
            employeeRepo.addEmployeeAttendanceById(id, employeeAttendance);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Phone Service

    public void addEmployeePhoneById(int id, EmployeePhone employeePhone) {
        try{
            employeeRepo.addEmployeePhoneById(id, employeePhone);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmployeePhoneById(int emp_id, int phone_id) {
        try{
            employeeRepo.deleteEmployeePhoneById(emp_id, phone_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }   

    public List<EmployeePhone> getEmployeePhoneById(int id) {
        try{
            return employeeRepo.getEmployeePhoneById(id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Email Service

    public void addEmployeeEmailById(int id, EmployeeEmail employeeEmail) {
        try{
            employeeRepo.addEmployeeEmailById(id, employeeEmail);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EmployeeEmail> getEmployeeEmailById(int id) {
        try{
            return employeeRepo.getEmployeeEmailById(id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmployeeEmailById(int emp_id, int email_id) {
        try{
            employeeRepo.deleteEmployeeEmailById(emp_id, email_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
