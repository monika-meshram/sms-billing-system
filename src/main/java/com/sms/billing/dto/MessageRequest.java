package com.sms.billing.dto;

import lombok.Data;

@Data
public class MessageRequest {
	private Long customerId;
	private String message;
}

