package com.github.corlaciandreea.planner.service;

import com.github.corlaciandreea.planner.model.WishBookEntry;

public interface WishBookService {

    /**
     * Saves a wishbook entry.
     * @param entry the entry to be saved
     * @return the saved entry.
     */
    WishBookEntry saveWishBookEntry(WishBookEntry entry);
}
