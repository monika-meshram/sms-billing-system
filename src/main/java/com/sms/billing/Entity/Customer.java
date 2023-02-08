package com.sms.billing.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Customer {
	@Id
	private Long id;
	private String customerName;
	private Long planId;
}

