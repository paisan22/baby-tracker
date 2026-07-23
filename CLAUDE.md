# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Persona

Act as a Senior Android/Kotlin developer collaborating with the app's original author. Follow the user's direction on scope and priorities, but bring senior-level judgment: flag architectural inconsistencies, point out simpler alternatives, and push back (briefly) on approaches that would introduce tech debt inconsistent with the existing patterns below. Prefer idiomatic, modern Android/Kotlin (Compose, coroutines/Flow, Hilt) over Java-style patterns.

## Project

BabyTracker (`nl.paisan.babytracker`) is a minimalist Android app for parents to track a baby's daily activities (feeding, diapers, rest), growth (weight, length), and basic bio data. Single-module app, Kotlin + Jetpack Compose, initially built ~3 years ago and now under active redevelopment.

## Build & Test Commands

```bash
./gradlew assembleDebug              # build debug APK
./gradlew installDebug                # build and install on connected device/emulator
./gradlew test                        # run local JVM unit tests (app/src/test)
./gradlew test --tests "nl.paisan.babytracker.ExampleUnitTest"   # run a single unit test class
./gradlew connectedAndroidTest         # run instrumented tests on a connected device/emulator (app/src/androidTest)
./gradlew lint                        # run Android lint
```

Note: build files are Groovy DSL (`build.gradle`), not Kotlin DSL (`build.gradle.kts`).

## Key Config

- `compileSdk` / `targetSdk` / `minSdk`: 33
- Java compatibility: 18
- Compose compiler extension: 1.4.6, Compose BOM: 2022.10.00
- DI: Hilt 2.44 (`kapt`, not KSP)
- Persistence: Room 2.5.2
- Background work: WorkManager 2.8.1
- Navigation: Navigation-Compose 2.6.0

## Architecture

The app follows an MVVM + repository pattern with a clear data/domain/ui/di layering under `app/src/main/java/nl/paisan/babytracker/`:

- **`data/`** — persistence layer.
  - `entities/` — Room `@Entity` classes (one per table, e.g. `BreastLog`, `WeightMeasurement`).
  - `dao/` — Room `@Dao` interfaces, one per entity, exposing `Flow` queries and suspend insert/delete functions.
  - `repositories/` — concrete repository implementations (e.g. `LengthMeasurementRepo`) that inject a DAO, wrap writes in `withContext(Dispatchers.IO)`, and catch/log exceptions rather than propagating them.
  - `mappers/` — Room `TypeConverter`s (e.g. `GenderConverter`) and entity↔domain mappers (e.g. `BioMapper`).
  - `migrations/` — Room `Migration` classes, named `MigrationFromXToY`.
  - `BabyTrackerDB.kt` — the single `RoomDatabase`, declares all entities and exposes one DAO accessor per entity.

- **`domain/`** — framework-agnostic business layer.
  - `repositories/` — repository *interfaces* (`I`-prefixed, e.g. `ILengthMeasurementRepo`) that the `data/repositories/` implementations bind to via Hilt `@Binds`. ViewModels depend on these interfaces, never on the concrete `data` implementations directly.
  - `entities/` — domain-level entities distinct from Room entities where needed (e.g. domain `Bio`).
  - `enums/` — shared enums (`ActivityType`, `BottleType`, `BreastSide`, `DiaperType`, `Gender`, `PhysicalType`).
  - `commands/` — command objects encapsulating multi-step domain operations (e.g. `AddBottleLogCommand`).
  - `services/` — stateless domain services (e.g. `DateTimeService`).
  - `workers/` — `WorkManager` `Worker`s (e.g. `SaveBioWorker`).
  - `repositories/Result.kt` — shared result type for domain operations.

- **`di/`** — Hilt modules. `DaoModule` provides DAOs from `BabyTrackerDB`; `RepoModule` binds each domain repository interface to its `data/repositories/` implementation. New repositories must be wired into `RepoModule` the same way.

- **`ui/`** — Compose UI, organized by screen.
  - `screen/<feature>/` — one package per screen, each with a `<Feature>Screen.kt` (Composable), `<Feature>UiState.kt` (immutable state data class), and `<Feature>ViewModel.kt` (`@HiltViewModel`, exposes `uiState` via `mutableStateOf`, private setter). Some screens have a nested `overviews/` or `wizards/` subpackage for sub-flows (e.g. `addActivity/wizards/DiaperWizard.kt`, `overviewActivity/overviews/`).
  - `common/` — shared, reusable Composables, all prefixed `BT` (e.g. `BTbutton`, `BTcardButton`, `BTconfirmDialog`, `BTwizardDialog`, `BTdatePicker`). Reuse these instead of raw Material components when building new screens.
  - `navigation/` — `Destinations` (route string constants) and `SetupNavGraph` (the `NavHost` wiring). New screens need a route constant here and an entry in the nav graph.
  - `layout/` — shared scaffolding (`DefaultLayout`).
  - `theme/` — Compose theme (`Color`, `Theme`, `Type`).

- **Existing feature areas**: Bio (baby profile), Nutrition (Breast/Bottle logs), Rest, Diaper, Weight measurement, Length measurement — each with the full entity → dao → repository → domain interface → ViewModel → screen stack. Use these as the template for new tracked-activity types.

### ViewModel convention

ViewModels are constructor-injected with domain repository interfaces (never DAOs or concrete repos directly), hold a single `UiState` data class via `mutableStateOf` with a private setter, and mutate it with `uiState = uiState.copy(...)`. Writes go through `viewModelScope.launch { repo.someSuspendFn(...) }`.
