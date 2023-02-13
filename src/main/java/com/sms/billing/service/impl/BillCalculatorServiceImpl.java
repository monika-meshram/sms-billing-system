package com.sms.billing.service.impl;

import com.sms.billing.Entity.Customer;
import com.sms.billing.Entity.Plan;
import com.sms.billing.constants.BillingPlanEnum;
import com.sms.billing.domain.MessageInfo;
import com.sms.billing.dto.BillingResponse;
import com.sms.billing.service.*;
import com.sms.billing.service.strategy.BasicPlanBillCalculatorStrategy;
import com.sms.billing.service.strategy.BillCalculatorStrategy;
import com.sms.billing.service.strategy.GoldPlanBillCalculatorStrategy;
import com.sms.billing.service.strategy.SilverPlanBillCalculatorStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BillCalculatorServiceImpl implements BillCalculatorService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private CustomerService customerService;
    private MessageService messageService;
    private PlanService planService;
    private LoyaltyDiscountCalculatorService loyaltyDiscountCalculatorService;
    private BillCalculatorStrategy basicPlanBillCalculatorStrategy;
    private BillCalculatorStrategy silverPlanBillCalculatorStrategy;
    private BillCalculatorStrategy goldPlanBillCalculatorStrategy;

    public BillCalculatorServiceImpl(CustomerService customerService,
                                     MessageService messageService,
                                     PlanService planService,
                                     LoyaltyDiscountCalculatorService loyaltyDiscountCalculatorService,
                                     BillCalculatorStrategy basicPlanBillCalculatorStrategy,
                                     BillCalculatorStrategy silverPlanBillCalculatorStrategy,
                                     BillCalculatorStrategy goldPlanBillCalculatorStrategy) {
        this.customerService = customerService;
        this.messageService = messageService;
        this.planService = planService;
        this.loyaltyDiscountCalculatorService = loyaltyDiscountCalculatorService;
        this.basicPlanBillCalculatorStrategy = basicPlanBillCalculatorStrategy;
        this.silverPlanBillCalculatorStrategy = silverPlanBillCalculatorStrategy;
        this.goldPlanBillCalculatorStrategy = goldPlanBillCalculatorStrategy;
    }

    @Override
    public BillingResponse calculateBill(Long customerId){

        logger.info("Fetching Customer details for : " + customerId);
        Customer customer = customerService.getCustomerById(customerId);

        // If no customer found, break
        if(customer==null){
            //throw new RuntimeException("No Customer Found with ID :" + customerId);
            logger.info("No Customer Found with ID :" + customerId);
            return BillingResponse.builder().build();
        }

        Long totalMessages = messageService.getMessageCountForCurrentMonth(customerId);

        Plan plan = planService.getPlanById(customer.getPlanId());

        // If no Plan found, break
        if(plan==null){
            //throw new RuntimeException("No Plan Found for Customer with ID :" + customerId);
            logger.info("No Plan Found for Customer with ID :" + customerId);
            return BillingResponse.builder().build();
        }

        MessageInfo messageInfo = MessageInfo.builder().messageCount(totalMessages).plan(plan).amount(BigDecimal.ZERO).build();

        //Check for Loyalty Plan and set amount after calculating the Discount
        messageInfo = loyaltyDiscountCalculatorService.calculateDiscountedPrice(messageInfo);

        if(BillingPlanEnum.BASIC.name().equals(messageInfo.getPlan().getName())){
            CalculateBillPerPlan calculateBillPerPlan = new CalculateBillPerPlan(basicPlanBillCalculatorStrategy);
            messageInfo = calculateBillPerPlan.calculation(messageInfo);
        } else if(BillingPlanEnum.SILVER.name().equals(messageInfo.getPlan().getName())){
            CalculateBillPerPlan calculateBillPerPlan = new CalculateBillPerPlan(silverPlanBillCalculatorStrategy);
            messageInfo = calculateBillPerPlan.calculation(messageInfo);
        } else if(BillingPlanEnum.GOLD.name().equals(messageInfo.getPlan().getName())){
            CalculateBillPerPlan calculateBillPerPlan = new CalculateBillPerPlan(goldPlanBillCalculatorStrategy);
            messageInfo = calculateBillPerPlan.calculation(messageInfo);
        }

        return BillingResponse.builder()
                        .amountPerMonth(messageInfo.getAmount())
                        .customerId(customer.getId()).build();
    }

}
