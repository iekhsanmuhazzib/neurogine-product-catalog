# Progress \& Decision Log — Neurogine Product Catalog Assessment

Candidate: Muhammad Iekhsan
Position: Junior Mobile Developer, Neurogine Sdn Bhd
Assignment received: 10 September 2026
Target completion: 16 September 2026 (personal buffer before 17 Sept official deadline)

This log tracks major decisions and progress as the app is built, with timestamps.
It exists so my reasoning is documented as I go, not reconstructed afterward for the README.

\---

## How to use this file

Each entry: date/time, what was decided or completed, and **why** — in my own words.
Where AI (Claude) was used for guidance/research, that's noted per the assignment's AI usage rules.

\---

## Entries

### [11 Sept 2026, ~9:30 AM] Stack decision: Kotlin + Jetpack Compose

I chose Kotlin because it had the lowest barrier to entry for me. I'd already
built a full Android app in Java (banking app project), so the SDK, project
structure, and general Android workflow were already familiar. I did look at
Flutter, Swift, React Native, and Kotlin Multiplatform first, but each would
mean learning a new language and SDK at the same time, not practical on a
7-day deadline. Kotlin let me spend most of my limited setup time on Jetpack
Compose and architecture instead of the basics.

---

### [11 Sept 2026, ~10:00 AM] Environment setup & first run

Started by verifying that Android Studio was up to date and creating a
quick test project to make sure Jetpack Compose was working properly. I
set up a physical Poco F7 Pro for testing, which required enabling the
"Install via USB" setting in HyperOS. After fixing a build error by
updating compileSdk to 37 in build.gradle.kts, the test app ran
successfully on the device.

---

### [11 Sept 2026, ~10:30 AM] API response shapes verified

I tested the three DummyJSON endpoints (list, detail, and search) directly
in the browser to check their actual JSON structures. I confirmed that the
list and search endpoints return a wrapper object containing a products
list along with pagination fields (total, skip, and limit). On the other
hand, the detail endpoint returns a single product object directly without
a wrapper. Because of this, I will build two separate data models: one for
the paginated list responses and another for single product details.

---

### [11 Sept 2026, ~11:36 AM] Scoping the Product data model

I went through the full DummyJSON product object (22+ fields) and
cross-checked against the assignment's actual required features, then
removed unused fields to match the assignment goals. Instead of building
extra filtering options right away, I kept the Product model focused
strictly on what the list view and detail view need. This kept the app
clean, and I noted extra filtering features as a possible future
improvement.

---

**AI usage note (for this session's entries):** Used Claude for guidance across
today's decisions — comparing stack tradeoffs, talking through the SDK version
fix, and scoping the Product model fields. All final decisions, code, and
reasoning above are my own.

\---

### \[ ] Phase 1: Data layer (models, Retrofit service, repository)

\---

### \[ ] Phase 2: Product list + pagination

\---

### \[ ] Phase 3: Loading/error/empty/success states

\---

### \[ ] Phase 4: Detail screen

\---

### \[ ] Phase 5: Debounced search

\---

### \[ ] Phase 6: Layer/architecture cleanup

\---

### \[ ] Bonus features attempted

\---

### \[ ] README + walkthrough video prep

\---

## Open TODOs / known gaps

*(Running list — carry honest TODOs here as they come up, move relevant ones into README.md at the end.)*

* 

