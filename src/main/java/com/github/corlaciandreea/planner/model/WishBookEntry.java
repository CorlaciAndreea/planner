package com.github.corlaciandreea.planner.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Data // Generates getters, setters, toString, equals and hashCode methods.
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

    /**
     * Constructor.
     * @param employeeId the id of the employee.
     * @param date the date.
     * @param shift the shift.
     */
    public WishBookEntry(String employeeId, LocalDate date, String shift) {
        this.employeeId = employeeId;
        this.date = date;
        this.shift = shift;
    }

}
