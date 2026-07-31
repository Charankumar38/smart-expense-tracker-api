# AI Usage Notes

**Q1. Which parts of the code were AI-generated vs. written by you?**

I'm honestly still a beginner with Java and Spring Boot, so I used Claude
to write the actual code for this assignment — the model, the DTO with
validation, the in-memory repository, the service layer, the controller
with all the endpoints, the error handling, and the test suite. I didn't
hand-write or rewrite the Java myself this time.

What I did do myself was everything around it. I didn't even have Java,
Maven, or Git installed on my laptop when I started, so a good chunk of my
actual effort went into setting up my whole dev environment from scratch —
which took a while and had its own mistakes along the way (extracted Maven
to the wrong folder at one point, mixed up terminal commands, etc.) before
I got it working properly.

**Q2. What did you validate, tested, or changed in the AI's output, and why?**

Once I had everything running, I made sure I wasn't just blindly
submitting whatever the AI gave me:

- Ran `mvn clean install` on a clean checkout — built successfully.
- Ran `mvn test` — all 17 tests passed (0 failures, 0 errors). These cover
  adding expenses, filtering by category, totals, deleting, and validation
  edge cases like blank titles, negative amounts, and future dates.
- Started the server and manually hit every endpoint myself with curl
  instead of just trusting it worked:
  - Added a few expenses and checked the response had the right id and
    fields.
  - Listed all expenses and confirmed they showed up.
  - Filtered by category and confirmed only the matching ones came back.
  - Checked the total endpoint and the numbers matched what I'd actually
    added.
  - Deleted one and re-checked the list to confirm it was really gone.

Nothing broke or looked wrong during any of that testing, so I didn't need
to fix any bugs — but I only know it works because I tested it end to end
myself, not because I assumed the AI got it right.

**Q3. Any AI suggestion you decided not to use, and why?**

The assignment offered an optional bonus (search, monthly summary,
Swagger docs, or Docker), and Claude could have added one of these. I
decided to skip it. With limited time and being new to all of this, I
thought it was better to make sure the required functionality
(add/view/filter/total/delete) was solid and fully tested than to spread
myself thin trying to add an extra feature on top.