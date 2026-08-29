package `in`.mandeep_singh.walkmethrough

import android.view.View

/**
 * Pluggable tooltip UI for tooltip-style steps. Use with [WalkthroughSession.setTooltipContent].
 */
interface WalkthroughTooltipContent {
    fun createView(
        context: android.content.Context,
        step: WalkthroughStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View
}
