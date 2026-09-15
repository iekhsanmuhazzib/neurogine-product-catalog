# Product Catalog — Neurogine Technical Assessment

A small Android product catalog app built with Kotlin and Jetpack Compose, consuming the [DummyJSON](https://dummyjson.com/) products API. Built for Neurogine Sdn Bhd's Junior Mobile Developer technical assessment.

## Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **Networking:** Retrofit + OkHttp (with a logging interceptor for debugging) + Gson for JSON parsing
- **Image loading:** Coil
- **Navigation:** Navigation Compose
- **Architecture:** MVVM-style, split into `data/` and `ui/` packages

## How to run

1. Clone this repository
2. Open the project in Android Studio (tested on a recent version, API 37 SDK)
3. Let Gradle sync and download dependencies
4. Run on an emulator or physical device with internet access (the app requires the `INTERNET` permission, already declared in the manifest)

No API key or additional setup is required — DummyJSON is a free, open API.

## Features implemented

- [x] Product list screen — title, thumbnail, and price per product
- [x] Pagination — loads more items automatically as the user scrolls (via the `skip` parameter)
- [x] Product detail screen — tap a product to see its full description, price, rating, and images
- [x] Loading / error (with retry) / empty / success states, visually distinguished
- [x] Debounced search (500ms) via the search endpoint
- [x] Layered architecture — `data/` (models, Retrofit, API access) separated from `ui/` (screens, ViewModels)

## Architecture decisions

**Kotlin + Jetpack Compose.** My only prior mobile experience was a Java-based Android app (university coursework), so Kotlin was the smallest jump — same JVM, SDK, and Android project structure I already knew, letting me spend limited setup time learning Compose and architecture instead of a completely new language and platform.

**Layered structure.** The `data/` package holds everything that doesn't care about UI: `Product` and `ProductInfo` (data models), `DummyJsonApi` (the Retrofit interface describing the 3 endpoints), `RetrofitInstance` (a singleton that builds the actual networking client), and `ProductListUiState` (a sealed class representing loading/error/empty/success). The `ui/` package holds Composable screens and their ViewModels, split further by feature (`productlist/`, `productdetail/`).

**UI state as a sealed class, not booleans.** Rather than tracking `isLoading`, `isError`, `isEmpty` as separate flags (which can contradict each other), each screen's state is one of a fixed set of mutually exclusive options (`Loading`, `Error(message)`, `Empty`, `Success(data)`). This makes invalid combinations impossible by construction, and Kotlin's `when` forces every case to be handled.

**Server-side search, debounced.** The app only ever holds a partial slice of the full product catalog in memory at any time (due to pagination), so filtering client-side would miss products that haven't been scrolled to yet. Search calls DummyJSON's `/products/search` endpoint instead, with a 500ms debounce (via `LaunchedEffect` + `delay`) so a network request only fires after the user pauses typing, not on every keystroke.

**Field scoping.** The raw DummyJSON product object has 20+ fields. Only the fields the assignment's required features actually need were modeled (`id`, `title`, `thumbnail`, `price`, `description`, `rating`, `images`) — fields like `brand`, `category`, `stock`, and `tags` were deliberately left out rather than built into unused filtering features, to keep the scope matched to what was asked.

## Known issue found & fixed

While implementing search, I hit a race condition: search results would briefly appear correctly, then get overwritten by the normal paginated list. The cause was that the scroll-position-based pagination trigger didn't know a search was active, and would fire `loadMoreProducts()` whenever the (now much shorter) search results list appeared "near the bottom." Fixed with an `isSearchActive` flag that pagination checks before triggering. Full details are in `PROGRESS.md`.

## What I'd add with more time (TODOs)

- Pull-to-refresh on the list screen
- A unit test around the pagination/search ViewModel logic
- Image loading placeholder/error states (Coil supports this, not yet wired up)
- The `Empty` state was verified by code review rather than a live test, since DummyJSON's endpoints didn't naturally return zero results under normal testing conditions
- Tested primarily on an emulator; physical device (Poco F7 Pro) testing was limited by a data-cable issue during development

## AI usage disclosure

I used Claude throughout development for guidance and research — explaining Kotlin/Compose/Retrofit concepts I hadn't used before, helping debug specific errors (a missing `INTERNET` permission crash, a Gradle SDK version mismatch, the search/pagination race condition), and reviewing my scoping decisions. All architecture, code, and final decisions in this project are my own; a full timestamped log of decisions and where AI was used is in `PROGRESS.md`.
