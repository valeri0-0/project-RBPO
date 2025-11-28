package com.valeri.project_RBPO.controller;

import com.valeri.project_RBPO.entity.Customer;
import com.valeri.project_RBPO.service.CustomerService;
import com.valeri.project_RBPO.model.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController
{

        private final CustomerService customerService;

        // Получить всех покупателей
        @GetMapping
        public ResponseEntity<List<Customer>> getAllCustomers()
        {
            return ResponseEntity.ok(customerService.getAllCustomers());
        }

        // Получить покупателя по ID
        @GetMapping("/{id}")
        public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id)
        {
            Customer customer = customerService.getCustomerById(id);
            return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
        }

    // Создать нового покупателя
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Обновить покупателя
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDto customerDto) {
        try {
            Customer customer = customerService.updateCustomer(id, customerDto);
            return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Удалить покупателя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id)
    {
        return customerService.deleteCustomer(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Получить покупателя по email
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }
}