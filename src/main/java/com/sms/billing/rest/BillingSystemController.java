package com.sms.billing.rest;

import com.sms.billing.dto.BillingResponse;
import com.sms.billing.dto.MessageRequest;
import com.sms.billing.dto.MessageResponse;
import com.sms.billing.service.BillCalculatorService;
import com.sms.billing.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billingSystem")
public class BillingSystemController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final MessageService messageService;
	private final BillCalculatorService billCalculatorService;

	public BillingSystemController(MessageService messageService, BillCalculatorService billCalculatorService) {
		this.messageService = messageService;
		this.billCalculatorService = billCalculatorService;
	}

	@PostMapping(value = "/message")
	public MessageResponse sendMessage(@RequestBody MessageRequest message) {
		logger.info("Sending Message");
		return messageService.sendMessage(message);
	}

	@GetMapping(value = "/customer/{customerId}/bill")
	public BillingResponse getBill(@PathVariable Long customerId) {
		logger.info("Customer id" + customerId);
		return billCalculatorService.calculateBill(customerId);
	}

	
}
