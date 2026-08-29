package `in`.mandeep_singh.walkmethrough

import android.view.View

/**
 * Pluggable tooltip UI for tooltip-style guide steps. Use with [WalkthroughSession.setTooltipContent].
 */
interface WalkthroughTooltipContent {
    fun createView(
        context: android.content.Context,
        guideStep: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View
}
