package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.entity.Customer;
import org.springframework.stereotype.Service;
import com.valeri.project_RBPO.model.CustomerDto;
import com.valeri.project_RBPO.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional

public class CustomerService
{
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(UUID id)
    {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(CustomerDto customerDto)
    {
        if (customerRepository.findByEmail(customerDto.getEmail()) != null)
        {
            throw new RuntimeException("Покупатель с электронной почтой '" + customerDto.getEmail() + "' уже существует");
        }
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(UUID id, CustomerDto customerDto)
    {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null)
        {
            Customer existingCustomer = customerRepository.findByEmail(customerDto.getEmail());
            if (existingCustomer != null && !existingCustomer.getId().equals(id))
            {
                throw new RuntimeException("Email '" + customerDto.getEmail() + "' уже используется другим клиентом");
            }
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            return customerRepository.save(customer);
        }
        return null;
    }

    public boolean deleteCustomer(UUID id)
    {
        if (customerRepository.existsById(id))
        {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Customer getCustomerByEmail(String email)
    {
        return customerRepository.findByEmail(email);
    }

}