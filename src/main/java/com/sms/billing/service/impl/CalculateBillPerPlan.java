package com.sms.billing.service.impl;

import com.sms.billing.domain.MessageInfo;
import com.sms.billing.service.strategy.BillCalculatorStrategy;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalculateBillPerPlan {

    private BillCalculatorStrategy billCalculatorStrategy;

    public MessageInfo calculation(MessageInfo messageInfo){
        return billCalculatorStrategy.billCalculation(messageInfo);
    }
}
