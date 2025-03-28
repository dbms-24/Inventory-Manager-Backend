package com.dbms.backend.service.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.backend.models.supplier.SupplierActiveCollection;
import com.dbms.backend.models.supplier.SupplierDetails;
import com.dbms.backend.repo.supplier.SupplierRepo;
import java.util.List;
import com.dbms.backend.models.supplier.SupplierEmail;
import com.dbms.backend.models.supplier.SupplierPhone;

@Service
public class SupplierService {
    @Autowired
    SupplierRepo supplierRepo;

    public void addSupplier(SupplierDetails supplieDetails, int user_id) {
        try{
            supplierRepo.addSupplier(supplieDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSupplierById(int id, SupplierDetails supplierDetails, int user_id) {
        try{
            supplierRepo.updateSupplierById(id, supplierDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSupplierById(int id, int user_id) {
        try{
            supplierRepo.deleteSupplierById(id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<SupplierDetails> getSupplier(int user_id) {
        try{
            return supplierRepo.getSupplier(user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Email Service

    public void addSupplierEmailById(int id, SupplierEmail supplierEmail) {
        try{
            supplierRepo.addSupplierEmailById(id, supplierEmail);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<SupplierEmail> getSupplierEmailById(int id) {
        try{
            return supplierRepo.getSupplierEmailById(id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSupplierEmailById(int id, int email_id) {
        try{
            supplierRepo.deleteSupplierEmailById(id, email_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Phone Service

    public void addSupplierPhoneById(int id, SupplierPhone supplierPhone) {
        try{
            supplierRepo.addSupplierPhoneById(id, supplierPhone);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<SupplierPhone> getSupplierPhoneById(int id) {
        try{
            return supplierRepo.getSupplierPhoneById(id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSupplierPhoneById(int id, int phone_id) {
        try{
            supplierRepo.deleteSupplierPhoneById(id, phone_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Supplier dress collection service
    
    public void addSupplierActiveDressCollection(int supplier_id, int dress_id) {
        try{
            supplierRepo.addSupplierActiveDressCollection(supplier_id, dress_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<SupplierActiveCollection> getSupplierActiveDressCollection(int supplier_id) {
        try{
            return supplierRepo.getSupplierActiveDressCollection(supplier_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSupplierActiveDressCollection(int supplier_id, int dress_id) {
        try{
            supplierRepo.deleteSupplierActiveDressCollection(supplier_id, dress_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
