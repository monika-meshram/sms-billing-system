package com.sms.billing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
	private Long customerId;
	private LocalDateTime sentDate;
	@Column(precision = 10, scale = 5)
	private BigDecimal pricePerMessage;
}

