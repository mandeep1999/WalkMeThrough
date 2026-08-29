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
    private var dialogContent: WalkthroughDialogContent? = null
    private var tooltipContent: WalkthroughTooltipContent? = null
    private var overlayParent: ViewGroup? = null

    fun add(guideStep: GuideStep) = apply { guideSteps.add(guideStep) }

    /**
     * Adds a card-style step with title, description, and optional navigation buttons.
     */
    fun card(targetView: View, configure: CardStepBuilder.() -> Unit = {}) = apply {
        val builder = CardStepBuilder(targetView)
        builder.configure()
        guideSteps.add(builder.build())
    }

    /**
     * Adds a compact tooltip step anchored near [targetView].
     */
    fun tooltip(targetView: View, configure: TooltipStepBuilder.() -> Unit = {}) = apply {
        val builder = TooltipStepBuilder(targetView)
        builder.configure()
        guideSteps.add(builder.build())
    }

    fun onStepShown(listener: (Int) -> Unit) = apply { onStepShown = listener }

    fun onComplete(listener: () -> Unit) = apply { onComplete = listener }

    fun onDismiss(listener: () -> Unit) = apply { onDismiss = listener }

    fun onOutsideClick(listener: () -> Unit) = apply { onOutsideClick = listener }

    fun setDialogContent(content: WalkthroughDialogContent) = apply { dialogContent = content }

    fun setTooltipContent(content: WalkthroughTooltipContent) = apply { tooltipContent = content }

    /**
     * Optional override for the view group that hosts the overlay. By default the activity content root is used.
     */
    fun setOverlayParent(viewGroup: ViewGroup) = apply { overlayParent = viewGroup }

    fun show(): WalkthroughCoordinator {
        require(guideSteps.isNotEmpty()) { "Add at least one guide step with card() or tooltip() before calling show()" }

        val coordinator = WalkthroughCoordinator(
            activity = activity,
            guideSteps = guideSteps.toList(),
            overlayParent = overlayParent,
            dialogContent = dialogContent,
            tooltipContent = tooltipContent,
            onStepShown = onStepShown,
            onComplete = onComplete,
            onDismiss = onDismiss,
            onOutsideClick = onOutsideClick,
        )
        coordinator.start()
        return coordinator
    }
}
