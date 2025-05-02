package com.github.corlaciandreea.planner.service;

import com.github.corlaciandreea.planner.model.Plan;

public interface PlanningService {
    /**
     * Saves a planning.
     *
     * @param plan the plan to be saved.
     * @return the saved plan.
     */
    Plan savePlanPerShift(Plan plan);
}
