package `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums

enum class Position {
    TOP,
    CENTER,
    BOTTOM;

    companion object {
        // Utility method to get a Position by name (case-insensitive)
        fun fromString(position: String?): Position {
            return when (position?.uppercase()) {
                "TOP" -> TOP
                "CENTER" -> CENTER
                "BOTTOM" -> BOTTOM
                else -> CENTER // Default position
            }
        }
    }
}
