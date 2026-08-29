package `in`.mandeep_singh.walkmethrough

import android.content.Context
import android.view.View

/**
 * Pluggable dialog UI for a walkthrough step. Supply a custom implementation to replace the default card.
 */
interface WalkthroughDialogContent {
  fun createView(
      context: Context,
      step: WalkthroughStep,
      stepIndex: Int,
      totalSteps: Int,
      actions: WalkthroughActions,
  ): View
}
