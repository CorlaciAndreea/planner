package com.github.corlaciandreea.planner.validator;

import org.springframework.stereotype.Component;

@Component
public class ShiftValidator {

    /**
     * Check if a shift is valid or not.
     * @param shift the shift to be validated.
     * @return {@code true} if the shift is early or late, {@code false} otherwise.
     */
    public boolean isShiftValid(String shift) {
        String formattedShift = shift.strip().replace("\n", "");
        return formattedShift.equalsIgnoreCase("early") || formattedShift.equalsIgnoreCase("late");
    }
}

