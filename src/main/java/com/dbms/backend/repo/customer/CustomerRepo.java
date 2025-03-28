package com.dbms.backend.repo.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.dbms.backend.models.customer.CustomerDetails;
import com.dbms.backend.models.customer.CustomerDetailsRowMapper;
@Repository
public class CustomerRepo {

    JdbcTemplate jdbcTemplate;

    public CustomerRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CustomerDetails> getCustomer(int user_id) {
        try{
            String sql = "SELECT * FROM customer WHERE user_id = ?";
            return jdbcTemplate.query(sql,new CustomerDetailsRowMapper(), user_id);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addCustomer(CustomerDetails customerDetails, int user_id) {
        try{
            System.out.println(customerDetails);
            String sql = "INSERT INTO customer (name, address, email, phone_number, points, user_id) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, customerDetails.name(), customerDetails.address(), customerDetails.email(), customerDetails.phone_number(), customerDetails.points(), user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomer(int id, CustomerDetails customerDetails, int user_id) {
        try{
            System.out.println(customerDetails);
            String sql = "UPDATE customer SET name = ?, address = ?, email = ?, phone_number = ?, points = ? WHERE id = ? AND user_id = ?";
            jdbcTemplate.update(sql,customerDetails.name(), customerDetails.address(), customerDetails.email(), customerDetails.phone_number(), customerDetails.points() , id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomerById(int cust_id, int user_id) {
        try{
            String sql = "DELETE FROM customer WHERE id = ? AND user_id = ?";
            jdbcTemplate.update(sql,cust_id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
