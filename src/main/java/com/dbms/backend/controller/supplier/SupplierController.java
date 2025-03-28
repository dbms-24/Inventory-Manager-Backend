package com.dbms.backend.controller.supplier;

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
import com.dbms.backend.service.supplier.SupplierService;
import com.dbms.backend.utils.GetTokenInfo;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import com.dbms.backend.models.supplier.SupplierActiveCollection;
import com.dbms.backend.models.supplier.SupplierDetails;
import com.dbms.backend.models.supplier.SupplierEmail;
import com.dbms.backend.models.supplier.SupplierPhone;

@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @Autowired
    GetTokenInfo getTokenInfo;

    @GetMapping
    public ResponseEntity<List<SupplierDetails>> getSupplier(HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            return ResponseEntity.ok(supplierService.getSupplier(user_id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> addSupplier(@RequestBody SupplierDetails supplierDetails, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            supplierService.addSupplier(supplierDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteSupplierById(@PathVariable("id") int id, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            supplierService.deleteSupplierById(id, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Boolean> updateSupplierById(@PathVariable("id") int id, @RequestBody SupplierDetails supplierDetails, HttpServletRequest request) {
        try{
            int user_id = Integer.parseInt(getTokenInfo.getId(request));
            supplierService.updateSupplierById(id, supplierDetails, user_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    // Email Routes

    @PostMapping("{id}/email")
    public ResponseEntity<Boolean> addSupplierEmailById(@PathVariable("id") int id,@RequestBody SupplierEmail supplierEmail) {
        try{
            supplierService.addSupplierEmailById(id, supplierEmail);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @GetMapping("{id}/email")
    public ResponseEntity<List<SupplierEmail>> getSupplierEmailById(@PathVariable("id") int id) {
        try{
            return ResponseEntity.ok(supplierService.getSupplierEmailById(id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("{id}/email/{email_id}")
    public ResponseEntity<Boolean> deleteSupplierEmailById(@PathVariable("id") int id, @PathVariable("email_id") int email_id) {
        try{
            supplierService.deleteSupplierEmailById(id, email_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    // Phone Routes

    @PostMapping("{id}/phone")
    public ResponseEntity<Boolean> addSupplierPhoneById(@PathVariable("id") int id, @RequestBody SupplierPhone supplierPhone) {
        try{
            supplierService.addSupplierPhoneById(id, supplierPhone);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @GetMapping("{id}/phone")
    public ResponseEntity<List<SupplierPhone>> getSupplierPhoneById(@PathVariable("id") int id) {
        try{
            return ResponseEntity.ok(supplierService.getSupplierPhoneById(id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("{id}/phone/{phone_id}")
    public ResponseEntity<Boolean> deleteSupplierPhoneById(@PathVariable("id") int id, @PathVariable("phone_id") int phone_id) {
        try{
            supplierService.deleteSupplierPhoneById(id, phone_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    // Supplier active collection

    @PostMapping("{id}/active_dress/{dress_id}")
    public ResponseEntity<Boolean> addSupplierActiveDressCollection(@PathVariable("id") int id, @PathVariable("dress_id") int dress_id) {
        try{
            supplierService.addSupplierActiveDressCollection(id, dress_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @GetMapping("{id}/active_dress")
    public ResponseEntity<List<SupplierActiveCollection>> getSupplierActiveDressCollection(@PathVariable("id") int id) {
        try{
            return ResponseEntity.ok(supplierService.getSupplierActiveDressCollection(id));
        }catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("{id}/active_dress/{dress_id}")
    public ResponseEntity<Boolean> deleteSupplierActiveDressCollection(@PathVariable("id") int id, @PathVariable("dress_id") int dress_id) {
        try{
            supplierService.deleteSupplierActiveDressCollection(id, dress_id);
            return ResponseEntity.ok(true);
        }catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }
}
