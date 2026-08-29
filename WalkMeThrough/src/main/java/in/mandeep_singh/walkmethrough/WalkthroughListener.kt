package `in`.mandeep_singh.walkmethrough

/**
 * Callbacks for walkthrough lifecycle events.
 *
 * Register with [WalkthroughBuilder.setListener] or the `doOn*` convenience methods.
 */
interface WalkthroughListener {
    fun onStepShown(index: Int) {}
    fun onComplete() {}
    fun onDismiss() {}
    fun onOutsideClick() {}
}
