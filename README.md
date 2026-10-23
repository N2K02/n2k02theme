# N2K02 Theme

Shared Jetpack Compose Material 3 Expressive design system for N2K02 Android applications.

## Principles
- Follow mRadio's uncluttered visual language, with Material You dynamic colors.
- Keep app-specific screens and permissions in their respective apps.
- Consistent controls: loading actions, settings rows, permission states, update states, status cards.
- Accessible semantics, readable contrast, large font support, no duplicated actions.
- Do not couple UI components to GitHub release APIs, Android services or app package names.

## Modules
- `theme`: reusable Android Compose UI library.

## Integration
This is a source-first module, **not a published dependency** yet. During initial integration, include the library as a Gradle composite build or publish a versioned artifact to a private package registry. Do not point public repositories at unauthenticated private package URLs.

Each consuming application must use a compatible Android Gradle Plugin, Kotlin/Compose compiler and Material 3 version. Align those versions before wiring this module into apps.

## Roadmap
1. Establish tokens and composables; validate library build.
2. Pilot in mRadio and BCam; test light/dark, font scale and loading states.
3. Standardize updater, permission and settings screens in the remaining apps.
4. Audit accessibility and release builds; review PRs individually.

No app update logic or sensitive signing keys belong in this repository.
