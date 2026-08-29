[![](https://jitpack.io/v/mandeep1999/WalkMeThrough.svg)](https://jitpack.io/#mandeep1999/WalkMeThrough)

# WalkMeThrough

**WalkMeThrough** is an Android library that guides users through your app by highlighting specific views and showing instructional UI.

## Add dependency

Add JitPack to your `settings.gradle` if needed:

```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

```gradle
dependencies {
    implementation 'com.github.mandeep1999:WalkMeThrough:v1.0.1'
}
```

## Guided walkthrough (recommended)

```kotlin
import `in`.mandeep_singh.walkmethrough.Position
import `in`.mandeep_singh.walkmethrough.Walkthrough

Walkthrough.from(this)
    .card(findViewById(R.id.profile_icon)) {
        titleText = "Profile"
        descriptionText = "Open your profile from here."
        nextButtonText = "Next"
        placement = Position.CENTER
    }
    .card(findViewById(R.id.settings_icon)) {
        titleText = "Settings"
        descriptionText = "Adjust preferences anytime."
        backButtonText = "Back"
        nextButtonText = "Done"
    }
    .onStepShown { index -> /* analytics */ }
    .onComplete { /* tour finished */ }
    .show()
```

The overlay attaches to the activity content root automatically. Override with `setOverlayParent(viewGroup)` if needed.

## Tooltip guide steps

Use compact tooltip bubbles for quick hints:

```kotlin
Walkthrough.from(this)
    .tooltip(findViewById(R.id.search_icon)) {
        titleText = "Search"
        descriptionText = "Find anything in the app."
        placement = Position.BOTTOM
    }
    .tooltip(findViewById(R.id.filter_icon)) {
        titleText = "Filters"
        placement = Position.TOP
    }
    .onComplete { /* finished */ }
    .show()
```

Tooltip defaults: no dimmed background, no target cutout, tap outside advances. Override per step:

```kotlin
.tooltip(target) {
    dimBackground = true
    highlightTarget = true
    advanceOnOutsideTap = false
}
```

Mix cards and tooltips in one guide:

```kotlin
Walkthrough.from(this)
    .card(profileView) { titleText = "Welcome"; nextButtonText = "Next" }
    .tooltip(searchView) { titleText = "Quick search tip" }
    .show()
```

## Custom UI

- Card steps: `setDialogContent(...)` with `WalkthroughDialogContent`
- Tooltip steps: `setTooltipContent(...)` with `WalkthroughTooltipContent`

## API overview

| Method / type | Purpose |
|---------------|---------|
| `card(target) { ... }` | Card step with optional back/next buttons |
| `tooltip(target) { ... }` | Anchored tooltip bubble |
| `add(GuideStep)` | Add a pre-built guide step |
| `placement` | `Position.TOP`, `CENTER`, or `BOTTOM` relative to target |
| `onStepShown`, `onComplete`, `onDismiss`, `onOutsideClick` | Session callbacks |
| `show()` | Returns `WalkthroughCoordinator` for manual `dismiss()` |

## Screenshots

<img src="https://github.com/user-attachments/assets/e9956af8-32f0-44d0-a135-902500376ef5" alt="WalkMeThrough screenshot" width="400"/>

## License

MIT — see [LICENSE](LICENSE).
