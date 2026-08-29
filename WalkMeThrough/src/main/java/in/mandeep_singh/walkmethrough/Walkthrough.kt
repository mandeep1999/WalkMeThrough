package `in`.mandeep_singh.walkmethrough

import android.app.Activity

/**
 * Entry point for creating walkthrough sessions.
 */
object Walkthrough {
    fun from(activity: Activity): WalkthroughSession = WalkthroughSession(activity)
}
