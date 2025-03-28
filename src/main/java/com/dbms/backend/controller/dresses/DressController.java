package com.dbms.backend.controller.dresses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbms.backend.service.dresses.DressService;
import com.dbms.backend.service.transaction.TransactionService;
import com.dbms.backend.utils.GetTokenInfo;

import jakarta.servlet.http.HttpServletRequest;

import com.dbms.backend.models.dresses.DressDetails;
import com.dbms.backend.models.stock.StockDescription;
import com.dbms.backend.models.stock.StockDressDescription;

import java.util.List;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
@RequestMapping("/dresses")
public class DressController {
    
    @Autowired
    DressService dressService;
    @Autowired
    GetTokenInfo getTokenInfo;

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<DressDetails>> getDress(HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            return ResponseEntity.ok(dressService.getDress(user_id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> addDress(@RequestBody DressDetails dressDetails, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            dressService.addDress(dressDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Boolean> updateDress(@PathVariable("id") int id, @RequestBody DressDetails dressDetails, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            dressService.updateDress(id, dressDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

     @DeleteMapping("/{dress_id}")
    public ResponseEntity<Boolean> deleteDressById(@PathVariable("dress_id") int dress_id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            dressService.deleteDressById(dress_id, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }   

    @GetMapping("/stock")
    public ResponseEntity<List<StockDressDescription>> getAllInventory(HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            return ResponseEntity.ok(transactionService.getAllInventory(user_id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/{id}/stock")
    public ResponseEntity<List<StockDescription>> getDressStockById(HttpServletRequest request, @PathVariable("id") int id) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            return ResponseEntity.ok(dressService.getDressStockById(id, user_id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/{id}/stock")
    public ResponseEntity<Boolean> addStockToDress(@RequestBody StockDescription stockDetails, HttpServletRequest request, @PathVariable("id") int dress_id) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            dressService.addStockToDress(dress_id, stockDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
    // Update stock of a dress and
    @PutMapping("/{dress_id}/stock/{stock_id}")
    public ResponseEntity<Boolean> updateStockById(@PathVariable("dress_id") int dress_id, @PathVariable("stock_id") int stock_id, @RequestBody StockDescription stockDetails, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            dressService.updateStockById(dress_id, stock_id, stockDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
    // Can dleete incentory if there are no transactions involved with it 

     @DeleteMapping("/{dress_id}/stock/{stock_id}")
    public ResponseEntity<Boolean> deleteStockById(@PathVariable("dress_id") int dress_id, @PathVariable("stock_id") int stock_id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            dressService.deleteStockById(dress_id, stock_id, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }   

}
