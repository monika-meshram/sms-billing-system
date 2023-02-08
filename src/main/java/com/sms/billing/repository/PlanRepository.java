package com.sms.billing.repository;

import com.sms.billing.Entity.Plan;
import org.springframework.data.repository.CrudRepository;

public interface PlanRepository extends CrudRepository<Plan, Long> {

}
