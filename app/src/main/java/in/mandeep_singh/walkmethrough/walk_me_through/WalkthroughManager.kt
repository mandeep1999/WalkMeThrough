package `in`.mandeep_singh.walkmethrough.walk_me_through

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import `in`.mandeep_singh.walkmethrough.walk_me_through.components.WalkthroughBuilder


class WalkthroughManager(private val context: Context) {

    private var currentWalkthroughView: View? = null

    fun showWalkthrough(
        rootView: ViewGroup,
        viewToHighlight: View,
        configureBuilder: WalkthroughBuilder.() -> WalkthroughBuilder,
    ) {
        val globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Remove the listener to avoid multiple calls
                viewToHighlight.viewTreeObserver.removeOnGlobalLayoutListener(this)
                currentWalkthroughView?.let { rootView.removeView(it) }

                // Create and configure the builder
                val builder = WalkthroughBuilder(rootView.context)
                builder.setViewToHighlight(viewToHighlight)
                configureBuilder(builder)
                currentWalkthroughView = builder.build()

                // Set layout parameters to cover the entire screen
                currentWalkthroughView?.let { overlay ->
                    overlay.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    rootView.addView(overlay)
                }
            }
        }

        viewToHighlight.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    fun removeWalkthrough() {
        currentWalkthroughView?.let { view ->
            (view.parent as? ViewGroup)?.removeView(view)
            currentWalkthroughView = null
        }
    }
}
