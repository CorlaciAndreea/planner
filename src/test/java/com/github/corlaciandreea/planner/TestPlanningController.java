package com.github.corlaciandreea.planner;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class TestPlanningController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddingWrongPlanning() throws Exception {
        this.mockMvc.perform(put("/planning/2025-05-02/early")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"employee12\", \"employee13\"]"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().contains("Wish book entry does not exists for ")));
        ;
    }

    @Test
    public void testAddingValidPlanning() throws Exception {
        // Add employees wises to validate the plan
        this.mockMvc.perform(put("/wish/employee100/2025-05-02")
                .contentType(MediaType.TEXT_PLAIN)
                .content("early"));

        this.mockMvc.perform(put("/wish/employee101/2025-05-02")
                .contentType(MediaType.TEXT_PLAIN)
                .content("early"));

        this.mockMvc.perform(put("/planning/2025-05-02/early")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"employee100\", \"employee101\"]"))
                .andExpect(status().isOk());
    }

}
