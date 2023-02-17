package com.sms.billing.service.impl;

import com.sms.billing.Entity.Customer;
import com.sms.billing.Entity.Message;
import com.sms.billing.Entity.Plan;
import com.sms.billing.Exceptions.CustomException;
import com.sms.billing.dto.MessageRequest;
import com.sms.billing.dto.MessageResponse;
import com.sms.billing.repository.MessageRepository;
import com.sms.billing.service.CustomerService;
import com.sms.billing.service.MessageService;
import com.sms.billing.service.PlanService;
import com.sms.billing.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageRepository messageRepository;
    private final PlanService planService;
    private final CustomerService customerService;

    public MessageServiceImpl(MessageRepository messageRepository, PlanService planService, CustomerService customerService) {
        this.messageRepository = messageRepository;
        this.planService = planService;
        this.customerService = customerService;
    }

    @Override
    public MessageResponse sendMessage(MessageRequest messageRequest) {

        Message message = Message.builder().customerId(messageRequest.getCustomerId())
                                            .message(messageRequest.getMessage())
                                            .sentDate(LocalDateTime.now()).build();

        Customer customer = customerService.getCustomerById(messageRequest.getCustomerId());

        if(customer==null){
            throw new CustomException("No Customer Found with ID :" + messageRequest.getCustomerId());
        }

        Plan plan = planService.getPlanById(customer.getPlanId());
        message.setPricePerMessage(plan!=null? plan.getPricePerMessage() : null);

        message = messageRepository.save(message);

        MessageResponse messageResponse = MessageResponse.builder()
                .pricePerMessage(message.getPricePerMessage())
                .customerId(message.getCustomerId())
                .sentDate(message.getSentDate()).build();

        logger.info("Message sent for  :" + messageRequest.getCustomerId());

        return messageResponse;
    }

    public long getMessageCountForCurrentMonth(Long customerId){
        return messageRepository
                .countByCustomerIdAndSentDateBetween(customerId, DateUtils.getStartOfCurrentMonth(), DateUtils.getEndOfCurrentMonth());
    }

}
