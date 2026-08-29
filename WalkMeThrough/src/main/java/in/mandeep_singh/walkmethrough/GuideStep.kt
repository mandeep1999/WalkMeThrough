package `in`.mandeep_singh.walkmethrough

import android.graphics.drawable.Drawable
import android.view.View

/**
 * Configuration for a single step in a guided walkthrough.
 */
data class GuideStep(
    val targetView: View,
    val presentation: GuidePresentation = GuidePresentation.CARD,
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
    /** Preferred placement relative to the highlighted target. */
    val placement: Position? = null,
    /** When null, defaults to true for [GuidePresentation.CARD] and false for [GuidePresentation.TOOLTIP]. */
    val dimBackground: Boolean? = null,
    /** When null, defaults to true for cards and false for tooltips. */
    val highlightTarget: Boolean? = null,
    val showArrow: Boolean = true,
    val advanceOnOutsideTap: Boolean = false,
)

/**
 * Fluent builder for a card-style guide step.
 */
class CardStepBuilder(private val targetView: View) {
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
    var placement: Position? = null
    var dimBackground: Boolean? = null
    var highlightTarget: Boolean? = null
    var advanceOnOutsideTap: Boolean = false

    fun build(): GuideStep = GuideStep(
        targetView = targetView,
        presentation = GuidePresentation.CARD,
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
        placement = placement,
        dimBackground = dimBackground,
        highlightTarget = highlightTarget,
        advanceOnOutsideTap = advanceOnOutsideTap,
    )
}

/**
 * Fluent builder for a tooltip-style guide step.
 */
class TooltipStepBuilder(private val targetView: View) {
    var titleText: String? = null
    var descriptionText: String? = null
    var titleTextColor: Int? = null
    var descriptionTextColor: Int? = null
    var dialogBackgroundColor: Int? = null
    var dialogBackground: Drawable? = null
    var dialogPadding: Padding? = null
    var placement: Position? = null
    var dimBackground: Boolean? = false
    var highlightTarget: Boolean? = false
    var showArrow: Boolean = true
    var advanceOnOutsideTap: Boolean = true

    fun build(): GuideStep = GuideStep(
        targetView = targetView,
        presentation = GuidePresentation.TOOLTIP,
        titleText = titleText,
        descriptionText = descriptionText,
        titleTextColor = titleTextColor,
        descriptionTextColor = descriptionTextColor,
        dialogBackgroundColor = dialogBackgroundColor,
        dialogBackground = dialogBackground,
        dialogPadding = dialogPadding,
        placement = placement,
        dimBackground = dimBackground,
        highlightTarget = highlightTarget,
        showArrow = showArrow,
        advanceOnOutsideTap = advanceOnOutsideTap,
    )
}
