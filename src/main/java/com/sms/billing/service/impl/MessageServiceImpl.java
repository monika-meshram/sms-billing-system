package com.sms.billing.service.impl;

import com.sms.billing.Entity.Customer;
import com.sms.billing.Entity.Message;
import com.sms.billing.Entity.Plan;
import com.sms.billing.dto.MessageRequest;
import com.sms.billing.dto.MessageResponse;
import com.sms.billing.repository.MessageRepository;
import com.sms.billing.service.CustomerService;
import com.sms.billing.service.MessageService;
import com.sms.billing.service.PlanService;
import com.sms.billing.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PlanService planService;

    @Autowired
    private CustomerService customerService;

    @Override
    public MessageResponse sendMessage(MessageRequest messageRequest) {

        Message message = new Message();
        message.setCustomerId(messageRequest.getCustomerId());
        message.setMessage(messageRequest.getMessage());
        message.setSentDate(LocalDateTime.now());

        Customer customer = customerService.getCustomerById(messageRequest.getCustomerId());
        if(customer!=null){
            Plan plan = planService.getPlanById(customer.getPlanId());
            message.setPricePerMessage(plan!=null? plan.getPricePerMessage() : null);
        }

        message = messageRepository.save(message);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setPricePerMessage(message.getPricePerMessage());
        messageResponse.setCustomerId(message.getCustomerId());
        messageResponse.setSentDate(message.getSentDate());

        logger.info("Message sent for  :" + messageRequest.getCustomerId());

        return messageResponse;
    }

    public List<Message> getAllMessages(Long customerId){
        LocalDateTime datenow = LocalDateTime.now();
        Month month =  datenow.getMonth();
        int yr = datenow.getYear();
        YearMonth yearMonth = YearMonth.of(yr, month);
        LocalDate first = yearMonth.atDay(1);
        LocalDate last = yearMonth.atEndOfMonth();

        logger.info("datenow :" + datenow);
        logger.info("first :" + first);
        logger.info("last :" + last);



        //List<Message> msgs =  messageRepository.findByCustomerIdAAndSentDate_MonthAndSentDate_Year(customerId, month, yr);

        //logger.info("Messages :" + msgs);

        //return msgs;

        //List<Message> messageList = messageRepository.findByCustomerId(customerId);
//        List<Message> messageList = messageRepository
//                .findByCustomerIdAndSentDateBetween(customerId, LocalDateTime.of(first, null), LocalDateTime.of(last, null));
        //System.out.println("messageList " + messageList.size());
        //return messageList;
        return new ArrayList<>();
    }

    public long getMessageCountForCurrentMonth(Long customerId){
        return messageRepository
                .countByCustomerIdAndSentDateBetween(customerId, DateUtils.getStartOfCurrentMonth(), DateUtils.getEndOfCurrentMonth());
    }

}
