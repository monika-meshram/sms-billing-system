package com.sms.billing.service;

import com.sms.billing.dto.MessageRequest;
import com.sms.billing.dto.MessageResponse;

public interface MessageService {
    MessageResponse sendMessage(MessageRequest request);
    long getMessageCountForCurrentMonth(Long customerId);
}
