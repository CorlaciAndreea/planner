package com.github.corlaciandreea.planner.repository;

import com.github.corlaciandreea.planner.model.WishBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishBookRepository extends JpaRepository<WishBookEntry, Long> {
}
