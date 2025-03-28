package com.dbms.backend.service.dresses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.dbms.backend.models.dresses.DressDetails;
import com.dbms.backend.models.stock.StockDescription;
import com.dbms.backend.repo.dresses.DressRepo;

@Service
public class DressService {

    @Autowired
    DressRepo dressRepo;


    public void addDress(DressDetails dressDetails, int user_id) {
        try{
            dressRepo.addDress(dressDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void updateDress(int id, DressDetails dressDetails, int user_id) {
        try{
            dressRepo.updateDress(id, dressDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteDressById(int dress_id, int user_id) {
        try{
            dressRepo.deleteDressById(dress_id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<DressDetails> getDress(int user_id) {
        try{
            return dressRepo.getDress(user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<StockDescription> getDressStockById(int id, int user_id) {
        try{
            return dressRepo.getDressStockById(id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addStockToDress(int dress_id, StockDescription stockDetails, int user_id) {
        try{
            dressRepo.addStockToDress(dress_id, stockDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void updateStockById(int dress_id, int stock_id, StockDescription stockDetails, int user_id) {
        try{
            dressRepo.updateStockById(dress_id, stock_id, stockDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteStockById(int dress_id, int stock_id, int user_id) {
        try{
            dressRepo.deleteStockById(dress_id, stock_id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
