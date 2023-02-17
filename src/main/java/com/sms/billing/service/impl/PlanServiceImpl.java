package com.sms.billing.service.impl;

import com.sms.billing.Entity.Plan;
import com.sms.billing.repository.PlanRepository;
import com.sms.billing.service.PlanService;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }


    @Override
    public Plan getPlanById(Long planId) {
        return planRepository.findById(planId).orElse(null);
    }
}
