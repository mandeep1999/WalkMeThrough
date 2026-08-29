package `in`.mandeep_singh.walkmethrough

/**
 * How a walkthrough step is presented on screen.
 */
enum class StepStyle {
    /** Full instructional card with optional navigation buttons. */
    DIALOG,

    /** Compact tooltip bubble anchored near the highlighted target. */
    TOOLTIP,
}
