package com.sms.billing.service.impl;

import com.sms.billing.Entity.Plan;
import com.sms.billing.domain.MessageInfo;
import com.sms.billing.service.strategy.BillCalculatorStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@AllArgsConstructor
public class CalculateBillPerPlan {

    private BillCalculatorStrategy billCalculatorStrategy;

    public MessageInfo calculation(MessageInfo messageInfo){
        return billCalculatorStrategy.billCalculation(messageInfo);
    }

}
