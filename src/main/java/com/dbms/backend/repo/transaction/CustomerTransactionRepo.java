package com.dbms.backend.repo.transaction;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dbms.backend.models.dresses.DressDetails;
import com.dbms.backend.models.stock.StockDescription;
import com.dbms.backend.models.stock.StockDressDescription;
import com.dbms.backend.models.transaction.CustomerCompleteTransaction;
import com.dbms.backend.models.transaction.CustomerTransaction;
import com.dbms.backend.models.transaction.UpdateTransactionInfo;

@Repository
public class CustomerTransactionRepo {

    private JdbcTemplate jdbcTemplate;

    CustomerTransactionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void addCustomerTransaction(CustomerTransaction customerTransaction, int user_id) {
        try{
            // Adding in Customer Transaction
            String custSql = "INSERT INTO customer_transaction (customer_id, transaction_date, amount) VALUES (?, ?, ?)";
            jdbcTemplate.update(custSql, customerTransaction.customer_id(), customerTransaction.transaction_date(), customerTransaction.amount());
            int id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM customer_transaction", Integer.class);
            // Updating and adding stock to customer and inventory to customer_inventory
            for(StockDescription stockDescription : customerTransaction.StockDescription()) {

                // Inventory in customer transaction
                String stockSql = "INSERT INTO inventory_in_cust_transaction (inventory_id, customer_transaction_id, quantity) VALUES (?, ?, ?)";
                jdbcTemplate.update(stockSql, stockDescription.id(), id, stockDescription.available_quantity()+ stockDescription.damaged_quantity());

                //Inventory bought by customer
                String inventory_bought_by_customer = "INSERT INTO inventory_bought_by_customer (customer_id, inventory_id) VALUES (?, ?)";
                jdbcTemplate.update(inventory_bought_by_customer, customerTransaction.customer_id(), stockDescription.id());

                // Update stock
                String updateStock = "UPDATE inventory SET available_quantity = available_quantity - ?, damaged_quantity = damaged_quantity - ?  WHERE id = ?";
                jdbcTemplate.update(updateStock, stockDescription.available_quantity(), stockDescription.damaged_quantity(), stockDescription.id());
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
  // Updating a customer transaction
   public void updateCustomerTransactionById(int cust_id, int transaction_id, UpdateTransactionInfo transactionInfo){
    try {
      String sql = "UPDATE customer_transaction SET amount = ?, transaction_date = ? WHERE id = ?";
      jdbcTemplate.update(sql, transactionInfo.amount(), transactionInfo.transaction_date(), transaction_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
// Delete an inventory bouhht by customer from the inventory list  -- No NEED 
   public void deleteInventoryOfCustomer(int cust_id, int inv_id){
    try {
      String sql = "DELETE FROM inventory_bought_by_customer where inventory_id = ?, customer_id = ?";
      jdbcTemplate.update(sql, inv_id, cust_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }


 // Getting the incentory bought by customer
  public List<StockDressDescription> inventoryOfCustomer(int cust_id){
    try {
      String sql = "SELECT DISTINCT i.id as id, d.id as dress_id, i.available_quantity as available_quantity, " +
                  " i.purchase_date as purchase_date, i.purchase_price as purchase_price,"+
                  " i.selling_price as selling_price, i.damaged_quantity as damaged_quantity, " +
                  " d.name as dress_name, d.brand as dress_brand, d.gender as dress_gender, d.color as dress_color, "+ 
                  " d.size as dress_size, d.required_quantity as dress_req_quantity "+ 
                  " FROM inventory_bought_by_customer as ibc, inventory as i, dress as d "+
                  " WHERE ibc.customer_id = ? AND  i.id = ibc.inventory_id AND d.id = i.dress_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> 
                new StockDressDescription(rs.getInt("id"), rs.getInt("dress_id"), rs.getInt("available_quantity") 
                , rs.getDate("purchase_date").toLocalDate(), 0, rs.getDouble("purchase_price")
                , rs.getDouble("selling_price"), rs.getInt("damaged_quantity"),
                new DressDetails(rs.getInt("dress_id"), rs.getString("dress_name"), rs.getString("dress_brand")
                , rs.getString("dress_gender"), rs.getString("dress_size"), rs.getString("dress_color")
                , rs.getInt("dress_req_quantity"))), cust_id);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }


  // Get all inentory with the dress names 

  public List<StockDressDescription> getAllInventory(int user_id){
    try {
      String sql = "SELECT DISTINCT i.id as id, d.id as dress_id, i.available_quantity as available_quantity, "+
                  " i.purchase_date as purchase_date, i.purchase_price as purchase_price, " +
                  " i.selling_price as selling_price, i.damaged_quantity as damaged_quantity, "+
                  " d.name as dress_name, d.brand as dress_brand, d.gender as dress_gender, d.color as dress_color, "+ 
                  " d.size as dress_size, d.required_quantity as dress_req_quantity "+ 
                  " FROM inventory as i, dress as d"+
                  " WHERE i.dress_id = d.id AND d.user_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> 
                new StockDressDescription(rs.getInt("id"), rs.getInt("dress_id"), rs.getInt("available_quantity") 
                , rs.getDate("purchase_date").toLocalDate(), 0, rs.getDouble("purchase_price")
                , rs.getDouble("selling_price"), rs.getInt("damaged_quantity"),
                new DressDetails(rs.getInt("dress_id"), rs.getString("dress_name"), rs.getString("dress_brand")
                , rs.getString("dress_gender"), rs.getString("dress_size"), rs.getString("dress_color")
                , rs.getInt("dress_req_quantity"))), user_id);

    } catch (Exception e) {
        System.out.println(e);
        throw new RuntimeException(e);
    }
  }


// Deleting a customer transaction 
  public void deleteCustomerTransactionById(int cust_id, int transaction_id, int user_id){
    try {
      String sql = "DELETE FROM customer_transaction WHERE id = ? AND customer_id = ?";
      jdbcTemplate.update(sql, transaction_id, cust_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  // Delete customer transactions 
  public void deleteAllCustomerTransaction(int cust_id, int user_id){
    try{
      String sql = "DELETE FROM customer_transaction WHERE customer_id = ?";
      jdbcTemplate.update(sql, cust_id);
    }catch (Exception e){
      throw new RuntimeException(e);
    }
  }


    public List<StockDressDescription> getStockDressDetails(int cust_txn_id){
    try {
      String sql = "SELECT DISTINCT i.id as id, d.id as dress_id, i.available_quantity as available_quantity, " +
                  " i.purchase_date as purchase_date, ict.quantity as quantity, i.purchase_price as purchase_price,"+
                  " i.selling_price as selling_price, i.damaged_quantity as damaged_quantity, " +
                  " d.name as dress_name, d.brand as dress_brand, d.gender as dress_gender, d.color as dress_color, "+ 
                  " d.size as dress_size, d.required_quantity as dress_req_quantity "+ 
                  " FROM inventory_in_cust_transaction as ict, inventory as i, dress as d "+
                  " WHERE ict.customer_transaction_id = ? AND  i.id = ict.inventory_id AND d.id = i.dress_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> 
                new StockDressDescription(rs.getInt("id"), rs.getInt("dress_id"), rs.getInt("available_quantity") 
                , rs.getDate("purchase_date").toLocalDate(), rs.getInt("quantity"), rs.getDouble("purchase_price")
                , rs.getDouble("selling_price"), rs.getInt("damaged_quantity"),
                new DressDetails(rs.getInt("dress_id"), rs.getString("dress_name"), rs.getString("dress_brand")
                , rs.getString("dress_gender"), rs.getString("dress_size"), rs.getString("dress_color")
                , rs.getInt("dress_req_quantity"))), cust_txn_id);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    }
    
    public List<CustomerCompleteTransaction> getTransactionsOfAllCustomers(int user_id){
    try {
      String sql = "SELECT DISTINCT ct.id as id, c.id as customer_id, ct.transaction_date as transaction_date, ct.amount as amount "+
                  " FROM users as u, customer as c, customer_transaction as ct "+
                  " WHERE u.id = ? AND u.id = c.user_id AND c.id = ct.customer_id";

      return jdbcTemplate.query(sql, (rs, rowNum)-> 
                          new CustomerCompleteTransaction(rs.getInt("id"), rs.getInt("customer_id")
                            , rs.getDate("transaction_date").toLocalDate(), rs.getDouble("amount"), getStockDressDetails(rs.getInt("id"))), user_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

    public List<CustomerCompleteTransaction> getCompleteCustomerTransactions(int cust_id){
    try {
      String sql = "SELECT DISTINCT ct.id as id, ct.customer_id as customer_id ,ct.transaction_date as transaction_date, ct.amount as amount "+
                  " FROM  customer_transaction as ct "+
                  " WHERE ct.customer_id = ?";

      return jdbcTemplate.query(sql, (rs, rowNum)-> 
                          new CustomerCompleteTransaction(rs.getInt("id"), rs.getInt("customer_id")
                            , rs.getDate("transaction_date").toLocalDate(), rs.getDouble("amount"), getStockDressDetails(rs.getInt("id"))), cust_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

}
