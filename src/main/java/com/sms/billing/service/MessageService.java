package com.sms.billing.service;

import com.sms.billing.Entity.Message;
import com.sms.billing.dto.MessageRequest;
import com.sms.billing.dto.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse sendMessage(MessageRequest request);
    List<Message> getAllMessages(Long customerId);
    long getMessageCountForCurrentMonth(Long customerId);
}
