package com.expensetracker.controller;

import com.expensetracker.dto.ExpenseRequest;
import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Expense> addExpense(@Valid @RequestBody ExpenseRequest request) {
        Expense created = service.addExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /api/expenses            -> all expenses
     * GET /api/expenses?category=X -> filtered by category
     */
    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(@RequestParam(required = false) String category) {
        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(service.getExpensesByCategory(category));
        }
        return ResponseEntity.ok(service.getAllExpenses());
    }

    /**
     * GET /api/expenses/total            -> overall total + breakdown by category
     * GET /api/expenses/total?category=X -> total for just that category
     */
    @GetMapping("/total")
    public ResponseEntity<Map<String, Object>> getTotal(@RequestParam(required = false) String category) {
        if (category != null && !category.isBlank()) {
            BigDecimal total = service.getTotalByCategory(category);
            return ResponseEntity.ok(Map.of("category", category, "total", total));
        }
        BigDecimal total = service.getTotal();
        return ResponseEntity.ok(Map.of(
                "total", total,
                "byCategory", service.getTotalsGroupedByCategory()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        service.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
