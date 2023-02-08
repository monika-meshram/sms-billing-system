package com.sms.billing.service;

import com.sms.billing.Entity.Plan;

public interface PlanService {
    Plan getPlanById(Long planId);
}
