package com.valeri.project_RBPO.model;

public class Order {
    private Long id;
    private Long customerId;
    private String status;

    public Order() {}

    public Order(Long id, Long customerId, String status) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}