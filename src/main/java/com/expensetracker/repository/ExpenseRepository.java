package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In-memory store for expenses, per the assignment spec (no DB required).
 * ConcurrentHashMap + AtomicLong so it's safe under concurrent requests,
 * which a real HTTP server will always have to handle.
 */
@Repository
public class ExpenseRepository {

    private final Map<Long, Expense> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public Expense save(Expense expense) {
        if (expense.getId() == null) {
            expense.setId(idCounter.incrementAndGet());
        }
        store.put(expense.getId(), expense);
        return expense;
    }

    public List<Expense> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Expense> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public boolean deleteById(Long id) {
        return store.remove(id) != null;
    }
}
