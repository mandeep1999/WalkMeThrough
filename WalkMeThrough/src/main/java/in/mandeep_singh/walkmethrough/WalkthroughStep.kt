package `in`.mandeep_singh.walkmethrough

import android.graphics.drawable.Drawable
import android.view.View

/**
 * Configuration for a single walkthrough step.
 */
data class WalkthroughStep(
    val targetView: View,
    val titleText: String? = null,
    val descriptionText: String? = null,
    val backButtonText: String? = null,
    val nextButtonText: String? = null,
    val dialogBackgroundColor: Int? = null,
    val titleTextColor: Int? = null,
    val descriptionTextColor: Int? = null,
    val backButtonTextColor: Int? = null,
    val nextButtonTextColor: Int? = null,
    val backButtonBackgroundColor: Int? = null,
    val nextButtonBackgroundColor: Int? = null,
    val dialogBackground: Drawable? = null,
    val nextButtonBackground: Drawable? = null,
    val backButtonBackground: Drawable? = null,
    val dialogPadding: Padding? = null,
    val dialogPosition: Position? = null,
)

/**
 * Fluent builder for [WalkthroughStep].
 */
class WalkthroughStepBuilder(private val targetView: View) {
    var titleText: String? = null
    var descriptionText: String? = null
    var backButtonText: String? = null
    var nextButtonText: String? = null
    var dialogBackgroundColor: Int? = null
    var titleTextColor: Int? = null
    var descriptionTextColor: Int? = null
    var backButtonTextColor: Int? = null
    var nextButtonTextColor: Int? = null
    var backButtonBackgroundColor: Int? = null
    var nextButtonBackgroundColor: Int? = null
    var dialogBackground: Drawable? = null
    var nextButtonBackground: Drawable? = null
    var backButtonBackground: Drawable? = null
    var dialogPadding: Padding? = null
    var dialogPosition: Position? = null

    fun build(): WalkthroughStep = WalkthroughStep(
        targetView = targetView,
        titleText = titleText,
        descriptionText = descriptionText,
        backButtonText = backButtonText,
        nextButtonText = nextButtonText,
        dialogBackgroundColor = dialogBackgroundColor,
        titleTextColor = titleTextColor,
        descriptionTextColor = descriptionTextColor,
        backButtonTextColor = backButtonTextColor,
        nextButtonTextColor = nextButtonTextColor,
        backButtonBackgroundColor = backButtonBackgroundColor,
        nextButtonBackgroundColor = nextButtonBackgroundColor,
        dialogBackground = dialogBackground,
        nextButtonBackground = nextButtonBackground,
        backButtonBackground = backButtonBackground,
        dialogPadding = dialogPadding,
        dialogPosition = dialogPosition,
    )
}
