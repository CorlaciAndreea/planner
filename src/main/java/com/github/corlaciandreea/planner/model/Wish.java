package com.github.corlaciandreea.planner.model;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@Entity
@Getter
@Setter
public class Wish {

    private String employeeId;
    private Date date;
    private String shift;
}
