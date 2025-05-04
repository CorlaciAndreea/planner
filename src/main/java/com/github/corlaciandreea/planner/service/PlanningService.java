package com.github.corlaciandreea.planner.service;

import com.github.corlaciandreea.planner.model.DaySchedule;
import com.github.corlaciandreea.planner.model.Plan;

import java.time.LocalDate;

public interface PlanningService {
    /**
     * Saves a planning.
     *
     * @param plan the plan to be saved.
     * @return the saved plan.
     */
    Plan savePlanPerShift(Plan plan);

    /**
     * Returns the schedule for a given day.
     *
     * @param date the day.
     * @return {@link DaySchedule} for the given date.
     */
    DaySchedule getSchedulePerDay(LocalDate date);
}
