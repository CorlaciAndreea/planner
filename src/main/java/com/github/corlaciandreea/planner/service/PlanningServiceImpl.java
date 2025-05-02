package com.github.corlaciandreea.planner.service;

import com.github.corlaciandreea.planner.error.ValidationException;
import com.github.corlaciandreea.planner.model.Plan;
import com.github.corlaciandreea.planner.model.WishBookEntry;
import com.github.corlaciandreea.planner.repository.PlanningRepository;
import com.github.corlaciandreea.planner.repository.WishBookRepository;
import com.github.corlaciandreea.planner.validator.ShiftValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (existingPlan.getPlanId() != null) {
            plan.setPlanId(existingPlan.getPlanId());
            // Update the employee list
            Set<String> emplSet = new HashSet<>(existingPlan.getEmployees());
            emplSet.addAll(plan.getEmployees());
            plan.setEmployees(emplSet.stream().toList());
        }

        // Check the employees have one shift per day
        checkOnlyOneEmployeePerShift(plan);

        // Save or update the plan
        return planningRepository.save(plan);
    }

    private void checkOnlyOneEmployeePerShift(Plan plan) {
        // Get all the shifts for the current day
        List<Plan> shiftsPerDay = this.planningRepository.findByDate(plan.getDate());

        // Add all employees to a set
        Set<String> allEmployees = new HashSet<>();
        int counter = 0;
        for (Plan p : shiftsPerDay) {
            Plan existingPlan = this.planningRepository.findPlanByDateAndShift(plan.getDate(), plan.getShift());
            if (existingPlan.getPlanId().equals(p.getPlanId())) {
                //If the plan is only updated use the new employees values
                p.setEmployees(plan.getEmployees());
            } else {
                //Otherwise take into consideration the current plan
                allEmployees.addAll(plan.getEmployees());
                counter += plan.getEmployees().size();
            }

            allEmployees.addAll(p.getEmployees());
            counter += p.getEmployees().size();
        }

        if (allEmployees.size() != counter) {
            throw new ValidationException("There can only be one employee per shift per day.");
        }
    }

    private void validatePlan(Plan plan) throws ValidationException {
        // Ensure that only 2 employees are included per shift
        List<String> employeesList = plan.getEmployees();
        if (employeesList.size() > 2) {
            throw new ValidationException("Only two employees per shift are allowed");
        }
        Set<String> emplSet = new HashSet<>(employeesList);
        // Check that the employees have corresponding wishbook entries
        boolean isWishBookEntryMissing = false;
        StringBuilder errorMessage = new StringBuilder("Wish book entry does not exists for ");

        for (String empl : emplSet) {
            WishBookEntry wishbookEntry = this.wishBookRepository.findEntryByEmployeeIdAndDate(empl, plan.getDate());
            if (wishbookEntry == null) {
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
