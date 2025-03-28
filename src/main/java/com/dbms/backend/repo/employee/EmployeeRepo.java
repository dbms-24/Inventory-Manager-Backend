package com.dbms.backend.repo.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dbms.backend.models.employee.EmployeeAttendance;
import com.dbms.backend.models.employee.EmployeeCompleteDetails;
import com.dbms.backend.models.employee.EmployeeDetails;
import com.dbms.backend.models.employee.EmployeeDetailsRowMapper;
import com.dbms.backend.models.employee.EmployeeEmail;
import com.dbms.backend.models.employee.EmployeePhone;

@Repository
public class EmployeeRepo {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<EmployeeDetails> getEmployeeDetails(int user_id) {
        try{
            String sql = "SELECT * FROM employee WHERE user_id = ?";
            return jdbcTemplate.query(sql, new EmployeeDetailsRowMapper(), user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmployee(EmployeeDetails employeeDetails, int user_id) {
        try{
            String sql = "INSERT INTO employee (name, role, address, join_date, salary, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, employeeDetails.name(), employeeDetails.role(), employeeDetails.address(), employeeDetails.join_date(), employeeDetails.salary(), user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<EmployeeCompleteDetails> getEmployeeDetailsById(int id, int user_id){
        try{
            String sql = "SELECT * FROM employee WHERE id = ? AND user_id = ?";
            String sqlPhones = "SELECT * FROM employee_phone WHERE employee_id = ?";
            String sqlEmails = "SELECT * FROM employee_email WHERE employee_id = ?";
            String sqlAttendance = "SELECT * FROM employee_leave WHERE employee_id = ?";
            EmployeeDetails employeeDetails = jdbcTemplate.query(sql, new EmployeeDetailsRowMapper(), id, user_id).stream().findFirst().orElse(null);
            if(employeeDetails == null){
                return Optional.empty();
            }
            List<EmployeePhone> phones = jdbcTemplate.query(sqlPhones, (rs, rowNum) -> new EmployeePhone(rs.getInt("id"),rs.getInt("employee_id"), rs.getString("phone_number")), id);
            List<EmployeeEmail> emails = jdbcTemplate.query(sqlEmails, (rs, rowNum)-> new EmployeeEmail(rs.getInt("id"),rs.getInt("employee_id"), rs.getString("email")), id);
            List<EmployeeAttendance> leaves = jdbcTemplate.query(sqlAttendance, (rs, rowNum)-> new EmployeeAttendance(rs.getInt("id"),rs.getInt("employee_id"), rs.getDate("leave_date").toLocalDate()), id);

            return Optional.of(new EmployeeCompleteDetails(employeeDetails, phones, emails, leaves));
        }catch (Exception e) {
            System.out.println("At repo layer");
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public void deleteEmployeeById(int id, int user_id) {
        try{
            String sql = "DELETE FROM employee WHERE id = ? AND user_id = ?";
            String sqlPhones = "DELETE FROM employee_phone WHERE employee_id = ?";
            String sqlEmails = "DELETE FROM employee_email WHERE employee_id = ?";
            String sqlDates = "DELETE FROM employee_leave WHERE employee_id = ?";
            // Deleting Phone numbers, emails and leave dates of the employee when he gets deleted
            // TODO: Need to check if the employee exist or not --> For validation
            jdbcTemplate.update(sqlPhones, id);
            jdbcTemplate.update(sqlEmails, id);
            jdbcTemplate.update(sqlDates, id);
            jdbcTemplate.update(sql, id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmployeeDetailsById(int id, EmployeeDetails employeeDetails, int user_id) {
        try{
            String sql = "UPDATE employee SET name = ?, role = ?, address = ?, join_date = ?, salary = ? WHERE id = ? AND user_id = ?";
            jdbcTemplate.update(sql, employeeDetails.name(), employeeDetails.role(), employeeDetails.address(), employeeDetails.join_date(), employeeDetails.salary(), id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Employee Attendance Repo

    public List<EmployeeAttendance> getEmployeeAttendanceById(int id) {
        try{
            String sql = "SELECT * FROM employee_leave WHERE employee_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new EmployeeAttendance(rs.getInt("id"),rs.getInt("employee_id"), rs.getDate("leave_date").toLocalDate()), id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addEmployeeAttendanceById(int id, EmployeeAttendance employeeAttendance) {
        if(id != employeeAttendance.employee_id()) {
            throw new IllegalArgumentException("Employee id in the path and body do not match");
        }
        try{
            String sql = "INSERT INTO employee_leave (employee_id, leave_date) VALUES (?, ?)";
            jdbcTemplate.update(sql, id, employeeAttendance.leave_date());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Phone Repo

    public void addEmployeePhoneById(int id, EmployeePhone employeePhone) {
        if(id != employeePhone.employee_id()) {
            throw new IllegalArgumentException("Employee id in the path and body do not match");
        }
        try{
            String sql = "INSERT INTO employee_phone (employee_id, phone_number) VALUES (?, ?)";
            jdbcTemplate.update(sql, id, employeePhone.phone());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EmployeePhone> getEmployeePhoneById(int id) {
        try{
            String sql = "SELECT * FROM employee_phone WHERE employee_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new EmployeePhone(rs.getInt("id"),rs.getInt("employee_id"), rs.getString("phone_number")), id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmployeePhoneById(int emp_id, int phoneId) {
        try{
            String sql = "DELETE FROM employee_phone WHERE employee_id = ? AND id = ?";
            jdbcTemplate.update(sql, emp_id, phoneId);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Email repo
    public void addEmployeeEmailById(int id, EmployeeEmail employeeEmail) {
        if(id != employeeEmail.employee_id()) {
            throw new IllegalArgumentException("Employee id in the path and body do not match");
        }
        try{
            String sql = "INSERT INTO employee_email (employee_id, email) VALUES (?, ?)";
            jdbcTemplate.update(sql, id, employeeEmail.email());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<EmployeeEmail> getEmployeeEmailById(int id) {
        try{
            String sql = "SELECT * FROM employee_email WHERE employee_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new EmployeeEmail(rs.getInt("id"),rs.getInt("employee_id"), rs.getString("email")), id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmployeeEmailById(int emp_id, int emailId) {
        try{
            String sql = "DELETE FROM employee_email WHERE employee_id = ? AND id = ?";
            jdbcTemplate.update(sql, emp_id, emailId);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
