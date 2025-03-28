package com.dbms.backend.controller.customer;

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
import com.dbms.backend.models.customer.CustomerDetails;
import com.dbms.backend.models.stock.StockDressDescription;
import com.dbms.backend.service.customer.CustomerService;
import com.dbms.backend.service.transaction.TransactionService;
import com.dbms.backend.utils.GetTokenInfo;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    GetTokenInfo getTokenInfo;

    @Autowired
    TransactionService transactionService;

    // Customer Routes
    @GetMapping
    public ResponseEntity<List<CustomerDetails>> getCustomer(HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            return ResponseEntity.ok(customerService.getCustomer(user_id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PostMapping
    public ResponseEntity<Boolean> addCustomer(@RequestBody CustomerDetails customerDetails, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            customerService.addCustomer(customerDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Boolean> updateCustomer(@RequestBody CustomerDetails customerDetails, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            customerService.updateCustomer(customerDetails.id(), customerDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteCustomer(@PathVariable("id") int cust_id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            customerService.deleteCustomerById(cust_id, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
    // Get inventory bought by customer 
   @GetMapping("/{cust_id}/inventory")
    public ResponseEntity<List<StockDressDescription>>  inventoryOfCustomer(@PathVariable("cust_id")int cust_id){
      try {
        return ResponseEntity.ok(transactionService.inventoryOfCustomer(cust_id));
      } catch (Exception e) {
        return ResponseEntity.status(500).body(null);
      }
  }
}
