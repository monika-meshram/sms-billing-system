package com.sms.billing.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
	@Id
	private Long id;
	private String name;
	@Column(precision = 10, scale = 5)
	private BigDecimal pricePerMessage;
	private Long freeMessageCount;
}
