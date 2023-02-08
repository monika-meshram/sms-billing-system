package com.sms.billing.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class LoyaltyPlan {
	@Id
	private Long id;
	private Long planId;
	private Long messageCount;

	@Column(precision = 10, scale = 5)
	private BigDecimal discountedPricePerMessage;
}
