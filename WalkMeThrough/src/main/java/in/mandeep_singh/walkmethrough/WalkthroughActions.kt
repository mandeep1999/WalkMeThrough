package `in`.mandeep_singh.walkmethrough

/**
 * Navigation callbacks wired from the dialog UI into the active walkthrough session.
 */
interface WalkthroughActions {
    fun onBack()
    fun onNext()
    fun onClose()
    fun onOutsideClick()
}
