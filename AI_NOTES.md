# AI Usage Notes

I'm honestly still a beginner with Java and Spring Boot, so for this
assignment I leaned on Claude pretty heavily to write the actual code —
the model, the DTO with validation, the in-memory repository, the service
layer, the controller with all the endpoints, error handling, and the
tests. I didn't hand-write or rewrite the Java myself this time around.

What I did do was everything around it. I didn't even have Java, Maven, or
Git installed on my laptop when I started, so a good chunk of my actual
effort went into getting my whole environment set up from scratch — which
took a while and had its own share of mistakes (extracted Maven to the
wrong folder at one point, pasted a few terminal commands together by
accident, etc.) before I got it right.

Once I had things running, I made sure I wasn't just blindly submitting
whatever the AI gave me:

- I ran `mvn clean install` on a clean checkout and it built successfully.
- I ran `mvn test` and all 17 tests passed (0 failures, 0 errors) — these
  cover adding expenses, filtering by category, totals, deleting, and
  validation edge cases like blank titles, negative amounts, and future
  dates.
- I started the server and actually hit every endpoint myself with curl
  instead of just trusting it worked:
  - Added a few expenses and checked the response had the right id and
    fields.
  - Listed all expenses and checked they showed up.
  - Filtered by category and confirmed only the matching ones came back.
  - Checked the total endpoint and the numbers matched what I'd actually
    added.
  - Deleted one and then re-checked the list to confirm it was really
    gone.

Nothing broke or looked wrong during any of that, so I didn't need to fix
any bugs — but I only know that because I actually tested it end to end
myself, not because I just assumed the code was fine.

The one thing I skipped on purpose was the optional bonus feature (search,
monthly summary, Swagger docs, Docker). With limited time and being new to
all of this, I decided it was better to make sure the required stuff
(add/view/filter/total/delete) was solid and fully tested than to spread
myself thin trying to add extra features on top.