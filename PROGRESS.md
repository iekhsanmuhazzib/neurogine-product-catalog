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

### [14 Sept 2026, ~9:00 AM] Retrofit interface: list + detail endpoints

Set up Retrofit and OkHttp dependencies via the version catalog to keep
things consistent with the rest of the project. Created ProductInfo.kt
to model the paginated API response (products list + total/skip/limit),
since this shape is different from a single Product. Then wrote
DummyJsonApi.kt with two endpoints: getProducts() for the list (using
@Query for limit/skip) and getProductDetail() for a single item (using
@Path for the id). Spent some time understanding what Retrofit
annotations actually do — initially confused @Path and @Query, and
mistakenly thought total/skip/limit needed separate API calls, before
realizing they're just fields bundled inside the same ProductInfo
response. Traced through an actual example request step-by-step to get
it to click.

---

### [15 Sept 2026, ~10:00 AM] Debounced search implementation + pagination race condition

Implemented server-side search (via the DummyJSON search endpoint) with
debouncing using LaunchedEffect + delay(500ms), so a network call only
fires after the user pauses typing rather than on every keystroke.

Chose server-side over client-side search because the app only loads a
partial slice of the 194 total products at any time (due to pagination) -
client-side filtering would miss products not yet scrolled to.

Hit a bug where search results would flash correctly, then immediately
revert back to the full unfiltered list. Traced this to a race condition:
when search results replaced the list with a small number of items, the
scroll-detection logic (used for pagination) would incorrectly treat this
as "near the bottom of the list" and auto-trigger loadMoreProducts(),
which had no awareness that a search was active and would overwrite the
search results with normal paginated data. Fixed by adding an
isSearchActive flag that pagination's loadMoreProducts() checks and
respects.

---

### [15 Sept 2026, ~11:00 AM] Search matches fields beyond the visible title

Noticed that searching for short substrings (e.g. "re") sometimes returns
products whose titles don't obviously contain the term (e.g. "Eyeshadow
Palette with Mirror"). This is because DummyJSON's search endpoint
appears to match against the full product object (likely description,
category, etc.), not just the title field shown in the UI. This is
server-side behavior outside the app's control, not a client bug.

---

### [15 Sept 2026, ~11:30 AM] Product detail screen with navigation

Added Navigation Compose to move between the list and detail screens,
using a route like "productDetail/{id}" to pass the tapped product's ID.
Built ProductDetailViewModel (using a ViewModelProvider.Factory since it
needs the product ID passed into its constructor, unlike the list
ViewModel) and ProductDetailScreen showing a horizontally scrollable
image row, title, price, rating, and full description.

Hit a build error calling a function I'd misnamed earlier in the
Retrofit interface (getProductsInfo instead of the getProductDetail I
referenced from the ViewModel) - simple naming mismatch, fixed by using
the actual function name. All 5 required features plus layered
architecture are now complete and tested on the emulator.

---

**AI usage note (for this session's entries):** Used Claude for guidance across
today's decisions — comparing stack tradeoffs, talking through the SDK version
fix, scoping the Product model fields, and working through navigation/detail
screen concepts. All final decisions, code, and reasoning above are my own.

\---

### \[x] Phase 1: Data layer (models, Retrofit service, repository)

\---

### \[x] Phase 2: Product list + pagination

\---

### \[x] Phase 3: Loading/error/empty/success states

\---

### \[x] Phase 4: Detail screen

\---

### \[x] Phase 5: Debounced search

\---

### \[x] Phase 6: Layer/architecture cleanup

\---

### \[ ] Bonus features attempted

\---

### \[ ] README + walkthrough video prep

\---

## Open TODOs / known gaps

*(Running list — carry honest TODOs here as they come up, move relevant ones into README.md at the end.)*

* 

