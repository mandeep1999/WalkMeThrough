package `in`.mandeep_singh.walkmethrough

/**
 * How a guide step is presented on screen.
 */
enum class GuidePresentation {
    /** Full instructional card with optional navigation buttons. */
    CARD,

    /** Compact tooltip bubble anchored near the highlighted target. */
    TOOLTIP,

    /** Dimmed overlay with a spotlight cutout only — no instructional UI. */
    SPOTLIGHT,

    /** Bottom banner anchored to the screen edge. */
    BANNER,

    /** Full-screen centered content over a dimmed background. */
    FULL_SCREEN,
}
