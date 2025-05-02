package com.github.corlaciandreea.planner.service;

import com.github.corlaciandreea.planner.error.ValidationException;
import com.github.corlaciandreea.planner.model.WishBookEntry;
import com.github.corlaciandreea.planner.repository.WishBookRepository;
import com.github.corlaciandreea.planner.validator.ShiftValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Marks this class as a Spring service component.
public class WishBookServiceImpl implements WishBookService {

    @Autowired
    private WishBookRepository wishBookRepository;
    @Autowired
    private ShiftValidator shiftValidator;

    @Override
    public WishBookEntry saveWishBookEntry(WishBookEntry entry) throws ValidationException {
        //Do not save the entry if the shift is not valid
        if (!shiftValidator.isShiftValid(entry.getShift())) {
            throw new ValidationException("The shift is not valid.");
        }

        // If the employee and the date are the same -> update an existing wish entry
        WishBookEntry existingEntry = this.wishBookRepository.findEntryByEmployeeIdAndDate(entry.getEmployeeId(), entry.getDate());
        if (existingEntry.getEntryId() != null) {
            entry.setEntryId(existingEntry.getEntryId());
        }
        return wishBookRepository.save(entry);
    }
}
