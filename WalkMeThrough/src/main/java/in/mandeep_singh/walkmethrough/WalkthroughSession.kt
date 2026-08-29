package `in`.mandeep_singh.walkmethrough

import `in`.mandeep_singh.walkmethrough.WalkthroughCoordinator
import android.app.Activity
import android.view.View
import android.view.ViewGroup

/**
 * Configures a multi-step walkthrough and displays it on an activity.
 */
class WalkthroughSession internal constructor(private val activity: Activity) {

    private val steps = mutableListOf<WalkthroughStep>()
    private var onStepShown: ((Int) -> Unit)? = null
    private var onComplete: (() -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    private var onOutsideClick: (() -> Unit)? = null
    private var dialogContent: WalkthroughDialogContent? = null
    private var overlayParent: ViewGroup? = null

    fun addStep(step: WalkthroughStep) = apply { steps.add(step) }

    fun step(targetView: View, configure: WalkthroughStepBuilder.() -> Unit = {}) = apply {
        val builder = WalkthroughStepBuilder(targetView)
        builder.configure()
        steps.add(builder.build())
    }

    fun onStepShown(listener: (Int) -> Unit) = apply { onStepShown = listener }

    fun onComplete(listener: () -> Unit) = apply { onComplete = listener }

    fun onDismiss(listener: () -> Unit) = apply { onDismiss = listener }

    fun onOutsideClick(listener: () -> Unit) = apply { onOutsideClick = listener }

    fun setDialogContent(content: WalkthroughDialogContent) = apply { dialogContent = content }

    /**
     * Optional override for the view group that hosts the overlay. By default the activity content root is used.
     */
    fun setOverlayParent(viewGroup: ViewGroup) = apply { overlayParent = viewGroup }

    /**
     * Starts the walkthrough and returns the coordinator for manual dismissal if needed.
     */
    fun show(): WalkthroughCoordinator {
        require(steps.isNotEmpty()) { "At least one walkthrough step must be added before calling show()" }

        val coordinator = WalkthroughCoordinator(
            activity = activity,
            steps = steps.toList(),
            overlayParent = overlayParent,
            dialogContent = dialogContent,
            onStepShown = onStepShown,
            onComplete = onComplete,
            onDismiss = onDismiss,
            onOutsideClick = onOutsideClick,
        )
        coordinator.start()
        return coordinator
    }
}
