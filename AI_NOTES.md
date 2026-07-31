# AI Usage Notes

> **To the applicant reading this later:** this file is a starting draft, not
> a finished answer. Fill in the bracketed sections below with what you
> actually did once you've run the build, read through the code, and made
> any changes. The reviewers are checking that this reflects real engagement
> with the AI's output, not a generic statement — be specific.

## 1. What was AI-generated vs. written by me

- The entire initial codebase (model, DTO, repository, service, controller,
  exception handling, tests, README) was generated with Claude in a single
  session, based on the assignment brief.
- [ Fill in: which parts, if any, you rewrote, restructured, or added to
  yourself after reviewing the generated code — e.g. "I changed the total
  endpoint's response shape", "I added a check for X", "I renamed Y for
  clarity". If you kept a section as-is after reviewing it, say so honestly
  too — that's a legitimate outcome as long as you understood it. ]

## 2. What I validated, tested, or changed, and why

- [ Fill in: did `mvn clean install` and `mvn test` pass on a clean checkout
  for you? Did you hit any errors and fix them? ]
- [ Fill in: did you manually test the endpoints with curl/Postman and
  confirm the behavior matches the spec — e.g. totals calculate correctly,
  filtering is case-insensitive, deleting a missing id returns 404? ]
- [ Fill in: any bug you found in the AI-generated code and how you fixed it.
  If you found none, say what you specifically checked to be confident of
  that. ]

## 3. AI suggestions I decided not to use, and why

- [ Fill in: e.g. "I considered adding JSON-file persistence but decided
  in-memory was sufficient per the spec and simpler to reason about", or
  "I skipped the Swagger/OpenAPI bonus to focus on making the core
  requirements solid given the time limit", or any other alternative you
  discussed with the AI and rejected, with your reasoning. ]

---

*Notes on approach: I used Claude to scaffold the Spring Boot project
structure, REST endpoints, validation, and test suite quickly, then reviewed
the generated code, ran the build and tests myself, and [describe what you
did from there].*
