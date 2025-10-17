package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.model.Customer;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CustomerService {
    private List<Customer> customers = new ArrayList<>();
    private Long nextId = 1L;

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public Customer getCustomerById(Long id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Customer createCustomer(Customer customer) {
        customer.setId(nextId++);
        customers.add(customer);
        return customer;
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = getCustomerById(id);
        if (customer != null) {
            customer.setName(customerDetails.getName());
            customer.setEmail(customerDetails.getEmail());
        }
        return customer;
    }

    public boolean deleteCustomer(Long id) {
        return customers.removeIf(c -> c.getId().equals(id));
    }
}