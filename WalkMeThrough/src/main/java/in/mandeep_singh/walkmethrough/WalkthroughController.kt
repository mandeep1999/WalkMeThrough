package `in`.mandeep_singh.walkmethrough

import android.app.Activity
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import `in`.mandeep_singh.walkmethrough.internal.content.DefaultGuideContent
import `in`.mandeep_singh.walkmethrough.internal.overlay.OverlayScreen
import `in`.mandeep_singh.walkmethrough.internal.util.WalkthroughPositioning

/**
 * Controls an active walkthrough. Returned from [WalkthroughBuilder.show].
 */
class WalkthroughController internal constructor(
    private val activity: Activity,
    private val steps: List<GuideStep>,
    private val overlayParent: ViewGroup?,
    private val guideContent: GuideContent?,
    private val listener: WalkthroughListener?,
) : DefaultLifecycleObserver, GuideActions {

    private var overlay: OverlayScreen? = null
    private var currentStepIndex = 0
    private var started = false

    internal fun start() {
        if (started) return
        started = true
        if (activity is ComponentActivity) {
            activity.lifecycle.addObserver(this)
        }
        showStep(0)
    }

    fun dismiss() {
        if (!started) return
        started = false
        if (activity is ComponentActivity) {
            activity.lifecycle.removeObserver(this)
        }
        overlay?.dismiss()
        overlay = null
        listener?.onDismiss()
    }

    fun isShowing(): Boolean = overlay != null

    override fun onDestroy(owner: LifecycleOwner) {
        dismiss()
    }

    override fun onBack() {
        if (currentStepIndex > 0) {
            showStep(currentStepIndex - 1)
        }
    }

    override fun onNext() {
        if (currentStepIndex < steps.lastIndex) {
            showStep(currentStepIndex + 1)
        } else {
            listener?.onComplete()
            dismiss()
        }
    }

    override fun onClose() {
        dismiss()
    }

    override fun onOutsideClick() {
        val step = steps[currentStepIndex]
        listener?.onOutsideClick()
        if (step.advanceOnOutsideTap) {
            onNext()
        }
    }

    private fun showStep(index: Int) {
        currentStepIndex = index
        val step = steps[index]
        val parent = overlayParent ?: activityOverlayParent()
        val contentView = createContentView(step, index)
        val dimBackground = WalkthroughPositioning.resolveDimBackground(
            step.presentation,
            step.dimBackground,
        )
        val highlightTarget = WalkthroughPositioning.resolveHighlightTarget(
            step.presentation,
            step.highlightTarget,
        )

        val overlayScreen = overlay ?: OverlayScreen(activity).also { overlay = it }

        if (overlayScreen.parent == null) {
            overlayScreen.show(
                parentViewGroup = parent,
                viewToHighlight = step.target,
                contentView = contentView,
                contentPlacement = step.placement,
                presentation = step.presentation,
                dimBackground = dimBackground,
                highlightTarget = highlightTarget,
                onOutsideClick = ::onOutsideClick,
            )
        } else {
            overlayScreen.updateStep(
                viewToHighlight = step.target,
                contentView = contentView,
                contentPlacement = step.placement,
                presentation = step.presentation,
                dimBackground = dimBackground,
                highlightTarget = highlightTarget,
                onOutsideClick = ::onOutsideClick,
            )
        }

        listener?.onStepShown(index)
    }

    private fun createContentView(step: GuideStep, index: Int): android.view.View? {
        val content = guideContent ?: DefaultGuideContent()
        return content.onCreateGuideView(
            context = activity,
            step = step,
            index = index,
            stepCount = steps.size,
            actions = this,
        )
    }

    private fun activityOverlayParent(): ViewGroup {
        return activity.findViewById(android.R.id.content)
    }
}
