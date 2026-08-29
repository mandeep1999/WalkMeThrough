package `in`.mandeep_singh.walkmethrough.internal.dialog

import android.content.Context
import android.view.View
import `in`.mandeep_singh.walkmethrough.WalkthroughActions
import `in`.mandeep_singh.walkmethrough.WalkthroughDialogContent
import `in`.mandeep_singh.walkmethrough.WalkthroughStep

internal class LegacyCallbackDialogContent(
    private val onBack: (() -> Unit)?,
    private val onNext: (() -> Unit)?,
    private val onClose: (() -> Unit)?,
) : WalkthroughDialogContent {

    override fun createView(
        context: Context,
        step: WalkthroughStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View {
        val wrappedActions = object : WalkthroughActions {
            override fun onBack() {
                onBack?.invoke()
                actions.onBack()
            }

            override fun onNext() {
                onNext?.invoke()
                actions.onNext()
            }

            override fun onClose() {
                onClose?.invoke()
                actions.onClose()
            }

            override fun onOutsideClick() {
                actions.onOutsideClick()
            }
        }
        return DefaultDialogView.create(context, step, stepIndex, totalSteps, wrappedActions)
    }
}
