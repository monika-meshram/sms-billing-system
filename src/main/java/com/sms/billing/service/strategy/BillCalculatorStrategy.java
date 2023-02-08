package com.sms.billing.service.strategy;

import com.sms.billing.Entity.Plan;
import com.sms.billing.domain.MessageInfo;

import java.math.BigDecimal;

public interface BillCalculatorStrategy {
    MessageInfo billCalculation(MessageInfo messageInfo);
}
