package com.insureflow.service.impl;

import com.insureflow.entity.Customer;
import com.insureflow.exception.BadRequestException;
import com.insureflow.exception.ResourceNotFoundException;
import com.insureflow.repository.CustomerRepository;
import com.insureflow.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
    }

    @Override
    public Customer getCustomerByCode(String code) {
        return customerRepository.findByCustomerCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with code: " + code));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new BadRequestException("Customer with email '" + customer.getEmail() + "' already exists!");
        }

        if (customer.getCustomerCode() == null || customer.getCustomerCode().trim().isEmpty()) {
            customer.setCustomerCode("CUST-" + (1000 + new Random().nextInt(9000)));
        }

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer existing = getCustomerById(id);

        if (!existing.getEmail().equalsIgnoreCase(customerDetails.getEmail()) &&
                customerRepository.existsByEmail(customerDetails.getEmail())) {
            throw new BadRequestException("Customer with email '" + customerDetails.getEmail() + "' already exists!");
        }

        existing.setFirstName(customerDetails.getFirstName());
        existing.setLastName(customerDetails.getLastName());
        existing.setEmail(customerDetails.getEmail());
        existing.setPhone(customerDetails.getPhone());
        existing.setAddress(customerDetails.getAddress());
        existing.setCity(customerDetails.getCity());
        existing.setState(customerDetails.getState());
        existing.setPostalCode(customerDetails.getPostalCode());
        if (customerDetails.getStatus() != null) {
            existing.setStatus(customerDetails.getStatus());
        }

        return customerRepository.save(existing);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existing = getCustomerById(id);
        customerRepository.delete(existing);
    }

    @Override
    public List<Customer> searchCustomers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return customerRepository.findAll();
        }
        return customerRepository.searchCustomers(keyword.trim());
    }
}
