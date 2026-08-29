package `in`.mandeep_singh.walkmethrough

/**
 * Navigation actions passed to custom [GuideContent] implementations.
 */
interface GuideActions {
    fun onBack()
    fun onNext()
    fun onClose()
    fun onOutsideClick()
}
