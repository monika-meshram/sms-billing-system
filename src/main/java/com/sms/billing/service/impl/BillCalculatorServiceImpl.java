package com.sms.billing.service.impl;

import com.sms.billing.constants.BillingPlanEnum;
import com.sms.billing.Entity.Customer;
import com.sms.billing.Entity.Message;
import com.sms.billing.Entity.Plan;
import com.sms.billing.domain.MessageInfo;
import com.sms.billing.dto.BillingResponse;
import com.sms.billing.service.*;
import com.sms.billing.service.strategy.BasicPlanBillCalculatorStrategy;
import com.sms.billing.service.strategy.GoldPlanBillCalculatorStrategy;
import com.sms.billing.service.strategy.SilverPlanBillCalculatorStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BillCalculatorServiceImpl implements BillCalculatorService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PlanService planService;

    @Autowired
    private LoyaltyDiscountCalculatorService loyaltyDiscountCalculatorService;

    @Override
    public BillingResponse calculateBill(Long customerId){

        logger.info("Fetching Customer details for : " + customerId);
        Customer customer = customerService.getCustomerById(customerId);

        // If no customer found, break
        if(customer==null){
            new RuntimeException("No Customer Found with ID :" + customer.getId());
        }

        Long totalMessages = messageService.getMessageCountForCurrentMonth(customerId);

        Plan plan = planService.getPlanById(customer.getPlanId());
        MessageInfo messageInfo = MessageInfo.builder().messageCount(totalMessages).plan(plan).amount(BigDecimal.ZERO).build();

        logger.info("Before loy MessageInfo" + messageInfo);

        //Check for Loyalty Plan and set amount after calculating the Discount
        messageInfo = loyaltyDiscountCalculatorService.calculateDiscountedPrice(messageInfo);
        //BigDecimal amount = messageInfo.getAmount();

        //plan = planService.getPlanById(customer.getPlanId());
        //messageInfo.setPlan(plan);

        logger.info("After loy MessageInfo" + messageInfo);

        if(BillingPlanEnum.BASIC.name().equals(messageInfo.getPlan().getName())){
            CalculateBillPerPlan calculateBillPerPlan = new CalculateBillPerPlan(new BasicPlanBillCalculatorStrategy());
            messageInfo = calculateBillPerPlan.calculation(messageInfo);
        } else if(BillingPlanEnum.SILVER.name().equals(messageInfo.getPlan().getName())){
            CalculateBillPerPlan calculateBillPerPlan = new CalculateBillPerPlan(new SilverPlanBillCalculatorStrategy());
            messageInfo = calculateBillPerPlan.calculation(messageInfo);
        } else if(BillingPlanEnum.GOLD.name().equals(messageInfo.getPlan().getName())){
            CalculateBillPerPlan calculateBillPerPlan = new CalculateBillPerPlan(new GoldPlanBillCalculatorStrategy());
            messageInfo = calculateBillPerPlan.calculation(messageInfo);
        }

        logger.info("After GOLD MessageInfo" + messageInfo);

        return BillingResponse.builder()
                        .amountPerMonth(messageInfo.getAmount())
                        .customerId(customer.getId()).build();
    }

}
