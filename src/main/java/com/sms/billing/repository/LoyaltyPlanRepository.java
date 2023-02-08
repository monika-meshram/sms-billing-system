package com.sms.billing.repository;

import com.sms.billing.Entity.LoyaltyPlan;
import org.springframework.data.repository.CrudRepository;

public interface LoyaltyPlanRepository extends CrudRepository<LoyaltyPlan, Long> {

    LoyaltyPlan findByPlanId(Long planId);

}
