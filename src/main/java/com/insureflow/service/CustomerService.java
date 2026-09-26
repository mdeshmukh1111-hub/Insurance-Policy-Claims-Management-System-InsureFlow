package com.insureflow.service;

import com.insureflow.entity.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer getCustomerByCode(String code);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Long id, Customer customerDetails);
    void deleteCustomer(Long id);
    List<Customer> searchCustomers(String keyword);
}
