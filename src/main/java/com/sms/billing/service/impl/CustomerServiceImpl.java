package com.sms.billing.service.impl;

import com.sms.billing.Entity.Customer;
import com.sms.billing.dto.MessageRequest;
import com.sms.billing.dto.MessageResponse;
import com.sms.billing.repository.CustomerRepository;
import com.sms.billing.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

}
