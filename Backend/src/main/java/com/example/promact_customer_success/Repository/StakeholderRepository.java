package com.example.promact_customer_success.Repository;

import com.example.promact_customer_success.Entity.Project;
import com.example.promact_customer_success.Entity.Stakeholder;

import java.util.List;

public interface StakeholderRepository extends CrudRepository<Stakeholder, Integer> {
    Iterable<Stakeholder> findByProjectId(Integer projectId);
}