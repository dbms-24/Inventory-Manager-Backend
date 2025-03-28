package com.dbms.backend.repo.transaction;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dbms.backend.models.dresses.DressDetails;
import com.dbms.backend.models.stock.StockDescriptionSupplier;
import com.dbms.backend.models.stock.StockDressDescription;
import com.dbms.backend.models.transaction.OtherTransaction;
import com.dbms.backend.models.transaction.SupplierCompleteTransaction;
import com.dbms.backend.models.transaction.SupplierTransaction;
import com.dbms.backend.models.transaction.UpdateTransactionInfo;

@Repository
public class SupplierTransactionRepo {
    private JdbcTemplate jdbcTemplate;

    SupplierTransactionRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void addSupplierTransaction(SupplierTransaction supplierTransaction, int user_id) {
        try {
            // Add in Supplier Transaction
            String supplier_transaction = "INSERT INTO supplier_transaction (supplier_id, transaction_date, amount) VALUES (?, ?, ?)";
            jdbcTemplate.update(supplier_transaction, supplierTransaction.supplier_id(), supplierTransaction.transaction_date(), supplierTransaction.amount());

            int id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM supplier_transaction", Integer.class);
            System.out.println("Id of the transaction");
            System.out.println(id);
            // Updating and adding stock to supplier and inventory to supplier_inventory

            for(StockDescriptionSupplier stockDescriptionSupplier : supplierTransaction.StockDescriptionSupplier()) {

                // Adding the quality of dress in for a supplier
                String dressQuality = "UPDATE supplier_active_dress_collection SET quality = (quality * no_of_times_bought + ?) / (no_of_times_bought + 1), no_of_times_bought = no_of_times_bought + 1 WHERE supplier_id = ? AND dress_id = ?";
                jdbcTemplate.update(dressQuality, stockDescriptionSupplier.quality(), supplierTransaction.supplier_id(), stockDescriptionSupplier.dress_id());
                // Check if stock exists such that same dress id on same date
                String checkStock = "SELECT * FROM inventory WHERE dress_id = ? AND purchase_date = ?";
                StockDescriptionSupplier stock = jdbcTemplate.query(checkStock, (rs, rowNum) -> new StockDescriptionSupplier(rs.getInt("id"), rs.getInt("dress_id"), rs.getInt("available_quantity"), rs.getDate("purchase_date").toLocalDate(), rs.getDouble("purchase_price"), rs.getDouble("selling_price"), rs.getInt("damaged_quantity"), 0), stockDescriptionSupplier.dress_id(), stockDescriptionSupplier.purchase_date()).stream().findFirst().orElse(null);
                int inventory_id;
                if(stock == null){
                    // Insert new stock
                    String insertStock = "INSERT INTO inventory (dress_id, available_quantity, purchase_date, purchase_price, selling_price, damaged_quantity) VALUES (?, ?, ?, ?, ?, ?)";
                    jdbcTemplate.update(insertStock, stockDescriptionSupplier.dress_id(), stockDescriptionSupplier.available_quantity(), stockDescriptionSupplier.purchase_date(), stockDescriptionSupplier.purchase_price(), stockDescriptionSupplier.selling_price(), stockDescriptionSupplier.damaged_quantity());
                    inventory_id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM inventory", Integer.class);
                }else {
                    // Update stock
                    String updateStock = "UPDATE inventory SET available_quantity = available_quantity + ?, damaged_quantity = damaged_quantity + ? WHERE id = ?";
                    jdbcTemplate.update(updateStock, stockDescriptionSupplier.available_quantity(), stockDescriptionSupplier.damaged_quantity(), stock.id());
                    inventory_id = stock.id(); 
                }
                System.out.println("inventory_id");
                System.out.println(inventory_id);
                // Inventory in supplier transaction
                String stockSql = "INSERT INTO inventory_in_supplier_transaction (inventory_id, supplier_transaction_id, quantity) VALUES (?, ?, ?)";
                jdbcTemplate.update(stockSql, inventory_id, id, stockDescriptionSupplier.available_quantity() + stockDescriptionSupplier.damaged_quantity());

                // Inventory bought by supplier
                String inventory_bought_by_supplier = "INSERT INTO inventory_bought_from_supplier (supplier_id, inventory_id) VALUES (?, ?)";
                jdbcTemplate.update(inventory_bought_by_supplier, supplierTransaction.supplier_id(), inventory_id);


            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


   public void updateSupplierTransactionById(int supp_id, int transaction_id, UpdateTransactionInfo transactionInfo){
    try {
      String sql = "UPDATE supplier_transaction SET amount = ?, transaction_date = ? WHERE id = ?";
      jdbcTemplate.update(sql, transactionInfo.amount(), transactionInfo.transaction_date(), transaction_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
// Delete an inventory bouhht by supplier from the inventory list  -- No NEED 
   public void deleteInventoryOfSupplier(int supp_id, int inv_id){
    try {
      String sql = "DELETE FROM inventory_bought_from_supplier WHERE inventory_id = ?, supplier_id = ?";
      jdbcTemplate.update(sql, inv_id, supp_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }


 // Getting the incentory bought by supplier 
  public List<StockDressDescription> inventoryOfSupplier(int supp_id){
    try {
      String sql = "SELECT DISTINCT i.id as id, d.id as dress_id, i.available_quantity as available_quantity, " +
                  " i.purchase_date as purchase_date, i.purchase_price as purchase_price,"+
                  " i.selling_price as selling_price, i.damaged_quantity as damaged_quantity, " +
                  " d.name as dress_name, d.brand as dress_brand, d.gender as dress_gender, d.color as dresss_color, "+ 
                  " d.size as dress_size, d.required_quantity as dress_req_quantity "+ 
                  " FROM inventory_bought_from_supplier as ibs, inventory as i, dress as d "+
                  " WHERE ibs.supplier_id = ? AND  i.id = ibs.inventory_id AND d.id = i.dress_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> 
                new StockDressDescription(rs.getInt("id"), rs.getInt("dress_id"), rs.getInt("available_quantity") 
                , rs.getDate("purchase_date").toLocalDate(), 0, rs.getDouble("purchase_price")
                , rs.getDouble("selling_price"), rs.getInt("damaged_quantity"),
                new DressDetails(rs.getInt("dress_id"), rs.getString("dress_name"), rs.getString("dress_brand")
                , rs.getString("dress_gender"), rs.getString("dress_size"), rs.getString("dress_color")
                , rs.getInt("dress_req_quantity"))), supp_id);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }



// Deleting a customer transaction 
  public void deleteSupplierTransactionById(int supp_id, int transaction_id, int user_id){
    try {
      String sql = "DELETE FROM supplier_transaction WHERE id = ? AND customer_id = ?";
      jdbcTemplate.update(sql, transaction_id, supp_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  // Delete customer transactions 
  public void deleteAllSupplierTransaction(int supp_id, int user_id){
    try{
      String sql = "DELETE FROM supplier_transaction WHERE supplier_id = ?";
      jdbcTemplate.update(sql, supp_id);
    }catch (Exception e){
      throw new RuntimeException(e);
    }
  }


    public List<StockDressDescription> getStockDressDetails(int supp_txn_id){
    try {
      String sql = "SELECT DISTINCT i.id AS id, d.id AS dress_id, i.available_quantity AS available_quantity, " +
                  " i.purchase_date AS purchase_date, ist.quantity AS quantity, i.purchase_price AS purchase_price,"+
                  " i.selling_price AS selling_price, i.damaged_quantity AS damaged_quantity, " +
                  " d.name AS dress_name, d.brand AS dress_brand, d.gender AS dress_gender, d.color AS dress_color, "+ 
                  " d.size AS dress_size, d.required_quantity AS dress_req_quantity "+ 
                  " FROM inventory_in_supplier_transaction AS ist, inventory AS i, dress AS d "+
                  " WHERE ist.supplier_transaction_id = ? AND  i.id = ist.inventory_id AND d.id = i.dress_id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> 
                new StockDressDescription(rs.getInt("id"), rs.getInt("dress_id"), rs.getInt("available_quantity") 
                , rs.getDate("purchase_date").toLocalDate(), rs.getInt("quantity"), rs.getDouble("purchase_price")
                , rs.getDouble("selling_price"), rs.getInt("damaged_quantity"),
                new DressDetails(rs.getInt("dress_id"), rs.getString("dress_name"), rs.getString("dress_brand")
                , rs.getString("dress_gender"), rs.getString("dress_size"), rs.getString("dress_color")
                , rs.getInt("dress_req_quantity"))), supp_txn_id);

    } catch (Exception e) {
      System.out.println("error at this abv");
      System.out.println(e);
      throw new RuntimeException(e);
    }
    }
    
    public List<SupplierCompleteTransaction> getTransactionsOfAllSuppliers(int user_id){
    try {
      String sql = "SELECT DISTINCT st.id as id, s.id as supplier_id, st.transaction_date as transaction_date, st.amount as amount "+
                  " FROM users as u, supplier as s, supplier_transaction as st "+
                  " WHERE u.id = ? AND u.id = s.user_id AND s.id = st.supplier_id";

      return jdbcTemplate.query(sql, (rs, rowNum)-> 
                          new SupplierCompleteTransaction(rs.getInt("id"), rs.getInt("supplier_id")
                            , rs.getDate("transaction_date").toLocalDate(), rs.getDouble("amount"), getStockDressDetails(rs.getInt("id"))), user_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

    public List<SupplierCompleteTransaction> getCompleteSupplierTransactions(int supp_id){
    try {
      String sql = "SELECT DISTINCT st.id AS id, st.supplier_id AS supplier_id ,st.transaction_date AS transaction_date, st.amount AS amount "+
                  " FROM  supplier_transaction AS st "+
                  " WHERE st.supplier_id = ?";

      return jdbcTemplate.query(sql, (rs, rowNum)-> 
                          new SupplierCompleteTransaction(rs.getInt("id"), rs.getInt("supplier_id")
                            , rs.getDate("transaction_date").toLocalDate(), rs.getDouble("amount"), getStockDressDetails(rs.getInt("id"))), supp_id);
    } catch (Exception e) {
        System.out.println(e);
        throw new RuntimeException(e);
    }
  }
  
    public List<OtherTransaction> getOtherTransaction(int user_id){
    try {
      String sql = "SELECT * FROM other_transaction WHERE user_id = ?";
      return jdbcTemplate.query(sql, (rs, rowNum)->new OtherTransaction(rs.getInt("id"), rs.getInt("user_id"), rs.getDate("transaction_date").toLocalDate(), rs.getDouble("amount"), rs.getString("description")), user_id);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
  
    public void addOtherTransaction(OtherTransaction otherTransaction, int user_id){
    try {
      String sql = "INSERT INTO other_transaction (user_id, transaction_date, amount, description ) VALUES (?, ?, ?, ?)";
      jdbcTemplate.update(sql, user_id, otherTransaction.transaction_date(), otherTransaction.amount(), otherTransaction.description());
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
}
