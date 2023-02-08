package com.sms.billing.domain;

import com.sms.billing.Entity.Plan;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class MessageInfo {

    private Long messageCount;
    private BigDecimal amount;
    private Plan plan;
}
