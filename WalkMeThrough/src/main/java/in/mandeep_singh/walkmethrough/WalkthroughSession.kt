package `in`.mandeep_singh.walkmethrough

import android.app.Activity
import android.view.View
import android.view.ViewGroup

/**
 * Configures a guided walkthrough and displays it on an activity.
 */
class WalkthroughSession internal constructor(private val activity: Activity) {

    private val guideSteps = mutableListOf<GuideStep>()
    private var onStepShown: ((Int) -> Unit)? = null
    private var onComplete: (() -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onOutsideClick: (() -> Unit)? = null
    private var guideContent: WalkthroughGuideContent? = null
    private var overlayParent: ViewGroup? = null

    fun add(guideStep: GuideStep) = apply { guideSteps.add(guideStep) }

    fun card(targetView: View, configure: CardStepBuilder.() -> Unit = {}) = apply {
        val builder = CardStepBuilder(targetView)
        builder.configure()
        guideSteps.add(builder.build())
    }

    fun tooltip(targetView: View, configure: TooltipStepBuilder.() -> Unit = {}) = apply {
        val builder = TooltipStepBuilder(targetView)
        builder.configure()
        guideSteps.add(builder.build())
    }

    /** Spotlight cutout on [targetView] with no instructional UI. Tap outside to advance. */
    fun spotlight(targetView: View, configure: SpotlightStepBuilder.() -> Unit = {}) = apply {
        val builder = SpotlightStepBuilder(targetView)
        builder.configure()
        guideSteps.add(builder.build())
    }

    /** Bottom banner with optional title, description, and next action. */
    fun banner(targetView: View, configure: BannerStepBuilder.() -> Unit = {}) = apply {
        val builder = BannerStepBuilder(targetView)
        builder.configure()
        guideSteps.add(builder.build())
    }

    /** Full-screen centered card — useful for intro or summary steps. */
    fun fullScreen(targetView: View, configure: FullScreenStepBuilder.() -> Unit = {}) = apply {
        val builder = FullScreenStepBuilder(targetView)
        builder.configure()
        guideSteps.add(builder.build())
    }

    fun onStepShown(listener: (Int) -> Unit) = apply { onStepShown = listener }

    fun onComplete(listener: () -> Unit) = apply { onComplete = listener }

    fun onDismiss(listener: () -> Unit) = apply { onDismiss = listener }

    fun onOutsideClick(listener: () -> Unit) = apply { onOutsideClick = listener }

    fun setGuideContent(content: WalkthroughGuideContent) = apply { guideContent = content }

    fun setOverlayParent(viewGroup: ViewGroup) = apply { overlayParent = viewGroup }

    fun show(): WalkthroughCoordinator {
        require(guideSteps.isNotEmpty()) {
            "Add at least one guide step before calling show()"
        }

        val coordinator = WalkthroughCoordinator(
            activity = activity,
            guideSteps = guideSteps.toList(),
            overlayParent = overlayParent,
            guideContent = guideContent,
            onStepShown = onStepShown,
            onComplete = onComplete,
            onDismiss = onDismiss,
            onOutsideClick = onOutsideClick,
        )
        coordinator.start()
        return coordinator
    }
}
