package `in`.mandeep_singh.walkmethrough

import android.app.Activity
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import `in`.mandeep_singh.walkmethrough.internal.content.DefaultWalkthroughGuideContent
import `in`.mandeep_singh.walkmethrough.internal.overlay.OverlayScreen
import `in`.mandeep_singh.walkmethrough.internal.util.WalkthroughPositioning

class WalkthroughCoordinator internal constructor(
    private val activity: Activity,
    private val guideSteps: List<GuideStep>,
    private val overlayParent: ViewGroup?,
    private val guideContent: WalkthroughGuideContent?,
    private val onStepShown: ((Int) -> Unit)?,
    private val onComplete: (() -> Unit)?,
    private val onDismiss: (() -> Unit)?,
    private val onOutsideClick: (() -> Unit)?,
) : DefaultLifecycleObserver, WalkthroughActions {

    private var overlay: OverlayScreen? = null
    private var currentStepIndex = 0
    private var started = false

    fun start() {
        if (started) return
        started = true
        if (activity is ComponentActivity) {
            activity.lifecycle.addObserver(this)
        }
        showGuideStep(0)
    }

    fun dismiss() {
        if (!started) return
        started = false
        if (activity is ComponentActivity) {
            activity.lifecycle.removeObserver(this)
        }
        overlay?.dismiss()
        overlay = null
        onDismiss?.invoke()
    }

    fun isShowing(): Boolean = overlay != null

    override fun onDestroy(owner: LifecycleOwner) {
        dismiss()
    }

    override fun onBack() {
        if (currentStepIndex > 0) {
            showGuideStep(currentStepIndex - 1)
        }
    }

    override fun onNext() {
        if (currentStepIndex < guideSteps.lastIndex) {
            showGuideStep(currentStepIndex + 1)
        } else {
            onComplete?.invoke()
            dismiss()
        }
    }

    override fun onClose() {
        dismiss()
    }

    override fun onOutsideClick() {
        val guideStep = guideSteps[currentStepIndex]
        onOutsideClick?.invoke()
        if (guideStep.advanceOnOutsideTap) {
            onNext()
        }
    }

    private fun showGuideStep(index: Int) {
        currentStepIndex = index
        val guideStep = guideSteps[index]
        val parent = overlayParent ?: activityOverlayParent()
        val contentView = createContentView(guideStep, index)
        val dimBackground = WalkthroughPositioning.resolveDimBackground(
            guideStep.presentation,
            guideStep.dimBackground,
        )
        val highlightTarget = WalkthroughPositioning.resolveHighlightTarget(
            guideStep.presentation,
            guideStep.highlightTarget,
        )

        val overlayScreen = overlay ?: OverlayScreen(activity).also { overlay = it }

        if (overlayScreen.parent == null) {
            overlayScreen.show(
                parentViewGroup = parent,
                viewToHighlight = guideStep.targetView,
                contentView = contentView,
                contentPosition = guideStep.placement,
                presentation = guideStep.presentation,
                dimBackground = dimBackground,
                highlightTarget = highlightTarget,
                onOutsideClick = ::onOutsideClick,
            )
        } else {
            overlayScreen.updateStep(
                viewToHighlight = guideStep.targetView,
                contentView = contentView,
                contentPosition = guideStep.placement,
                presentation = guideStep.presentation,
                dimBackground = dimBackground,
                highlightTarget = highlightTarget,
                onOutsideClick = ::onOutsideClick,
            )
        }

        onStepShown?.invoke(index)
    }

    private fun createContentView(guideStep: GuideStep, index: Int): android.view.View? {
        val content = guideContent ?: DefaultWalkthroughGuideContent()
        return content.createView(
            context = activity,
            guideStep = guideStep,
            stepIndex = index,
            totalSteps = guideSteps.size,
            actions = this,
        )
    }

    private fun activityOverlayParent(): ViewGroup {
        return activity.findViewById(android.R.id.content)
    }
}
