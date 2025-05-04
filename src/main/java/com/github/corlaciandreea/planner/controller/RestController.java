package com.github.corlaciandreea.planner.controller;

import com.github.corlaciandreea.planner.error.ValidationException;
import com.github.corlaciandreea.planner.model.DaySchedule;
import com.github.corlaciandreea.planner.model.Plan;
import com.github.corlaciandreea.planner.model.WishBookEntry;
import com.github.corlaciandreea.planner.service.PlanningService;
import com.github.corlaciandreea.planner.service.WishBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private WishBookService wishBookService;

    @Autowired
    private PlanningService planningService;


    @GetMapping("/")
    public String getResponse() {
        return "it works";
    }

    @PutMapping("/wish/{employeeId}/{date}")
    public WishBookEntry saveWishBookEntry(@PathVariable("employeeId") String employeeId,
                                           @PathVariable("date") LocalDate date,
                                           @RequestBody String shift) {
        try {
            WishBookEntry newEntry = new WishBookEntry(employeeId, date, shift);
            return this.wishBookService.saveWishBookEntry(newEntry);
        } catch (ValidationException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }

    @PutMapping("/planning/{date}/{shift}")
    public Plan addPlanning(@PathVariable("date") LocalDate date,
                            @PathVariable("shift") String shift,
                            @RequestBody List<String> emplyeesList) {
        try {
            Plan newPlanning = new Plan(date, shift, emplyeesList);
            return this.planningService.savePlanPerShift(newPlanning);
        } catch (ValidationException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }

    @GetMapping("/schedule/{date}")
    public DaySchedule getScheduleForDay(@PathVariable("date") LocalDate date) {
        return this.planningService.getSchedulePerDay(date);
    }
}
