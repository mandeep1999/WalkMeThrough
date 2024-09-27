package `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums

/**
 * Enum class representing the possible positions for the dialog box in the walkthrough.
 */
enum class Position {
    TOP,
    CENTER,
    BOTTOM;

    companion object {
        /**
         * Utility method to get a Position enum value from a string.
         * The string comparison is case-insensitive.
         *
         * @param position The string representation of the position.
         * @return The corresponding Position enum value. Defaults to CENTER if the input is invalid.
         */
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
