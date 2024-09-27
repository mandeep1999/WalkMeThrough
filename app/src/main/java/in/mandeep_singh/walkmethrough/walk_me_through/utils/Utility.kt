package `in`.mandeep_singh.walkmethrough.walk_me_through.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.TextView
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums.Position

object Utility {

    /**
     * Validates if the given string is a valid hex color code.
     *
     * @param hexColor The hex color code as a string.
     * @return True if the string matches the hex color pattern; false otherwise.
     */
    private fun isValidHexColor(hexColor: String?): Boolean {
        // Return false if the input is null
        if (hexColor == null) {
            return false
        }

        // Define the regular expression pattern for hex color codes
        val hexColorPattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$".toRegex()

        // Return true if the hexColor matches the pattern, false otherwise
        return hexColorPattern.matches(hexColor)
    }

    /**
     * Sets the text color of a TextView using a hex color string.
     *
     * @param color The hex color code as a string.
     */
    internal fun TextView.setTextHexColor(color: String?) {
        val isValidColor = isValidHexColor(color)
        if (isValidColor) {
            setTextColor(Color.parseColor(color))
        }
    }

    /**
     * Converts a Float value from pixels to density-independent pixels (dp).
     *
     * @param context The context used for converting dp to pixels.
     * @return The equivalent dp value as an integer. Returns 0 if the input Float is null.
     */
    internal fun Float?.toDp(context: Context): Int {
        return if (this != null) {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                this,
                context.resources.displayMetrics
            ).toInt()
        } else {
            0 // Default value when input is null
        }
    }

    /**
     * Calculates the vertical position for displaying a dialog based on the overlay and dialog dimensions.
     *
     * @param topOverlayHeight The height of the overlay area above the highlighted view.
     * @param bottomOverlayHeight The height of the overlay area below the highlighted view.
     * @param infoDialogHeight The height of the dialog.
     * @param highlightHeight The height of the highlighted view.
     * @param dialogPosition The preferred position for the dialog (TOP, CENTER, BOTTOM).
     * @param context The context used for converting dp values.
     * @return The calculated vertical position for the dialog.
     */
    internal fun getPositionOfDialog(
        topOverlayHeight: Int,
        bottomOverlayHeight: Int,
        infoDialogHeight: Int,
        highlightHeight: Int,
        dialogPosition: Position?,
        context: Context
    ): Int {
        val fiftyDP = 50f.toDp(context)
        val sixteenDP = 16f.toDp(context)

        return if (topOverlayHeight > bottomOverlayHeight) {
            when (dialogPosition) {
                Position.TOP -> sixteenDP
                Position.CENTER -> topOverlayHeight / 2 - infoDialogHeight / 2
                Position.BOTTOM -> topOverlayHeight - infoDialogHeight - highlightHeight - fiftyDP
                else -> topOverlayHeight / 2 - infoDialogHeight / 2
            }
        } else {
            when (dialogPosition) {
                Position.TOP -> topOverlayHeight + highlightHeight + sixteenDP
                Position.CENTER -> topOverlayHeight + highlightHeight + (bottomOverlayHeight / 2) - infoDialogHeight / 2
                Position.BOTTOM -> topOverlayHeight + highlightHeight - infoDialogHeight + bottomOverlayHeight - sixteenDP
                else -> topOverlayHeight + highlightHeight + (bottomOverlayHeight / 2) - infoDialogHeight / 2
            }
        }
    }

    /**
     * Retrieves the height of the status bar.
     *
     * @param context The context used to get the window.
     * @return The height of the status bar in pixels.
     */
    internal fun statusBarHeight(context: Context): Int {
        val rectangle = Rect()
        val window = (context as? Activity)?.window
        window?.decorView?.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }

    /**
     * Retrieves the height of the window.
     *
     * @param context The context used to get the window manager.
     * @return The height of the window in pixels.
     */
    internal fun getWindowHeight(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context as? Activity)?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}