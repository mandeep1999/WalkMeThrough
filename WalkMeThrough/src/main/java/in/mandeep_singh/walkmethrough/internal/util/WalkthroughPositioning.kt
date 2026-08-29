package `in`.mandeep_singh.walkmethrough.internal.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import `in`.mandeep_singh.walkmethrough.GuidePresentation
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

    /**
     * Computes [leftMargin, topMargin] for a tooltip bubble anchored near a highlighted target.
     */
    fun getTooltipMargins(
        overlayWidth: Int,
        targetLeft: Int,
        targetTop: Int,
        targetWidth: Int,
        targetHeight: Int,
        tooltipWidth: Int,
        tooltipHeight: Int,
        position: Position?,
        context: Context,
    ): Pair<Int, Int> {
        val gap = dpToPx(context, 8f)
        val horizontalMargin = dpToPx(context, 12f)

        val resolvedPosition = position ?: if (targetTop > windowHeight(context) / 2) {
            Position.TOP
        } else {
            Position.BOTTOM
        }

        val targetCenterX = targetLeft + targetWidth / 2
        var left = targetCenterX - tooltipWidth / 2
        left = left.coerceIn(horizontalMargin, overlayWidth - tooltipWidth - horizontalMargin)

        val top = when (resolvedPosition) {
            Position.TOP -> targetTop - tooltipHeight - gap
            Position.BOTTOM -> targetTop + targetHeight + gap
            Position.CENTER -> targetTop + targetHeight / 2 - tooltipHeight / 2
        }

        return left to top
    }

    fun resolveDimBackground(presentation: GuidePresentation, dimBackground: Boolean?): Boolean {
        return dimBackground ?: (presentation == GuidePresentation.CARD)
    }

    fun resolveHighlightTarget(
        presentation: GuidePresentation,
        highlightTarget: Boolean?,
    ): Boolean {
        return highlightTarget ?: (presentation == GuidePresentation.CARD)
    }
}
