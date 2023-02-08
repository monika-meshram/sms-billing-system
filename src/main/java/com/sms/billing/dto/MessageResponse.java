package com.sms.billing.dto;

import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MessageResponse {
	private Long customerId;
	private LocalDateTime sentDate;
	@Column(precision = 10, scale = 5)
	private BigDecimal pricePerMessage;
}

