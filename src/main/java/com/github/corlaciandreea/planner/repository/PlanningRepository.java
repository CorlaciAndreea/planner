package com.github.corlaciandreea.planner.repository;

import com.github.corlaciandreea.planner.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PlanningRepository extends JpaRepository<Plan, Long> {

    @Query("select p from Plan p where p.date = :date and p.shift = :shift")
    Plan findPlanByDateAndShift(@Param("date") LocalDate date, @Param("shift") String shift);
}
