package com.sms.billing.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BillingResponse {
	private Long customerId;
	private BigDecimal amountPerMonth;
}

