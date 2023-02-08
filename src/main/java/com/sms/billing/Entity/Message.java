package com.sms.billing.Entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long customerId;
	private String message;
	private LocalDateTime sentDate;

	@Column(precision = 10, scale = 5)
	private BigDecimal pricePerMessage;
}

