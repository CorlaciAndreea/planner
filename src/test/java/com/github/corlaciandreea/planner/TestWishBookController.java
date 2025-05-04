package com.github.corlaciandreea.planner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class TestWishBookController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddingAWish() throws Exception {
        this.mockMvc.perform(put("/wish/employee1/2025-05-02")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("early"))
                .andExpect(status().isOk());
    }
}
