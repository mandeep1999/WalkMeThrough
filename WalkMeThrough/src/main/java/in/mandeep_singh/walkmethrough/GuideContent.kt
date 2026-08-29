package `in`.mandeep_singh.walkmethrough

import android.content.Context
import android.view.View

/**
 * Supplies custom UI for guide steps. Return null when no overlay content is needed
 * (for example spotlight-only steps).
 */
interface GuideContent {
    fun onCreateGuideView(
        context: Context,
        step: GuideStep,
        index: Int,
        stepCount: Int,
        actions: GuideActions,
    ): View?
}
