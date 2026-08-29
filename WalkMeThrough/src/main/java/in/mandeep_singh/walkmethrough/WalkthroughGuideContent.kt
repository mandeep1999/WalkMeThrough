package `in`.mandeep_singh.walkmethrough

import android.view.View

/**
 * Pluggable UI for guide steps. Return null when no overlay content is needed (e.g. spotlight-only steps).
 */
interface WalkthroughGuideContent {
    fun createView(
        context: android.content.Context,
        guideStep: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View?
}
