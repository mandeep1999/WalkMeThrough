package `in`.mandeep_singh.walkmethrough.walk_me_through.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums.Position

object Utility {

    fun isValidHexColor(hexColor: String?): Boolean {
        // Return false if the input is null
        if (hexColor == null) {
            return false
        }

        // Define the regular expression pattern for hex color codes
        val hexColorPattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$".toRegex()

        // Return true if the hexColor matches the pattern, false otherwise
        return hexColorPattern.matches(hexColor)
    }

    fun TextView.setTextHexColor(color: String?) {
        val isValidColor = isValidHexColor(color)
        if (isValidColor) {
            setTextColor(Color.parseColor(color))
        }
    }

    fun Float?.toDp(context: Context): Int {
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

    fun getPositionOfDialog(
        topOverlayHeight: Int,
        bottomOverlayHeight: Int,
        infoDialogHeight: Int,
        highlightHeight: Int,
        dialogPosition: Position?,
        context: Context
    ): Int {
        val fiftyDP = 50f.toDp(context)
        val sixTeenDP = 16f.toDp(context)

        return if (topOverlayHeight > bottomOverlayHeight) {

            when (dialogPosition) {
                Position.TOP -> {
                    sixTeenDP
                }

                Position.CENTER -> {
                    topOverlayHeight / 2 - infoDialogHeight / 2
                }

                Position.BOTTOM -> {
                    topOverlayHeight - infoDialogHeight - highlightHeight - fiftyDP
                }

                else -> {
                    topOverlayHeight / 2 - infoDialogHeight / 2

                }
            }
        } else {

            when (dialogPosition) {
                Position.TOP -> {
                    topOverlayHeight + highlightHeight + sixTeenDP
                }

                Position.CENTER -> {
                    topOverlayHeight + highlightHeight + (bottomOverlayHeight / 2) - infoDialogHeight / 2
                }

                Position.BOTTOM -> {
                    topOverlayHeight + highlightHeight - infoDialogHeight + bottomOverlayHeight - sixTeenDP
                }

                else -> {
                    topOverlayHeight + highlightHeight + (bottomOverlayHeight / 2) - infoDialogHeight / 2
                }
            }
        }
    }

    fun statusBarHeight(context: Context): Int {
        val rectangle = Rect()
        val window = (context as? Activity)?.window
        window?.decorView?.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }

    fun getWindowHeight(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        (context as? Activity)?.windowManager?.getDefaultDisplay()?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}