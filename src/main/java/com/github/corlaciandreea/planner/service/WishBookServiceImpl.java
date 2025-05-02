package com.github.corlaciandreea.planner.service;

import com.github.corlaciandreea.planner.model.WishBookEntry;
import com.github.corlaciandreea.planner.repository.WishBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Marks this class as a Spring service component.
public class WishBookServiceImpl implements WishBookService {

    @Autowired
    private WishBookRepository wishBookRepository;

    @Override
    public WishBookEntry saveWishBookEntry(WishBookEntry entry){
        WishBookEntry entryToBeSaved = entry;

        // If the employee and the date are the same -> update an existing wish entry
        WishBookEntry existingEntry = this.wishBookRepository.findEntryByEmployeeIdAndDate(entry.getEmployeeId(), entry.getDate());
        if(existingEntry.getEntryId() != null) {
            entryToBeSaved.setEntryId(existingEntry.getEntryId());
        }
        return wishBookRepository.save(entryToBeSaved);
    }
}
