package com.dbms.backend.controller.transaction;

import java.util.List;

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

import com.dbms.backend.models.transaction.CustomerCompleteTransaction;
import com.dbms.backend.models.transaction.CustomerTransaction;
import com.dbms.backend.models.transaction.OtherTransaction;
import com.dbms.backend.models.transaction.SupplierCompleteTransaction;
import com.dbms.backend.models.transaction.SupplierTransaction;
import com.dbms.backend.models.transaction.UpdateTransactionInfo;
import com.dbms.backend.service.transaction.TransactionService;
import com.dbms.backend.utils.GetTokenInfo;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    GetTokenInfo getTokenInfo;

    @Autowired 
    TransactionService transactionService;
   // Customer 
   
    // Get all customer transactions
    @GetMapping("/customer/{cust_id}")
     public ResponseEntity<List<CustomerCompleteTransaction>> getCompleteCustomerTransactions(@PathVariable("cust_id") int cust_id){
    try {
     return ResponseEntity.ok(transactionService.getCompleteCustomerTransactions(cust_id));

    } catch (Exception e) {
      return ResponseEntity.status(500).body(null);

    }
  }   

    // Add a customer tranasaction
    @PostMapping("/customer/{cust_id}")
    public ResponseEntity<Boolean> addCustomerTransaction(@PathVariable("cust_id") int cust_id, @RequestBody CustomerTransaction customerTransaction, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            transactionService.addCustomerTransaction(customerTransaction, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }   

    // Delete a customer transaction
    @DeleteMapping("/customer/{cust_id}")
    public ResponseEntity<Boolean> deleteAllCustomerTransaction(@PathVariable("cust_id") int cust_id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            transactionService.deleteAllCustomerTransaction(cust_id, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
    
    // Delete all customer transactions 
     @DeleteMapping("{trxn_id}/customer/{cust_id}")
    public ResponseEntity<Boolean> deleteCustomerTransaction(@PathVariable("cust_id") int cust_id, @PathVariable("trxn_id") int trxn_id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            transactionService.deleteCustomerTransactionById(cust_id, trxn_id, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    } 
   

    // Update a customer transaction
    @PutMapping("{trxn_id}/customer/{cust_id}")
    public ResponseEntity<Boolean> updateCustomerTransactionById(@PathVariable("cust_id") int cust_id, @PathVariable("trxn_id") int trxn_id, @RequestBody UpdateTransactionInfo transactionInfo, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            transactionService.updateCustomerTransactionById(cust_id, trxn_id, transactionInfo);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
    // Get all the user transactions of the a user 
    @GetMapping("/customer")
     public ResponseEntity<List<CustomerCompleteTransaction>> getTransactionsOfAllCustomers(HttpServletRequest request){
    try { 
      int user_id = Integer.parseInt(getTokenInfo.getId(request));
      return ResponseEntity.ok(transactionService.getTransactionsOfAllCustomers(user_id));
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);
    }
  }


  // Supplier
     @GetMapping("/supplier/{supp_id}")
     public ResponseEntity<List<SupplierCompleteTransaction>> getCompleteSupplierTransactions(@PathVariable("supp_id") int supp_id){
    try {
     return ResponseEntity.ok(transactionService.getCompleteSupplierTransactions(supp_id));

    } catch (Exception e) {
      return ResponseEntity.status(500).body(null);

    }
  }   

    // Add a supplier tranasaction
    @PostMapping("/supplier/{supp_id}")
    public ResponseEntity<Boolean> addSupplierTransaction(@PathVariable("supp_id") int supp_id, @RequestBody SupplierTransaction supplierTransaction, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            transactionService.addSupplierTransaction(supplierTransaction, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }   

    // Delete all supplier transaction
    @DeleteMapping("/supplier/{supp_id}")
    public ResponseEntity<Boolean> deleteAllSupplierTransaction(@PathVariable("supp_id") int supp_id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            transactionService.deleteAllSupplierTransaction(supp_id, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
    
    // Delete a supplier  transactions 
     @DeleteMapping("{trxn_id}/supplier/{supp_id}")
    public ResponseEntity<Boolean> addSupplierTransaction(@PathVariable("supp_id") int supp_id, @PathVariable("trxn_id") int trxn_id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            transactionService.deleteSupplierTransactionById(supp_id, trxn_id, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    } 
   

    // Update a supplier transaction
    @PutMapping("{trxn_id}/supplier/{cust_id}")
    public ResponseEntity<Boolean> updateSupplierTransactionById(@PathVariable("supp_id") int supp_id, @PathVariable("trxn_id") int trxn_id, @RequestBody UpdateTransactionInfo transactionInfo, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            transactionService.updateSupplierTransactionById(supp_id, trxn_id, transactionInfo);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
    // Get all the supplier transactions of the a user 
    @GetMapping("/supplier")
     public ResponseEntity<List<SupplierCompleteTransaction>> getTransactionsOfAllSuppliers(HttpServletRequest request){
    try { 
      int user_id = Integer.parseInt(getTokenInfo.getId(request));
      return ResponseEntity.ok(transactionService.getTransactionsOfAllSuppliers(user_id));
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);
    }
  }
  // Other transactions 
    @GetMapping("/other_transaction")
    public ResponseEntity<List<OtherTransaction>> getOtherTransaction(HttpServletRequest request){
    try { 
      int user_id = Integer.parseInt(getTokenInfo.getId(request));
      return ResponseEntity.ok(transactionService.getOtherTransaction(user_id));
    } catch (Exception e) {
        return ResponseEntity.status(500).body(null);
    }
  }

    @PostMapping("/other_transaction")
    public ResponseEntity<Boolean> addOtherTransaction(HttpServletRequest request, @RequestBody OtherTransaction otherTransaction){
    try { 
      int user_id = Integer.parseInt(getTokenInfo.getId(request));
      transactionService.addOtherTransaction(otherTransaction, user_id);
      return ResponseEntity.ok(true);
    } catch (Exception e) {
        return ResponseEntity.status(500).body(false);
    }
  }

}
