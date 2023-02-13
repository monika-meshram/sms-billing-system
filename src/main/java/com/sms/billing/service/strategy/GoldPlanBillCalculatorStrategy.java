package com.sms.billing.service.strategy;

import com.sms.billing.Entity.Plan;
import com.sms.billing.domain.MessageInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GoldPlanBillCalculatorStrategy implements BillCalculatorStrategy {

    @Override
    public MessageInfo billCalculation(MessageInfo messageInfo) {

        if(messageInfo.getMessageCount() > messageInfo.getPlan().getFreeMessageCount()){

            return MessageInfo.builder().amount(messageInfo.getAmount().add(messageInfo.getPlan().getPricePerMessage()
                            .multiply(BigDecimal.valueOf(messageInfo.getMessageCount()-messageInfo.getPlan().getFreeMessageCount()))))
                    .plan(messageInfo.getPlan())
                    .messageCount(0L)
                    .build();
        }

        return messageInfo;
    }
}
