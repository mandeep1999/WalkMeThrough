[![](https://jitpack.io/v/mandeep1999/WalkMeThrough.svg)](https://jitpack.io/#mandeep1999/WalkMeThrough)


# WalkMeThrough

**WalkMeThrough** is an Android library designed to guide users through your app by highlighting specific views and displaying instructional dialogs.

## Overview

The `WalkthroughBuilder` class helps you create and configure a walkthrough view that highlights a specific UI element and displays a dialog box with customizable content.

## Usage

### 1. Add Dependency

Add JitPack to your `settings.gradle` (or `settings.gradle.kts`) if it is not already present:

```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Include the library in your app module:

```gradle
dependencies {
    implementation 'com.github.mandeep1999:WalkMeThrough:v1.0.1'
}
```

### 2. Set Up Your Activity

Configure a walkthrough from any activity in your app:

```kotlin
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import `in`.mandeep_singh.walkmethrough.walk_me_through.components.WalkthroughBuilder
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums.Position

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val content = findViewById<ViewGroup>(R.id.content)
        val textView = findViewById<TextView>(R.id.text_view)

        WalkthroughBuilder(this)
            .setViewToHighlight(textView)
            .setParentViewGroup(content)
            .setTitleText("Welcome")
            .setDescriptionText("Tap Next to continue the tour.")
            .setNextButtonText("Next")
            .setBackButtonText("Back")
            .setBackButtonBackground(
                ContextCompat.getDrawable(this, R.drawable.your_back_button_background)
            )
            .setNextButtonBackground(
                ContextCompat.getDrawable(this, R.drawable.your_next_button_background)
            )
            .setBackButtonTextColor(getColor(R.color.your_back_button_text_color))
            .setNextButtonTextColor(getColor(R.color.your_next_button_text_color))
            .setOnBackClick { Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show() }
            .setOnNextClick { Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show() }
            .setDialogPosition(Position.CENTER)
            .setOnOutsideClickListener { Toast.makeText(this, "Outside", Toast.LENGTH_SHORT).show() }
            .setOnCloseClick { Toast.makeText(this, "Close", Toast.LENGTH_SHORT).show() }
            .build()
    }
}
```

### 3. Configuration Options

The `WalkthroughBuilder` class allows you to configure the following:

- `setViewToHighlight(view: View)`: The view to highlight in the walkthrough.
- `setParentViewGroup(viewGroup: ViewGroup)`: The parent view group where the walkthrough view will be added.
- `setTitleText(text: String)`: The title text to display in the dialog.
- `setDescriptionText(text: String)`: The description text to display in the dialog.
- `setNextButtonText(text: String)`: The text for the "Next" button.
- `setBackButtonText(text: String)`: The text for the "Back" button.
- `setBackButtonBackground(drawable: Drawable)`: Background drawable for the "Back" button.
- `setNextButtonBackground(drawable: Drawable)`: Background drawable for the "Next" button.
- `setBackButtonTextColor(color: Int)`: Text color for the "Back" button.
- `setNextButtonTextColor(color: Int)`: Text color for the "Next" button.
- `setOnBackClick(listener: () -> Unit)`: Callback for when the "Back" button is clicked.
- `setOnNextClick(listener: () -> Unit)`: Callback for when the "Next" button is clicked.
- `setDialogPosition(position: Position)`: Position of the dialog relative to the highlighted view.
- `setOnOutsideClickListener(listener: () -> Unit)`: Callback for clicks outside the dialog.
- `setOnCloseClick(listener: () -> Unit)`: Callback for the close button click.

### 4. Screenshots

<img src="https://github.com/user-attachments/assets/e9956af8-32f0-44d0-a135-902500376ef5" alt="WalkMeThrough screenshot" width="400"/>

### License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
