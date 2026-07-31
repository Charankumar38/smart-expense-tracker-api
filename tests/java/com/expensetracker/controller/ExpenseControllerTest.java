package com.expensetracker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Full-stack tests through the real HTTP layer (MockMvc) against the
 * actual Spring context, so validation + exception handling are exercised
 * exactly as a real client would hit them.
 *
 * Note: the in-memory repository is a singleton Spring bean, so state
 * persists across test methods within this class (Spring reuses the
 * context). Assertions below are written to not depend on the store
 * being empty at the start of each test.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void seedOneExpense() throws Exception {
        String body = """
                {"title":"Seed expense","amount":10.00,"category":"Misc","date":"2026-07-20"}
                """;
        mockMvc.perform(post("/api/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
    }

    @Test
    void addExpense_returnsCreatedWithGeneratedId() throws Exception {
        String body = """
                {"title":"Groceries","amount":120.50,"category":"Food","date":"2026-07-20"}
                """;

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Groceries"))
                .andExpect(jsonPath("$.amount").value(120.50))
                .andExpect(jsonPath("$.category").value("Food"));
    }

    @Test
    void addExpense_rejectsBlankTitleAndNegativeAmount() throws Exception {
        String body = """
                {"title":"","amount":-5,"category":"","date":"2026-07-20"}
                """;

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title").exists())
                .andExpect(jsonPath("$.errors.amount").exists())
                .andExpect(jsonPath("$.errors.category").exists());
    }

    @Test
    void addExpense_rejectsFutureDate() throws Exception {
        String body = """
                {"title":"Future thing","amount":10,"category":"Misc","date":"2099-01-01"}
                """;

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.date").exists());
    }

    @Test
    void getExpenses_filtersByCategory() throws Exception {
        String travel = """
                {"title":"Cab","amount":80,"category":"Travel","date":"2026-07-20"}
                """;
        mockMvc.perform(post("/api/expenses").contentType(MediaType.APPLICATION_JSON).content(travel));

        mockMvc.perform(get("/api/expenses").param("category", "Travel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Travel"));
    }

    @Test
    void getTotal_returnsOverallTotalAndCategoryBreakdown() throws Exception {
        mockMvc.perform(get("/api/expenses/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").exists())
                .andExpect(jsonPath("$.byCategory").exists());
    }

    @Test
    void getTotal_filteredByCategory_returnsOnlyThatCategoryTotal() throws Exception {
        mockMvc.perform(get("/api/expenses/total").param("category", "Misc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Misc"))
                .andExpect(jsonPath("$.total").exists());
    }

    @Test
    void deleteExpense_returns404ForUnknownId() throws Exception {
        mockMvc.perform(delete("/api/expenses/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }
}
