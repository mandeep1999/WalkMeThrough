package `in`.mandeep_singh.walkmethrough

/**
 * How a guide step is presented on screen.
 */
enum class GuidePresentation {
    /** Full instructional card with optional navigation buttons. */
    CARD,

    /** Compact tooltip bubble anchored near the highlighted target. */
    TOOLTIP,
}
