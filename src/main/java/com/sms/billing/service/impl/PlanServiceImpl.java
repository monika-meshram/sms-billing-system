package com.sms.billing.service.impl;

import com.sms.billing.Entity.Plan;
import com.sms.billing.dto.MessageRequest;
import com.sms.billing.dto.MessageResponse;
import com.sms.billing.repository.PlanRepository;
import com.sms.billing.service.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlanRepository planRepository;

    @Override
    public Plan getPlanById(Long planId) {
        return planRepository.findById(planId).orElse(null);
    }
}
