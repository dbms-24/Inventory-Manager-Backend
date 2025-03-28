package com.dbms.backend.service.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.backend.models.transaction.CustomerCompleteTransaction;
import com.dbms.backend.models.transaction.CustomerTransaction;
import com.dbms.backend.models.transaction.OtherTransaction;
import com.dbms.backend.models.transaction.SupplierCompleteTransaction;
import com.dbms.backend.models.transaction.SupplierTransaction;
import com.dbms.backend.models.transaction.UpdateTransactionInfo;
import com.dbms.backend.repo.transaction.CustomerTransactionRepo;
import com.dbms.backend.repo.transaction.SupplierTransactionRepo;
import com.dbms.backend.models.stock.StockDressDescription;

@Service
public class TransactionService{
  @Autowired
  CustomerTransactionRepo customerTransactionRepo;

  @Autowired
  SupplierTransactionRepo supplierTransactionRepo;

  // Customer 

  // Add a customer transaciton
    public void addCustomerTransaction(CustomerTransaction customerTransaction, int user_id) {
        try{
            customerTransactionRepo.addCustomerTransaction(customerTransaction, user_id);;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

  // Delete a customer transaction
    public void deleteCustomerTransactionById(int cust_id, int trans_id, int user_id) {
        try{
            customerTransactionRepo.deleteCustomerTransactionById(cust_id, trans_id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
  
  // Delete all Customer transactions

     public void deleteAllCustomerTransaction(int cust_id, int user_id) {
        try{
            customerTransactionRepo.deleteAllCustomerTransaction(cust_id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
  // Update customer transaction
    public void updateCustomerTransactionById(int cust_id, int trans_id, UpdateTransactionInfo transactionInfo) {
        try{
            customerTransactionRepo.updateCustomerTransactionById(cust_id, trans_id, transactionInfo);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   // Getting inventory of customer  
    public List<StockDressDescription>  inventoryOfCustomer(int cust_id){
      try {
        return customerTransactionRepo.inventoryOfCustomer(cust_id);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
  }

    public List<StockDressDescription>  getAllInventory(int user_id){
      try {
        return customerTransactionRepo.getAllInventory(user_id);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
  }


 // Getting transactions of all customers of a user

    public List<CustomerCompleteTransaction> getTransactionsOfAllCustomers(int user_id){
    try {
      return customerTransactionRepo.getTransactionsOfAllCustomers(user_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
  // Get complete info of customer transactions 
    public List<CustomerCompleteTransaction> getCompleteCustomerTransactions(int cust_id){
    try {
     return customerTransactionRepo.getCompleteCustomerTransactions(cust_id);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  // Suplier 
  // Add a supplier transaciton
    public void addSupplierTransaction(SupplierTransaction supplierTransaction, int user_id) {
        try{
            supplierTransactionRepo.addSupplierTransaction(supplierTransaction, user_id);;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

  // Delete a supplier transaction
    public void deleteSupplierTransactionById(int supp_id, int trans_id, int user_id) {
        try{
            supplierTransactionRepo.deleteSupplierTransactionById(supp_id, trans_id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
  
  // Delete all supplier transactions

     public void deleteAllSupplierTransaction(int supp_id, int user_id) {
        try{
            supplierTransactionRepo.deleteAllSupplierTransaction(supp_id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
  // Update supplier transaction
    public void updateSupplierTransactionById(int supp_id, int trans_id, UpdateTransactionInfo transactionInfo) {
        try{
            supplierTransactionRepo.updateSupplierTransactionById(supp_id, trans_id, transactionInfo);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   // Getting inventory of supplier  
    public List<StockDressDescription>  inventoryOfSupplier(int supp_id){
      try {
        return supplierTransactionRepo.inventoryOfSupplier(supp_id);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
  }


 // Getting transactions of all customers of a user

    public List<SupplierCompleteTransaction> getTransactionsOfAllSuppliers(int user_id){
    try {
      return supplierTransactionRepo.getTransactionsOfAllSuppliers(user_id);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
  // Get complete info of customer transactions 
    public List<SupplierCompleteTransaction> getCompleteSupplierTransactions(int supp_id){
    try {
     return supplierTransactionRepo.getCompleteSupplierTransactions(supp_id);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

    public List<OtherTransaction> getOtherTransaction(int user_id){
    try {
     return supplierTransactionRepo.getOtherTransaction(user_id);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }
  
    public void addOtherTransaction(OtherTransaction otherTransaction, int user_id){
    try {
    supplierTransactionRepo.addOtherTransaction(otherTransaction, user_id);

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
  }

  
}
