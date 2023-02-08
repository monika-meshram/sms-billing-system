package com.sms.billing.service;

import com.sms.billing.Entity.Customer;
import com.sms.billing.dto.MessageResponse;

public interface CustomerService {

    Customer getCustomerById(Long customerId);
}
