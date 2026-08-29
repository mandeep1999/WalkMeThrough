package `in`.mandeep_singh.walkmethrough.internal.content

import android.content.Context
import android.view.View
import `in`.mandeep_singh.walkmethrough.GuidePresentation
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.WalkthroughActions
import `in`.mandeep_singh.walkmethrough.WalkthroughGuideContent
import `in`.mandeep_singh.walkmethrough.internal.banner.DefaultBannerView
import `in`.mandeep_singh.walkmethrough.internal.dialog.DefaultDialogView
import `in`.mandeep_singh.walkmethrough.internal.fullscreen.DefaultFullScreenView
import `in`.mandeep_singh.walkmethrough.internal.tooltip.DefaultTooltipView

internal class DefaultWalkthroughGuideContent : WalkthroughGuideContent {

    override fun createView(
        context: Context,
        guideStep: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View? {
        return when (guideStep.presentation) {
            GuidePresentation.SPOTLIGHT -> null
            GuidePresentation.TOOLTIP -> DefaultTooltipView.create(
                context,
                guideStep,
                stepIndex,
                totalSteps,
                actions,
            )
            GuidePresentation.BANNER -> DefaultBannerView.create(
                context,
                guideStep,
                stepIndex,
                totalSteps,
                actions,
            )
            GuidePresentation.FULL_SCREEN -> DefaultFullScreenView.create(
                context,
                guideStep,
                stepIndex,
                totalSteps,
                actions,
            )
            GuidePresentation.CARD -> DefaultDialogView.create(
                context,
                guideStep,
                stepIndex,
                totalSteps,
                actions,
            )
        }
    }
}
