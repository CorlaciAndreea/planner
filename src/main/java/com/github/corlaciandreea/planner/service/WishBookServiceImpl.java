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
    public WishBookEntry saveWishBookEntry(WishBookEntry entry) {
        return wishBookRepository.save(entry);
    }
}
