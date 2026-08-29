package `in`.mandeep_singh.walkmethrough

import android.graphics.drawable.Drawable
import android.view.View

/**
 * Configuration for a single walkthrough step.
 */
data class WalkthroughStep(
    val targetView: View,
    val style: StepStyle = StepStyle.DIALOG,
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
    /** When null, defaults to true for [StepStyle.DIALOG] and false for [StepStyle.TOOLTIP]. */
    val dimBackground: Boolean? = null,
    /** When null, defaults to true. */
    val highlightTarget: Boolean? = null,
    /** Whether the tooltip arrow is shown (tooltip style only). */
    val showTooltipArrow: Boolean = true,
    /** Tooltip steps advance to the next step when the user taps outside the bubble. */
    val advanceOnOutsideTap: Boolean = false,
)

/**
 * Fluent builder for [WalkthroughStep].
 */
class WalkthroughStepBuilder(private val targetView: View) {
    var style: StepStyle = StepStyle.DIALOG
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
    var dimBackground: Boolean? = null
    var highlightTarget: Boolean? = null
    var showTooltipArrow: Boolean = true
    var advanceOnOutsideTap: Boolean = false

    fun build(): WalkthroughStep = WalkthroughStep(
        targetView = targetView,
        style = style,
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
        dimBackground = dimBackground,
        highlightTarget = highlightTarget,
        showTooltipArrow = showTooltipArrow,
        advanceOnOutsideTap = advanceOnOutsideTap,
    )
}
