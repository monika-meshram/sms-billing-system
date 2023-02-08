package com.sms.billing.repository;

import com.sms.billing.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    //List<Message> findByCustomerId(Long customerId);

    //List<Message> findByCustomerIdAndSentDateBetween(Long customerId, LocalDateTime first, LocalDateTime last);
    long countByCustomerIdAndSentDateBetween(Long customerId, LocalDateTime first, LocalDateTime last);



}
