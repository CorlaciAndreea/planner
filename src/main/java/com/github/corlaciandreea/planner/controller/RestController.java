package com.github.corlaciandreea.planner.controller;

import com.github.corlaciandreea.planner.model.WishBookEntry;
import com.github.corlaciandreea.planner.service.WishBookService;
import com.github.corlaciandreea.planner.service.WishBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Date;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private WishBookService wishBookService;


    @GetMapping("/")
    public String getResponse(){
        return "it works";
    }

    @PutMapping("/wish/{employeeId}/{date}")
    public WishBookEntry saveWishBookEntry(@PathVariable("employeeId") String employeeId,
                                           @PathVariable("date") LocalDate date,
                                           @RequestBody String shift){
        WishBookEntry newEntry = new WishBookEntry(employeeId, date, shift);
        return this.wishBookService.saveWishBookEntry(newEntry);
    }
}
