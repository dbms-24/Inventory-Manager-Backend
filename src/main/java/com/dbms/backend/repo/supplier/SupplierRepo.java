package com.dbms.backend.repo.supplier;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dbms.backend.models.supplier.SupplierActiveCollection;
import com.dbms.backend.models.supplier.SupplierDetails;
import com.dbms.backend.models.supplier.SupplierDetailsRowMapper;
import com.dbms.backend.models.supplier.SupplierEmail;
import com.dbms.backend.models.supplier.SupplierPhone;

import java.util.List;

@Repository
public class SupplierRepo {
    JdbcTemplate jdbcTemplate;
    public SupplierRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    // SupplierRepo

    public void addSupplier(SupplierDetails supplierDetails, int user_id) {
        try{
            String sql = "INSERT INTO supplier (name, address, user_id) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, supplierDetails.name(), supplierDetails.address(), user_id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void updateSupplierById(int id, SupplierDetails supplierDetails, int user_id) {
        try{
            String sql = "UPDATE supplier SET name = ?, address = ? WHERE id = ? AND user_id = ?";
            jdbcTemplate.update(sql, supplierDetails.name(), supplierDetails.address(), id, user_id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void deleteSupplierById(int id, int user_id) {
        try{
            String sql = "DELETE FROM supplier WHERE id = ? AND user_id = ?";
            jdbcTemplate.update(sql, id, user_id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<SupplierDetails> getSupplier(int user_id) {
        try{
            String sql = "SELECT * FROM supplier WHERE user_id = ?";
            return jdbcTemplate.query(sql, new SupplierDetailsRowMapper(), user_id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    // Supplier Emails

    public void addSupplierEmailById(int id, SupplierEmail supplierEmail) {
        if(id != supplierEmail.supplier_id()){
            throw new IllegalArgumentException("Supplier id and email supplier id do not match");
        }
        try{
            String sql = "INSERT INTO supplier_email (supplier_id, email) VALUES (?, ?)";
            jdbcTemplate.update(sql, id, supplierEmail.email());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<SupplierEmail> getSupplierEmailById(int id) {
        try{
            String sql = "SELECT * FROM supplier_email WHERE supplier_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new SupplierEmail(rs.getInt("id"), rs.getInt("supplier_id"), rs.getString("email")), id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void deleteSupplierEmailById(int id, int email_id) {
        try{
            String sql = "DELETE FROM supplier_email WHERE supplier_id = ? AND id = ?";
            jdbcTemplate.update(sql, id, email_id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    // Supplier Phone

    public void addSupplierPhoneById(int id, SupplierPhone supplierPhone){
        if(id != supplierPhone.supplier_id()){
            throw new IllegalArgumentException("Supplier id and phone supplier id do not match");
        }
        try{
            String sql = "INSERT INTO supplier_phone (supplier_id, phone) VALUES (?, ?)";
            jdbcTemplate.update(sql, id, supplierPhone.phone());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<SupplierPhone> getSupplierPhoneById(int id) {
        try{
            String sql = "SELECT * FROM supplier_phone WHERE supplier_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new SupplierPhone(rs.getInt("id"), rs.getInt("supplier_id"), rs.getString("phone")), id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void deleteSupplierPhoneById(int id, int phone_id) {
        try{
            String sql = "DELETE FROM supplier_phone WHERE supplier_id = ? AND id = ?";
            jdbcTemplate.update(sql, id, phone_id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    // Supplier Available Dress Collection -- No need for update (Update is done in transaction)

    public void addSupplierActiveDressCollection(int supplier_id,  int dress_id) {
        try{
            String sql = "INSERT INTO supplier_active_dress_collection (supplier_id, dress_id, quality, no_of_times_bought) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, supplier_id, dress_id, 0, 0);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<SupplierActiveCollection> getSupplierActiveDressCollection(int supplier_id) {
        try{
            String sql = "SELECT * FROM supplier_active_dress_collection WHERE supplier_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> new SupplierActiveCollection(rs.getInt("id"), rs.getInt("supplier_id"), rs.getInt("dress_id"), rs.getInt("quality"), rs.getInt("no_of_times_bought")), supplier_id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public void deleteSupplierActiveDressCollection(int supplier_id, int dress_id) {
        try{
            String sql = "DELETE FROM supplier_active_dress_collection WHERE supplier_id = ? AND dress_id = ?";
            jdbcTemplate.update(sql, supplier_id, dress_id);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
