package `in`.mandeep_singh.walkmethrough.internal.coordinator

import android.app.Activity
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import `in`.mandeep_singh.walkmethrough.WalkthroughActions
import `in`.mandeep_singh.walkmethrough.WalkthroughDialogContent
import `in`.mandeep_singh.walkmethrough.WalkthroughStep
import `in`.mandeep_singh.walkmethrough.internal.dialog.DefaultWalkthroughDialogContent
import `in`.mandeep_singh.walkmethrough.internal.overlay.OverlayScreen

class WalkthroughCoordinator internal constructor(
    private val activity: Activity,
    private val steps: List<WalkthroughStep>,
    private val overlayParent: ViewGroup?,
    private val dialogContent: WalkthroughDialogContent?,
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
        showStep(0)
    }

    fun dismiss() {
        if (!started) return
        started = false
        if (activity is LifecycleOwner) {
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
            showStep(currentStepIndex - 1)
        }
    }

    override fun onNext() {
        if (currentStepIndex < steps.lastIndex) {
            showStep(currentStepIndex + 1)
        } else {
            onComplete?.invoke()
            dismiss()
        }
    }

    override fun onClose() {
        dismiss()
    }

    override fun onOutsideClick() {
        onOutsideClick?.invoke()
    }

    private fun showStep(index: Int) {
        currentStepIndex = index
        val step = steps[index]
        val parent = overlayParent ?: activityOverlayParent()
        val dialogView = createDialogView(step, index)

        val overlayScreen = overlay ?: OverlayScreen(activity).also { overlay = it }

        if (overlayScreen.parent == null) {
            overlayScreen.show(
                parentViewGroup = parent,
                viewToHighlight = step.targetView,
                dialogView = dialogView,
                dialogPosition = step.dialogPosition,
                onOutsideClick = ::onOutsideClick,
            )
        } else {
            overlayScreen.updateStep(
                viewToHighlight = step.targetView,
                dialogView = dialogView,
                dialogPosition = step.dialogPosition,
                onOutsideClick = ::onOutsideClick,
            )
        }

        onStepShown?.invoke(index)
    }

    private fun createDialogView(step: WalkthroughStep, index: Int): android.view.View {
        val content = dialogContent ?: DefaultWalkthroughDialogContent()
        return content.createView(
            context = activity,
            step = step,
            stepIndex = index,
            totalSteps = steps.size,
            actions = this,
        )
    }

    private fun activityOverlayParent(): ViewGroup {
        return activity.findViewById(android.R.id.content)
    }
}
