package com.dbms.backend.repo.dresses;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dbms.backend.models.dresses.DressDetailsRowMapper;
import com.dbms.backend.models.stock.StockDescription;
import com.dbms.backend.models.dresses.DressDetails;
import java.util.List;

@Repository
public class DressRepo {
    
    private JdbcTemplate jdbcTemplate;
    
    public DressRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    // No deletion is allowed
    public void addDress(DressDetails dressDetails, int user_id) {
        try{
            String sql = "INSERT INTO dress (name, brand, gender, size, color, required_quantity, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, dressDetails.name(), dressDetails.brand(), dressDetails.gender(), dressDetails.size(), dressDetails.color(), dressDetails.required_quantity(), user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDress(int id, DressDetails dressDetails, int user_id){
        if(id != dressDetails.id()){
            throw new IllegalArgumentException("Id in path and body do not match");
        }
        try{
            String sql = "Update dress SET name = ?, brand = ?, gender = ?, size = ?, color = ?, required_quantity = ? WHERE id = ? AND user_id = ?";
            jdbcTemplate.update(sql, dressDetails.name(), dressDetails.brand(), dressDetails.gender(), dressDetails.size(), dressDetails.color(), dressDetails.required_quantity(), id, user_id);
        }catch(Exception e){
            System.out.println("SQL Exception");
            throw new RuntimeException(e);
        }
    }
    public void deleteDressById(int dress_id, int user_id) {
        try{
            String sql ="DELETE FROM dress WHERE dress_id = ? AND user_id = ?";
            jdbcTemplate.update(sql,dress_id, user_id);
          }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<DressDetails> getDress(int user_id) {
        try{
            String sql = "SELECT * FROM dress WHERE user_id = ?";
            return jdbcTemplate.query(sql,new DressDetailsRowMapper(), user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<StockDescription> getDressStockById(int id, int user_id) {
        try{
            String sql = "SELECT * FROM inventory WHERE dress_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum)-> new StockDescription(rs.getInt("id"), rs.getInt("dress_id"), rs.getInt("available_quantity"), rs.getDate("purchase_date").toLocalDate(), rs.getDouble("purchase_price"), rs.getDouble("selling_price"), rs.getInt("damaged_quantity")), id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void addStockToDress(int dress_id, StockDescription stockDetails, int user_id) {
        try{
            String sql = "INSERT INTO inventory (dress_id, available_quantitiy, purchase_date, purchase_price, selling_price, damaged_quantity) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, stockDetails.dress_id(), stockDetails.available_quantity(), stockDetails.purchase_date(), stockDetails.purchase_price(), stockDetails.selling_price(), stockDetails.damaged_quantity());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStockById(int dress_id,int stock_id, StockDescription stockDetails, int user_id) {
        try{
            String sql = "UPDATE inventory SET available_quantitiy = ?, purchase_date = ?, purchase_price = ?, selling_price = ?, damaged_quantity = ? WHERE dress_id = ? AND id = ?";
            jdbcTemplate.update(sql, stockDetails.available_quantity(), stockDetails.purchase_date(), stockDetails.purchase_price(), stockDetails.selling_price(), stockDetails.damaged_quantity(), dress_id, stock_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStockById(int dress_id, int stock_id, int user_id) {
        try{
            String sql ="DELETE FROM inventory WHERE dress_id = ? AND id = ?";
            jdbcTemplate.update(sql,dress_id, stock_id);
          }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
