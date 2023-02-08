package com.sms.billing.service;

import com.sms.billing.domain.MessageInfo;

public interface LoyaltyDiscountCalculatorService {
    //MessageInfo calculateDiscountedPrice(Long planId, Long totalMessages);

    MessageInfo calculateDiscountedPrice(MessageInfo messageInfo1);
}
