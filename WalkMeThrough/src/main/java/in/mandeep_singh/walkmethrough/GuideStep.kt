package `in`.mandeep_singh.walkmethrough

import android.graphics.drawable.Drawable
import android.view.View

/**
 * Immutable configuration for a single guide step.
 */
data class GuideStep(
    val target: View,
    val presentation: GuidePresentation = GuidePresentation.CARD,
    val title: String? = null,
    val description: String? = null,
    val backText: String? = null,
    val nextText: String? = null,
    val backgroundColor: Int? = null,
    val titleColor: Int? = null,
    val descriptionColor: Int? = null,
    val backTextColor: Int? = null,
    val nextTextColor: Int? = null,
    val backBackgroundColor: Int? = null,
    val nextBackgroundColor: Int? = null,
    val background: Drawable? = null,
    val nextBackground: Drawable? = null,
    val backBackground: Drawable? = null,
    val padding: Padding? = null,
    val placement: Placement? = null,
    val dimBackground: Boolean? = null,
    val highlightTarget: Boolean? = null,
    val showArrow: Boolean = true,
    val advanceOnOutsideTap: Boolean = false,
)

@WalkthroughDsl
abstract class StepBuilder internal constructor(
    internal val target: View,
    internal val presentation: GuidePresentation,
) {
    var title: String? = null
    var description: String? = null
    var backText: String? = null
    var nextText: String? = null
    var backgroundColor: Int? = null
    var titleColor: Int? = null
    var descriptionColor: Int? = null
    var backTextColor: Int? = null
    var nextTextColor: Int? = null
    var backBackgroundColor: Int? = null
    var nextBackgroundColor: Int? = null
    var background: Drawable? = null
    var nextBackground: Drawable? = null
    var backBackground: Drawable? = null
    var padding: Padding? = null
    var placement: Placement? = null
    var dimBackground: Boolean? = null
    var highlightTarget: Boolean? = null
    var advanceOnOutsideTap: Boolean = false
    var showArrow: Boolean = true

    internal fun toGuideStep(): GuideStep = GuideStep(
        target = target,
        presentation = presentation,
        title = title,
        description = description,
        backText = backText,
        nextText = nextText,
        backgroundColor = backgroundColor,
        titleColor = titleColor,
        descriptionColor = descriptionColor,
        backTextColor = backTextColor,
        nextTextColor = nextTextColor,
        backBackgroundColor = backBackgroundColor,
        nextBackgroundColor = nextBackgroundColor,
        background = background,
        nextBackground = nextBackground,
        backBackground = backBackground,
        padding = padding,
        placement = placement,
        dimBackground = dimBackground,
        highlightTarget = highlightTarget,
        showArrow = showArrow,
        advanceOnOutsideTap = advanceOnOutsideTap,
    )
}

@WalkthroughDsl
class CardStepBuilder internal constructor(target: View) : StepBuilder(target, GuidePresentation.CARD)

@WalkthroughDsl
class TooltipStepBuilder internal constructor(target: View) : StepBuilder(target, GuidePresentation.TOOLTIP) {
    init {
        dimBackground = false
        highlightTarget = false
        advanceOnOutsideTap = true
    }
}

@WalkthroughDsl
class SpotlightStepBuilder internal constructor(target: View) : StepBuilder(target, GuidePresentation.SPOTLIGHT) {
    init {
        dimBackground = true
        highlightTarget = true
        advanceOnOutsideTap = true
    }
}

@WalkthroughDsl
class BannerStepBuilder internal constructor(target: View) : StepBuilder(target, GuidePresentation.BANNER) {
    init {
        dimBackground = true
        highlightTarget = true
        advanceOnOutsideTap = false
    }
}

@WalkthroughDsl
class FullScreenStepBuilder internal constructor(target: View) : StepBuilder(target, GuidePresentation.FULL_SCREEN) {
    init {
        dimBackground = true
        highlightTarget = false
        advanceOnOutsideTap = false
    }
}
