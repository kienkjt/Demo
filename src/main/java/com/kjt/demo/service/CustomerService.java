package com.kjt.demo.service;

import com.kjt.demo.dto.CustomerCreateRequest;
import com.kjt.demo.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerCreateRequest request);

    CustomerResponse updateCustomer(Long id, CustomerCreateRequest request);

    void deleteCustomer(Long id);

    CustomerResponse getCustomer(Long id);

    List<CustomerResponse> getAllCustomers();
}
