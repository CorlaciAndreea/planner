package com.github.corlaciandreea.planner.repository;

import com.github.corlaciandreea.planner.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningRepository extends JpaRepository<Plan, Long> {
}
