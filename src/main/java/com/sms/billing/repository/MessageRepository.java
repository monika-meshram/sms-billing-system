package com.sms.billing.repository;

import com.sms.billing.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MessageRepository extends JpaRepository<Message, Long> {

    long countByCustomerIdAndSentDateBetween(Long customerId, LocalDateTime first, LocalDateTime last);



}
