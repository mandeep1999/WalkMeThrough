package `in`.mandeep_singh.walkmethrough.internal.content

import android.content.Context
import android.view.View
import `in`.mandeep_singh.walkmethrough.GuideActions
import `in`.mandeep_singh.walkmethrough.GuideContent
import `in`.mandeep_singh.walkmethrough.GuidePresentation
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.internal.banner.DefaultBannerView
import `in`.mandeep_singh.walkmethrough.internal.dialog.DefaultDialogView
import `in`.mandeep_singh.walkmethrough.internal.fullscreen.DefaultFullScreenView
import `in`.mandeep_singh.walkmethrough.internal.tooltip.DefaultTooltipView

internal class DefaultGuideContent : GuideContent {

    override fun onCreateGuideView(
        context: Context,
        step: GuideStep,
        index: Int,
        stepCount: Int,
        actions: GuideActions,
    ): View? {
        return when (step.presentation) {
            GuidePresentation.SPOTLIGHT -> null
            GuidePresentation.TOOLTIP -> DefaultTooltipView.create(context, step, index, stepCount, actions)
            GuidePresentation.BANNER -> DefaultBannerView.create(context, step, index, stepCount, actions)
            GuidePresentation.FULL_SCREEN -> DefaultFullScreenView.create(context, step, index, stepCount, actions)
            GuidePresentation.CARD -> DefaultDialogView.create(context, step, index, stepCount, actions)
        }
    }
}
