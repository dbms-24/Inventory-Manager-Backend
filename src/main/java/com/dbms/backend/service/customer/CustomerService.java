package com.dbms.backend.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.dbms.backend.models.customer.CustomerDetails;
import com.dbms.backend.repo.customer.CustomerRepo;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    public List<CustomerDetails> getCustomer(int user_id) {
        try{
            return customerRepo.getCustomer(user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addCustomer(CustomerDetails customerDetails, int user_id) {
        try{
            customerRepo.addCustomer(customerDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomer(int id, CustomerDetails customerDetails, int user_id) {
        try{
            customerRepo.updateCustomer(id, customerDetails, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomerById(int cust_id, int user_id) {
        try{
            customerRepo.deleteCustomerById(cust_id, user_id);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
