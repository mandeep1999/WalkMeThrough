package `in`.mandeep_singh.walkmethrough

/**
 * Vertical placement of the walkthrough dialog relative to the highlighted view.
 */
enum class Position {
    TOP,
    CENTER,
    BOTTOM;

    companion object {
        fun fromString(position: String?): Position {
            return when (position?.uppercase()) {
                "TOP" -> TOP
                "CENTER" -> CENTER
                "BOTTOM" -> BOTTOM
                else -> CENTER
            }
        }
    }
}
