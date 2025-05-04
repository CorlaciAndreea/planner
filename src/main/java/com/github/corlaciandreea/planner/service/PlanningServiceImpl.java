package com.github.corlaciandreea.planner.service;

import com.github.corlaciandreea.planner.error.ValidationException;
import com.github.corlaciandreea.planner.model.DaySchedule;
import com.github.corlaciandreea.planner.model.Plan;
import com.github.corlaciandreea.planner.model.WishBookEntry;
import com.github.corlaciandreea.planner.repository.PlanningRepository;
import com.github.corlaciandreea.planner.repository.WishBookRepository;
import com.github.corlaciandreea.planner.validator.ShiftValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
import java.util.*;

@Service
public class PlanningServiceImpl implements PlanningService {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private WishBookRepository wishBookRepository;

    @Autowired
    private ShiftValidator shiftValidator;

    @Override
    public Plan savePlanPerShift(Plan plan) throws ValidationException {
        // Do not save the entry if the shift is not valid
        if (!shiftValidator.isShiftValid(plan.getShift())) {
            throw new ValidationException("The shift is not valid.");
        }

        // Validate the introduced plan
        validatePlan(plan);

        // If the plan already exists allow the admin to update the entry
        Plan existingPlan = this.planningRepository.findPlanByDateAndShift(plan.getDate(), plan.getShift());
        if (existingPlan != null) {
            plan.setPlanId(existingPlan.getPlanId());
        }

        // Check the employees have one shift per day
        checkOnlyOneEmployeePerShift(plan);

        // Save or update the plan
        return planningRepository.save(plan);
    }

    @Override
    public DaySchedule getSchedulePerDay(LocalDate date) {
        // Get all the planning for the current day
        List<Plan> shiftsForDay = this.planningRepository.findByDate(date);

        if (shiftsForDay == null) {
            throw new HttpServerErrorException(HttpStatus.NO_CONTENT, "There is no schedule for that specific date.");
        }

        // Sort the list so the shifts are early then late
        Collections.sort(shiftsForDay, new Comparator<Plan>() {
            @Override
            public int compare(Plan p1, Plan p2) {
                return p1.getShift().compareToIgnoreCase(p2.getShift());
            }
        });

        //Create the schedule
        DaySchedule daySchedule = new DaySchedule();
        daySchedule.setDate(date);
        Map<String, List<String>> shifts = new HashMap<>();
        // Add the existing shifts
        for (Plan p : shiftsForDay) {
            shifts.put(p.getShift(), p.getEmployees());
        }
        daySchedule.setShifts(shifts);

        return daySchedule;
    }

    /**
     * Method that checks if one employee has only one shift per day in the new planning.
     *
     * @param newPlan the plan to be validated.
     * @throws ValidationException if one employee.
     */
    private void checkOnlyOneEmployeePerShift(Plan newPlan) {
        // Get all the planning for the current day
        List<Plan> shiftsPerDay = this.planningRepository.findByDate(newPlan.getDate());

        for (String employee : newPlan.getEmployees()) {
            for (Plan p : shiftsPerDay) {
                // Do not take into consideration the updated plan
                if (newPlan.getPlanId().equals(p.getPlanId())) {
                    continue;
                } else {
                    for (String allocatedEmployee : p.getEmployees()) {
                        if (employee.equals(allocatedEmployee)) {
                            throw new ValidationException("The employee " + employee + "cannot allocated for the "
                                    + newPlan.getShift() + " shift, because he is already allocated for the " + p.getShift() + " shift on"
                                    + p.getDate().toString());
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks of the new plan takes into consideration the wish book entries added by the user and if there are only two
     * employees assigned per shift.
     *
     * @param plan the plan to be validated.
     * @throws ValidationException if the plan is not valid.
     */
    private void validatePlan(Plan plan) throws ValidationException {
        // Ensure that only 2 employees are included per shift
        List<String> employeesList = plan.getEmployees();
        if (employeesList.size() == 1) {
            throw new ValidationException("There must be two employees per shift.");
        } else if (employeesList.size() > 2) {
            throw new ValidationException("Only two employees per shift are allowed.");
        }
        Set<String> emplSet = new HashSet<>(employeesList);
        // Check that the employees have corresponding wishbook entries
        boolean isWishBookEntryMissing = false;
        StringBuilder errorMessage = new StringBuilder("Wish book entry does not exists for ");

        for (String empl : emplSet) {
            WishBookEntry wishbookEntry = this.wishBookRepository.findEntryByEmployeeIdAndDate(empl, plan.getDate());
            if (wishbookEntry == null || !wishbookEntry.getShift().equalsIgnoreCase(plan.getShift())) {
                errorMessage.append(empl).append(" ");
                isWishBookEntryMissing = true;
            }
        }

        if (isWishBookEntryMissing) {
            errorMessage.append(".");
            throw new ValidationException(errorMessage.toString());
        }
    }
}
