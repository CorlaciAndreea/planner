package com.github.corlaciandreea.planner.repository;

import com.github.corlaciandreea.planner.model.WishBookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WishBookRepository extends JpaRepository<WishBookEntry, Long> {

    @Query("select entry from WishBookEntry entry where entry.employeeId = :employeeId and entry.date = :date")
    WishBookEntry findEntryByEmployeeIdAndDate(@Param("employeeId") String employeeId, @Param("date") LocalDate date);
}
