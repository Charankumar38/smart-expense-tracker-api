# Smart Expense Tracker API

A REST API for tracking personal expenses — add, list, filter by category,
compute totals (overall and per category), and delete. Built with Java and
Spring Boot, data stored in memory (no database required, per the assignment).

## Tech Stack

- Java 21
- Spring Boot 3.3.0 (Web + Validation)
- Maven
- JUnit 5 + MockMvc (tests)

## Prerequisites

- JDK 21 or later — check with `java -version`
- Maven 3.8+ — check with `mvn -version`

If you don't have these installed:
- Java: install via [Adoptium Temurin](https://adoptium.net/) or your OS package manager (`sudo apt install openjdk-21-jdk` on Ubuntu/Debian).
- Maven: `sudo apt install maven` (Ubuntu/Debian) or [maven.apache.org](https://maven.apache.org/install.html).

## Setup, Run, Test

Clone the repo and `cd` into it, then:

```bash
# 1. Install dependencies and build
mvn clean install

# 2. Start the server (runs on http://localhost:8080)
mvn spring-boot:run

# 3. Run the test suite (in a separate terminal, or before starting the server)
mvn test
```

## API Endpoints

| Method | Path                                  | Description                                      |
|--------|---------------------------------------|---------------------------------------------------|
| POST   | `/api/expenses`                       | Add a new expense                                  |
| GET    | `/api/expenses`                       | List all expenses                                  |
| GET    | `/api/expenses?category={category}`   | List expenses filtered by category                |
| GET    | `/api/expenses/total`                 | Overall total + totals broken down by category     |
| GET    | `/api/expenses/total?category={cat}`  | Total for a single category                        |
| DELETE | `/api/expenses/{id}`                  | Delete an expense by id                            |

### Example requests

Add an expense:

```bash
curl -X POST http://localhost:8080/api/expenses \
  -H "Content-Type: application/json" \
  -d '{"title":"Groceries","amount":120.50,"category":"Food","date":"2026-07-20"}'
```

List all expenses:

```bash
curl http://localhost:8080/api/expenses
```

Filter by category:

```bash
curl "http://localhost:8080/api/expenses?category=Food"
```

Overall total (with per-category breakdown):

```bash
curl http://localhost:8080/api/expenses/total
```

Total for one category:

```bash
curl "http://localhost:8080/api/expenses/total?category=Food"
```

Delete an expense:

```bash
curl -X DELETE http://localhost:8080/api/expenses/1
```

### Validation & errors

- `title`, `category`: required, non-blank
- `amount`: required, must be greater than 0
- `date`: required, cannot be in the future
- Invalid input → `400` with a JSON body listing the specific field errors
- Deleting or referencing a non-existent id → `404` with a JSON error body

## Project Structure

```
expense-tracker/
  README.md
  AI_NOTES.md
  pom.xml
  src/
    main/java/com/expensetracker/
      ExpenseTrackerApplication.java
      model/Expense.java
      dto/ExpenseRequest.java
      repository/ExpenseRepository.java   # in-memory store
      service/ExpenseService.java          # business logic
      controller/ExpenseController.java    # REST endpoints
      exception/                            # custom exception + global handler
    main/resources/application.properties
    test/java/com/expensetracker/
      service/ExpenseServiceTest.java       # unit tests
      controller/ExpenseControllerTest.java # MockMvc integration tests
  tests/
    README.md   # explains why tests live under src/test/java (Maven convention)
```

> **Note on structure:** the assignment brief asked for a top-level `tests/`
> folder. Maven requires test sources to live under `src/test/java` for
> `mvn test` to discover and run them, so that's where the real test suite
> is. `tests/README.md` points to it explicitly.

## Data Persistence

Expenses are stored in memory (`ConcurrentHashMap`) and reset when the
server restarts — this is intentional per the assignment spec, which allows
in-memory storage with no database requirement.
