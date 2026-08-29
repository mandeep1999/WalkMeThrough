package `in`.mandeep_singh.walkmethrough

/**
 * Placement of guide content relative to the highlighted target.
 */
enum class Placement {
    TOP,
    CENTER,
    BOTTOM;

    companion object {
        fun fromString(value: String?): Placement {
            return when (value?.uppercase()) {
                "TOP" -> TOP
                "CENTER" -> CENTER
                "BOTTOM" -> BOTTOM
                else -> CENTER
            }
        }
    }
}
