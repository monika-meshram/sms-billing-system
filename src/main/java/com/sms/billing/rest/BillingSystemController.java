package com.sms.billing.rest;

import com.sms.billing.dto.BillingResponse;
import com.sms.billing.dto.MessageRequest;
import com.sms.billing.dto.MessageResponse;
import com.sms.billing.service.BillCalculatorService;
import com.sms.billing.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/billingSystem")
public class BillingSystemController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageService messageService;

	@Autowired
	private BillCalculatorService billCalculatorService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		logger.info("Test Method");
		return "Working";
	}

	@RequestMapping(value = "/message", method = RequestMethod.POST)
	public MessageResponse sendMessage(@RequestBody MessageRequest message) {
		logger.info("Sending Message");
		return messageService.sendMessage(message);
	}

	@RequestMapping(value = "/customer/{customerId}/bill", method = RequestMethod.GET)
	public BillingResponse getBill(@PathVariable Long customerId) {
		logger.info("Customer id" + customerId);
		return billCalculatorService.calculateBill(customerId);
	}

	
}
