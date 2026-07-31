# Tests

This project uses Maven, which requires test source files to live under
`src/test/java` in order for `mvn test` to discover and run them.

The actual test suite is here:

- `src/test/java/com/expensetracker/service/ExpenseServiceTest.java` — unit tests
  for the business logic (add, filter, totals, delete) against a real in-memory
  repository, no Spring context needed.
- `src/test/java/com/expensetracker/controller/ExpenseControllerTest.java` —
  full-stack tests that hit the actual HTTP layer via MockMvc, exercising
  validation, error handling, and the real Spring context end to end.

Run the whole suite with:

```
mvn test
```

This `tests/` folder is kept as the structural entry point requested in the
assignment, since moving the test sources out of `src/test/java` would break
Maven's build.
