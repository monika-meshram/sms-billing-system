package com.sms.billing.rest;

import com.sms.BillingSystemApplication;
import com.sms.billing.Entity.Message;
import com.sms.billing.dto.BillingResponse;
import com.sms.billing.dto.MessageRequest;
import com.sms.billing.dto.MessageResponse;
import com.sms.billing.repository.MessageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BillingSystemApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BillingSystemControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MessageRepository messageRepository;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    public void setup(){
        IntStream.range(0, 100010).forEach(value -> {
            Message message = Message.builder().message("message"+value)
                    .pricePerMessage(BigDecimal.valueOf(0.003)).customerId(1L)
                    .sentDate(LocalDateTime.now())
                    .build();
            messageRepository.save(message);
        });
    }

    @AfterEach
    public void tearDown(){
        messageRepository.deleteAll();
    }

    @Test
    void sendMessage() {
        MessageRequest messageRequest = MessageRequest.builder().customerId(1L).message("test msg").build();
        MessageResponse messageResponse = restTemplate.postForObject(createURLWithPort("/billingSystem/message"),messageRequest, MessageResponse.class);

        assertTrue(messageResponse.getCustomerId() == 1L);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    void getBill() {
        BillingResponse billingResponse = restTemplate.getForObject(createURLWithPort("/billingSystem/customer/1/bill"), BillingResponse.class);
        assertTrue(billingResponse.getCustomerId()== 1L);
        assertTrue("297.00500".equals(billingResponse.getAmountPerMonth().toString()));
    }
}