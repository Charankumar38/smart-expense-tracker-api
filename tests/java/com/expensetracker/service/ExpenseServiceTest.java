package com.expensetracker.service;

import com.expensetracker.dto.ExpenseRequest;
import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Plain unit tests against a fresh ExpenseService + real ExpenseRepository
 * for each test (no Spring context needed here — keeps these fast).
 */
class ExpenseServiceTest {

    private ExpenseService service;

    @BeforeEach
    void setUp() {
        service = new ExpenseService(new ExpenseRepository());
    }

    private ExpenseRequest request(String title, String amount, String category, LocalDate date) {
        ExpenseRequest r = new ExpenseRequest();
        r.setTitle(title);
        r.setAmount(new BigDecimal(amount));
        r.setCategory(category);
        r.setDate(date);
        return r;
    }

    @Test
    void addExpense_assignsIdAndPersists() {
        Expense e = service.addExpense(request("Lunch", "250.00", "Food", LocalDate.now()));

        assertNotNull(e.getId());
        assertEquals(1, service.getAllExpenses().size());
    }

    @Test
    void addExpense_assignsIncrementingIds() {
        Expense first = service.addExpense(request("Lunch", "250.00", "Food", LocalDate.now()));
        Expense second = service.addExpense(request("Bus", "50.00", "Travel", LocalDate.now()));

        assertNotNull(first.getId());
        assertNotNull(second.getId());
        assertTrue(second.getId() > first.getId());
    }

    @Test
    void getExpensesByCategory_filtersCaseInsensitively() {
        service.addExpense(request("Lunch", "250.00", "Food", LocalDate.now()));
        service.addExpense(request("Bus ticket", "50.00", "Travel", LocalDate.now()));

        List<Expense> food = service.getExpensesByCategory("food");

        assertEquals(1, food.size());
        assertEquals("Lunch", food.get(0).getTitle());
    }

    @Test
    void getExpensesByCategory_returnsEmptyListWhenNoMatch() {
        service.addExpense(request("Lunch", "250.00", "Food", LocalDate.now()));

        assertTrue(service.getExpensesByCategory("Entertainment").isEmpty());
    }

    @Test
    void getTotal_sumsAllExpenses() {
        service.addExpense(request("Lunch", "250.00", "Food", LocalDate.now()));
        service.addExpense(request("Bus ticket", "50.00", "Travel", LocalDate.now()));

        assertEquals(0, new BigDecimal("300.00").compareTo(service.getTotal()));
    }

    @Test
    void getTotal_isZeroWhenNoExpenses() {
        assertEquals(0, BigDecimal.ZERO.compareTo(service.getTotal()));
    }

    @Test
    void getTotalByCategory_sumsOnlyMatchingCategory() {
        service.addExpense(request("Lunch", "250.00", "Food", LocalDate.now()));
        service.addExpense(request("Dinner", "300.00", "Food", LocalDate.now()));
        service.addExpense(request("Bus ticket", "50.00", "Travel", LocalDate.now()));

        assertEquals(0, new BigDecimal("550.00").compareTo(service.getTotalByCategory("Food")));
    }

    @Test
    void getTotalsGroupedByCategory_groupsCorrectly() {
        service.addExpense(request("Lunch", "250.00", "Food", LocalDate.now()));
        service.addExpense(request("Dinner", "300.00", "Food", LocalDate.now()));
        service.addExpense(request("Bus ticket", "50.00", "Travel", LocalDate.now()));

        var totals = service.getTotalsGroupedByCategory();

        assertEquals(0, new BigDecimal("550.00").compareTo(totals.get("Food")));
        assertEquals(0, new BigDecimal("50.00").compareTo(totals.get("Travel")));
    }

    @Test
    void deleteExpense_removesExisting() {
        Expense e = service.addExpense(request("Lunch", "250.00", "Food", LocalDate.now()));

        service.deleteExpense(e.getId());

        assertTrue(service.getAllExpenses().isEmpty());
    }

    @Test
    void deleteExpense_throwsWhenIdMissing() {
        assertThrows(ExpenseNotFoundException.class, () -> service.deleteExpense(999L));
    }
}
