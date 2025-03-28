package com.dbms.backend.models.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CustomerDetailsRowMapper implements RowMapper<CustomerDetails> {

    @Override
    public CustomerDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CustomerDetails(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("address"),
            rs.getString("phone_number"),
            rs.getString("email"),
            rs.getInt("points")
    );
    }
    
}
