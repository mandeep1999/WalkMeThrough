package `in`.mandeep_singh.walkmethrough

import android.view.View

/**
 * Pluggable card UI for card-style guide steps. Use with [WalkthroughSession.setDialogContent].
 */
interface WalkthroughDialogContent {
    fun createView(
        context: android.content.Context,
        guideStep: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View
}
