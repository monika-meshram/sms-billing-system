package com.sms.billing.service.impl;

import com.sms.billing.Entity.LoyaltyPlan;
import com.sms.billing.domain.MessageInfo;
import com.sms.billing.repository.LoyaltyPlanRepository;
import com.sms.billing.service.LoyaltyDiscountCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class LoyaltyDiscountCalculatorServiceImpl implements LoyaltyDiscountCalculatorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LoyaltyPlanRepository loyaltyPlanRepository;

    @Override
    public MessageInfo calculateDiscountedPrice(MessageInfo messageInfo){

    LoyaltyPlan loyaltyPlan = loyaltyPlanRepository.findByPlanId(messageInfo.getPlan().getId());

    if(loyaltyPlan!=null){
        if(messageInfo.getMessageCount()>loyaltyPlan.getMessageCount()){
            logger.info("Loyalty Plan available for customer for Plan : " + messageInfo.getPlan().getName());
            Long remainingMessageCount = messageInfo.getMessageCount()-loyaltyPlan.getMessageCount();
            return MessageInfo.builder()
                    .amount(loyaltyPlan.getDiscountedPricePerMessage().multiply(BigDecimal.valueOf(remainingMessageCount)))
                    .plan(messageInfo.getPlan())
                            .messageCount(loyaltyPlan.getMessageCount()).build();
        }
    }
        return messageInfo;
    }

}
