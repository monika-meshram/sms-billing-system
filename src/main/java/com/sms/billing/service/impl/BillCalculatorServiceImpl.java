package com.sms.billing.service.impl;

import com.sms.billing.Entity.Customer;
import com.sms.billing.Entity.Plan;
import com.sms.billing.Exceptions.CustomException;
import com.sms.billing.constants.BillingPlanEnum;
import com.sms.billing.domain.MessageInfo;
import com.sms.billing.dto.BillingResponse;
import com.sms.billing.service.*;
import com.sms.billing.service.strategy.BillCalculatorStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumMap;

@Service
public class BillCalculatorServiceImpl implements BillCalculatorService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CustomerService customerService;
    private final MessageService messageService;
    private final PlanService planService;
    private final LoyaltyDiscountCalculatorService loyaltyDiscountCalculatorService;
    private final BillCalculatorStrategy basicPlanBillCalculatorStrategy;
    private final BillCalculatorStrategy silverPlanBillCalculatorStrategy;
    private final BillCalculatorStrategy goldPlanBillCalculatorStrategy;

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
            throw new CustomException("No Customer Found with ID :" + customerId);
        }

        Long totalMessages = messageService.getMessageCountForCurrentMonth(customerId);
        Plan plan = planService.getPlanById(customer.getPlanId());
        EnumMap<BillingPlanEnum, BillCalculatorStrategy> lookupStrategy = new EnumMap<>(BillingPlanEnum.class);
            lookupStrategy.put(BillingPlanEnum.BASIC, basicPlanBillCalculatorStrategy);
            lookupStrategy.put(BillingPlanEnum.SILVER, silverPlanBillCalculatorStrategy);
            lookupStrategy.put(BillingPlanEnum.GOLD, goldPlanBillCalculatorStrategy);

        // If no Plan found, break
        if(plan==null){
            throw new CustomException("No Plan Found for Customer with ID :" + customerId);
        }

        MessageInfo messageInfo = MessageInfo.builder().messageCount(totalMessages).plan(plan).amount(BigDecimal.ZERO).build();

        //Check for Loyalty Plan and set amount after calculating the Discount
        messageInfo = loyaltyDiscountCalculatorService.calculateDiscountedPrice(messageInfo);

        messageInfo = new CalculateBillPerPlan(lookupStrategy.get(BillingPlanEnum.valueOf(messageInfo.getPlan().getName())))
                .calculation(messageInfo);

        return BillingResponse.builder()
                        .amountPerMonth(messageInfo.getAmount())
                        .customerId(customer.getId()).build();
    }

}
