package `in`.mandeep_singh.walkmethrough

import android.app.Activity

/**
 * Entry point for creating walkthroughs.
 */
object Walkthrough {

    /**
     * Starts configuring a walkthrough for [activity].
     */
    @JvmStatic
    fun with(activity: Activity): WalkthroughBuilder = WalkthroughBuilder(activity)
}
