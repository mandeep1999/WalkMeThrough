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

Mix presentation types in one guide:

```kotlin
Walkthrough.from(this)
    .fullScreen(contentRoot) {
        titleText = "Welcome"
        descriptionText = "A quick tour of the app."
        nextButtonText = "Start"
    }
    .spotlight(featureView)
    .tooltip(searchView) { titleText = "Quick search tip"; placement = Position.BOTTOM }
    .card(settingsView) { titleText = "Settings"; nextButtonText = "Done" }
    .banner(cartView) { titleText = "Cart"; nextButtonText = "Next" }
    .show()
```

## Spotlight steps

Highlight a target with no instructional UI — useful for “look here” moments:

```kotlin
Walkthrough.from(this)
    .spotlight(findViewById(R.id.feature_button))
    .show()
```

Defaults: dimmed background, spotlight cutout, tap outside advances.

## Banner steps

Bottom banner anchored to the screen edge:

```kotlin
Walkthrough.from(this)
    .banner(findViewById(R.id.cart_icon)) {
        titleText = "Your cart"
        descriptionText = "Review items before checkout."
        nextButtonText = "Continue"
    }
    .show()
```

## Full-screen steps

Centered card over a dimmed background — good for intro or summary screens:

```kotlin
Walkthrough.from(this)
    .fullScreen(findViewById(android.R.id.content)) {
        titleText = "Welcome"
        descriptionText = "Let's walk through the basics."
        nextButtonText = "Get started"
    }
    .show()
```

## Custom UI

Use `setGuideContent(...)` with `WalkthroughGuideContent` to replace default UI for any presentation type. Return `null` for spotlight-only steps with no overlay content.

## API overview

| Method / type | Purpose |
|---------------|---------|
| `card(target) { ... }` | Instructional card with optional back/next buttons |
| `tooltip(target) { ... }` | Compact anchored tooltip bubble |
| `spotlight(target) { ... }` | Spotlight cutout only, no instructional UI |
| `banner(target) { ... }` | Bottom banner with title, description, and next action |
| `fullScreen(target) { ... }` | Full-screen centered card over dimmed background |
| `add(GuideStep)` | Add a pre-built guide step |
| `setGuideContent(...)` | Custom UI via `WalkthroughGuideContent` |
| `placement` | `Position.TOP`, `CENTER`, or `BOTTOM` relative to target |
| `onStepShown`, `onComplete`, `onDismiss`, `onOutsideClick` | Session callbacks |
| `show()` | Returns `WalkthroughCoordinator` for manual `dismiss()` |

### GuidePresentation defaults

| Presentation | Dim background | Highlight target | Tap outside advances |
|--------------|----------------|------------------|------------------------|
| `CARD` | yes | yes | no |
| `TOOLTIP` | no | no | yes |
| `SPOTLIGHT` | yes | yes | yes |
| `BANNER` | yes | yes | no |
| `FULL_SCREEN` | yes | no | no |

## Screenshots

<img src="https://github.com/user-attachments/assets/e9956af8-32f0-44d0-a135-902500376ef5" alt="WalkMeThrough screenshot" width="400"/>

## License

MIT — see [LICENSE](LICENSE).
