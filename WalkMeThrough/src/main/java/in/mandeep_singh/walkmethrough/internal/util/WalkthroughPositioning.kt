package `in`.mandeep_singh.walkmethrough.internal.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import `in`.mandeep_singh.walkmethrough.Position

object WalkthroughPositioning {

    fun dpToPx(context: Context, value: Float?): Int {
        return if (value != null) {
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                context.resources.displayMetrics,
            ).toInt()
        } else {
            0
        }
    }

    fun getDialogTopMargin(
        topOverlayHeight: Int,
        bottomOverlayHeight: Int,
        dialogHeight: Int,
        highlightHeight: Int,
        dialogPosition: Position?,
        context: Context,
    ): Int {
        val fiftyDp = dpToPx(context, 50f)
        val sixteenDp = dpToPx(context, 16f)

        return if (topOverlayHeight > bottomOverlayHeight) {
            when (dialogPosition) {
                Position.TOP -> sixteenDp
                Position.CENTER -> topOverlayHeight / 2 - dialogHeight / 2
                Position.BOTTOM -> topOverlayHeight - dialogHeight - highlightHeight - fiftyDp
                else -> topOverlayHeight / 2 - dialogHeight / 2
            }
        } else {
            when (dialogPosition) {
                Position.TOP -> topOverlayHeight + highlightHeight + sixteenDp
                Position.CENTER -> topOverlayHeight + highlightHeight + (bottomOverlayHeight / 2) - dialogHeight / 2
                Position.BOTTOM -> topOverlayHeight + highlightHeight - dialogHeight + bottomOverlayHeight - sixteenDp
                else -> topOverlayHeight + highlightHeight + (bottomOverlayHeight / 2) - dialogHeight / 2
            }
        }
    }

    fun statusBarHeight(context: Context): Int {
        val rectangle = Rect()
        val window = (context as? Activity)?.window
        window?.decorView?.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }

    fun windowHeight(context: Context): Int {
        val activity = context as? Activity
        return activity?.resources?.displayMetrics?.heightPixels ?: 0
    }
}
