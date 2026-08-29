[![](https://jitpack.io/v/mandeep1999/WalkMeThrough.svg)](https://jitpack.io/#mandeep1999/WalkMeThrough)

# WalkMeThrough

**WalkMeThrough** is an Android library that guides users through your app by highlighting specific views and showing instructional dialogs.

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

## Multi-step walkthrough (recommended)

```kotlin
import `in`.mandeep_singh.walkmethrough.Position
import `in`.mandeep_singh.walkmethrough.Walkthrough

Walkthrough.from(this)
    .step(findViewById(R.id.profile_icon)) {
        titleText = "Profile"
        descriptionText = "Open your profile from here."
        nextButtonText = "Next"
        dialogPosition = Position.CENTER
    }
    .step(findViewById(R.id.settings_icon)) {
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

## Tooltip steps

Use lightweight tooltip bubbles for quick hints without a full dialog card:

```kotlin
Walkthrough.from(this)
    .tooltipStep(findViewById(R.id.search_icon)) {
        titleText = "Search"
        descriptionText = "Find anything in the app."
        dialogPosition = Position.BOTTOM
    }
    .tooltipStep(findViewById(R.id.filter_icon)) {
        titleText = "Filters"
        dialogPosition = Position.TOP
    }
    .onComplete { /* finished */ }
    .show()
```

Tooltip defaults: no dimmed background, no target cutout, tap outside advances to the next step. Override per step:

```kotlin
.tooltipStep(target) {
    dimBackground = true
    highlightTarget = true
    advanceOnOutsideTap = false
}
```

Mix dialog and tooltip steps in one session:

```kotlin
Walkthrough.from(this)
    .step(profileView) { titleText = "Welcome"; nextButtonText = "Next" }
    .tooltipStep(searchView) { titleText = "Quick search tip" }
    .show()
```

Custom tooltip UI via `setTooltipContent(...)`.

## Single-step API (legacy)

`WalkthroughBuilder` remains available for one-off highlights:

```kotlin
import `in`.mandeep_singh.walkmethrough.walk_me_through.components.WalkthroughBuilder
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums.Position

WalkthroughBuilder(this)
    .setViewToHighlight(findViewById(R.id.text_view))
    .setTitleText("Welcome")
    .setDescriptionText("Tap Next to continue.")
    .setNextButtonText("Next")
    .setDialogPosition(Position.CENTER)
    .setOnNextClick { /* ... */ }
    .build()
```

`setParentViewGroup(...)` is optional; when omitted, the library uses the activity content root.

## Custom dialog UI

Provide your own dialog layout via `WalkthroughDialogContent`:

```kotlin
Walkthrough.from(this)
    .setDialogContent(myCustomDialogContent)
    .step(targetView) { titleText = "..." }
    .show()
```

## Configuration highlights

- `step(targetView) { ... }` — add a step with optional styling fields
- `onStepShown`, `onComplete`, `onDismiss`, `onOutsideClick` — session callbacks
- `show()` returns a `WalkthroughCoordinator` for manual `dismiss()`
- Per-step fields include title, description, button text/colors/backgrounds, `dialogPosition`, `dialogPadding`, `StepStyle`, `dimBackground`, `highlightTarget`, and `advanceOnOutsideTap`
- `tooltipStep(...)` — shorthand for compact tooltip bubbles

## Screenshots

<img src="https://github.com/user-attachments/assets/e9956af8-32f0-44d0-a135-902500376ef5" alt="WalkMeThrough screenshot" width="400"/>

## License

MIT — see [LICENSE](LICENSE).
