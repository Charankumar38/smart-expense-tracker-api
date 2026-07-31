# Tests

The actual, runnable test suite lives at `src/test/java/com/expensetracker/`
because Maven requires tests to be there in order for `mvn test` to
discover and run them:

- `src/test/java/com/expensetracker/service/ExpenseServiceTest.java` — unit
  tests for the business logic (add, filter, totals, delete).
- `src/test/java/com/expensetracker/controller/ExpenseControllerTest.java` —
  full HTTP-layer tests via MockMvc (validation, error handling, endpoints).

Run the whole suite with:

```
mvn test
```

A copy of both test files is also included here under `tests/java/` for
easy browsing, matching the structure requested in the assignment brief.
These are identical to the files under `src/test/java` — only the copies
in `src/test/java` are actually executed by Maven.