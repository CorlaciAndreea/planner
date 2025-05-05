package com.github.corlaciandreea.planner.controller;

import com.github.corlaciandreea.planner.error.ValidationException;
import com.github.corlaciandreea.planner.model.WishBookEntry;
import com.github.corlaciandreea.planner.service.WishBookService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/wish")
public class WishBookController {

    @Autowired
    private WishBookService wishBookService;

    @Tag(name = "addWishBookEntry", description = "Add the desired shift per date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The employee wish was saved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WishBookEntry.class))}),
            @ApiResponse(responseCode = "400", description = "The shift is not valid.",
                    content = @Content)})
    @PutMapping("/{employeeId}/{date}")
    public WishBookEntry saveWishBookEntry(@PathVariable("employeeId") String employeeId,
                                           @PathVariable("date") LocalDate date,
                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                   description = "Desired shift", required = true,
                                                   content = @Content(mediaType = "application/json",
                                                           schema = @Schema(implementation = WishBookEntry.class),
                                                           examples = {@ExampleObject(value = "early"), @ExampleObject(value = "late")})) @RequestBody String shift) {
        try {
            WishBookEntry newEntry = new WishBookEntry(employeeId, date, shift);
            return this.wishBookService.saveWishBookEntry(newEntry);
        } catch (ValidationException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }

}
