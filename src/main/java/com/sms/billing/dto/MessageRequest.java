package com.sms.billing.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageRequest {
	private Long customerId;
	private String message;
}

