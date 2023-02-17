package com.sms.billing.service.strategy;

import com.sms.billing.domain.MessageInfo;

public interface BillCalculatorStrategy {
    MessageInfo billCalculation(MessageInfo messageInfo);
}
