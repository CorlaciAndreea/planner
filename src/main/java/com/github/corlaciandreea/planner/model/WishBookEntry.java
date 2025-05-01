package com.github.corlaciandreea.planner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data // Generates getters, setters, toString, equals and hshCode methods.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="wishbook")
public class WishBookEntry {
    @Id //Primary key of the entity.
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto-generates the primary key.
    private Long entryId;

    private String employeeId;
    private LocalDate date;
    private String shift;

    public WishBookEntry(String employeeId, LocalDate date, String shift) {
        this.employeeId = employeeId;
        this.date = date;
        this.shift = shift;
    }
}
