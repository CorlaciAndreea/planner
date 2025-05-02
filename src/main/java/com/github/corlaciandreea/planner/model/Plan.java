package com.github.corlaciandreea.planner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data // Generates getters, setters, toString, equals and hashCode methods.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "planning")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Auto-generates the primary key.
    private Long planId;

    private LocalDate date;
    private String shift;

    @ElementCollection
    @Column(name = "employees")
    private List<String> employees;
}
