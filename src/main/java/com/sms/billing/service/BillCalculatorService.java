package com.sms.billing.service;

import com.sms.billing.dto.BillingResponse;

import java.math.BigDecimal;

public interface BillCalculatorService {

    BillingResponse calculateBill(Long customerId);
}
