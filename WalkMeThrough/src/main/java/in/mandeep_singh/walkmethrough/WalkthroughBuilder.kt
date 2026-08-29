package `in`.mandeep_singh.walkmethrough

import android.app.Activity
import android.view.View
import android.view.ViewGroup

/**
 * Configures a guided walkthrough before displaying it.
 *
 * Create with [Walkthrough.with].
 */
@WalkthroughDsl
class WalkthroughBuilder internal constructor(private val activity: Activity) {

    private val steps = mutableListOf<GuideStep>()
    private var listener: WalkthroughListener? = null
    private var guideContent: GuideContent? = null
    private var overlayParent: ViewGroup? = null

    fun add(step: GuideStep) = apply { steps.add(step) }

    fun card(target: View, configure: CardStepBuilder.() -> Unit = {}) = apply {
        steps.add(CardStepBuilder(target).apply(configure).toGuideStep())
    }

    fun tooltip(target: View, configure: TooltipStepBuilder.() -> Unit = {}) = apply {
        steps.add(TooltipStepBuilder(target).apply(configure).toGuideStep())
    }

    /** Spotlight cutout on [target] with no instructional UI. Tap outside to advance by default. */
    fun spotlight(target: View, configure: SpotlightStepBuilder.() -> Unit = {}) = apply {
        steps.add(SpotlightStepBuilder(target).apply(configure).toGuideStep())
    }

    /** Bottom banner with optional title, description, and next action. */
    fun banner(target: View, configure: BannerStepBuilder.() -> Unit = {}) = apply {
        steps.add(BannerStepBuilder(target).apply(configure).toGuideStep())
    }

    /** Full-screen centered card — useful for intro or summary steps. */
    fun fullScreen(target: View, configure: FullScreenStepBuilder.() -> Unit = {}) = apply {
        steps.add(FullScreenStepBuilder(target).apply(configure).toGuideStep())
    }

    fun overlayParent(parent: ViewGroup) = apply { overlayParent = parent }

    fun guideContent(content: GuideContent) = apply { guideContent = content }

    fun setListener(listener: WalkthroughListener) = apply { this.listener = listener }

    fun doOnStepShown(action: (Int) -> Unit) = apply {
        listener = mergeListener(listener, onStepShown = action)
    }

    fun doOnComplete(action: () -> Unit) = apply {
        listener = mergeListener(listener, onComplete = action)
    }

    fun doOnDismiss(action: () -> Unit) = apply {
        listener = mergeListener(listener, onDismiss = action)
    }

    fun doOnOutsideClick(action: () -> Unit) = apply {
        listener = mergeListener(listener, onOutsideClick = action)
    }

    fun show(): WalkthroughController {
        require(steps.isNotEmpty()) { "Add at least one guide step before calling show()" }

        return WalkthroughController(
            activity = activity,
            steps = steps.toList(),
            overlayParent = overlayParent,
            guideContent = guideContent,
            listener = listener,
        ).also { it.start() }
    }

    private fun mergeListener(
        existing: WalkthroughListener?,
        onStepShown: ((Int) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null,
        onOutsideClick: (() -> Unit)? = null,
    ): WalkthroughListener {
        return object : WalkthroughListener {
            override fun onStepShown(index: Int) {
                existing?.onStepShown(index)
                onStepShown?.invoke(index)
            }

            override fun onComplete() {
                existing?.onComplete()
                onComplete?.invoke()
            }

            override fun onDismiss() {
                existing?.onDismiss()
                onDismiss?.invoke()
            }

            override fun onOutsideClick() {
                existing?.onOutsideClick()
                onOutsideClick?.invoke()
            }
        }
    }
}
