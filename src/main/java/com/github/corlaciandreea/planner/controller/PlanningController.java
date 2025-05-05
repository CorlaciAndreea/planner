package com.github.corlaciandreea.planner.controller;

import com.github.corlaciandreea.planner.error.ValidationException;
import com.github.corlaciandreea.planner.model.DaySchedule;
import com.github.corlaciandreea.planner.model.Plan;
import com.github.corlaciandreea.planner.service.PlanningService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class PlanningController {

    @Autowired
    private PlanningService planningService;

    @Tag(name = "addPlanning", description = "Add wish book entries to schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The planning was saved for a given date and shift",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Plan.class))}),
            @ApiResponse(responseCode = "400", description = "The shift is not valid.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "There must be two employees per shift.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Only two employees per shift are allowed.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Wish book entry does not exists for some employees.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "The employee already has one shift planned for the given day.",
                    content = @Content)
    })
    @PutMapping("/planning/{date}/{shift}")
    public Plan addPlanning(@PathVariable("date") LocalDate date,
                            @PathVariable("shift") String shift,
                            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "The employee list for the planning", required = true,
                                    content = @Content(mediaType = "application/json",
                                            examples = @ExampleObject(value = "[\"employee1\", \"employee2\"]"))) @RequestBody List<String> emplyeesList) {
        try {
            Plan newPlanning = new Plan(date, shift, emplyeesList);
            return this.planningService.savePlanPerShift(newPlanning);
        } catch (ValidationException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }

    @Tag(name = "seeSchedule", description = "Get the schedule for a given date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The schedule for the given day is returned",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DaySchedule.class),
                            examples = @ExampleObject(value = "{\"date\":\"2025-05-02\",\"shifts\":{\"early\":[\"employee6\",\"employee1\"],\"late\":[\"employee3\",\"employee5\",\"employee4\"]}}"))}),
            @ApiResponse(responseCode = "204", description = "There is no schedule for that specific date.",
                    content = @Content)
    })
    @GetMapping("/schedule/{date}")
    public DaySchedule getScheduleForDay(@PathVariable("date") LocalDate date) {
        return this.planningService.getSchedulePerDay(date);
    }
}
