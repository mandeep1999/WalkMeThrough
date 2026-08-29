package `in`.mandeep_singh.walkmethrough.internal.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import `in`.mandeep_singh.walkmethrough.GuidePresentation
import `in`.mandeep_singh.walkmethrough.Placement

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
        placement: Placement?,
        context: Context,
    ): Int {
        val fiftyDp = dpToPx(context, 50f)
        val sixteenDp = dpToPx(context, 16f)

        return if (topOverlayHeight > bottomOverlayHeight) {
            when (placement) {
                Placement.TOP -> sixteenDp
                Placement.CENTER -> topOverlayHeight / 2 - dialogHeight / 2
                Placement.BOTTOM -> topOverlayHeight - dialogHeight - highlightHeight - fiftyDp
                else -> topOverlayHeight / 2 - dialogHeight / 2
            }
        } else {
            when (placement) {
                Placement.TOP -> topOverlayHeight + highlightHeight + sixteenDp
                Placement.CENTER -> topOverlayHeight + highlightHeight + (bottomOverlayHeight / 2) - dialogHeight / 2
                Placement.BOTTOM -> topOverlayHeight + highlightHeight - dialogHeight + bottomOverlayHeight - sixteenDp
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
        placement: Placement?,
        context: Context,
    ): Pair<Int, Int> {
        val gap = dpToPx(context, 8f)
        val horizontalMargin = dpToPx(context, 12f)

        val resolvedPlacement = placement ?: if (targetTop > windowHeight(context) / 2) {
            Placement.TOP
        } else {
            Placement.BOTTOM
        }

        val targetCenterX = targetLeft + targetWidth / 2
        var left = targetCenterX - tooltipWidth / 2
        left = coerceHorizontalMargin(
            desiredLeft = left,
            contentWidth = tooltipWidth,
            containerWidth = overlayWidth,
            horizontalMargin = horizontalMargin,
        )

        val top = when (resolvedPlacement) {
            Placement.TOP -> targetTop - tooltipHeight - gap
            Placement.BOTTOM -> targetTop + targetHeight + gap
            Placement.CENTER -> targetTop + targetHeight / 2 - tooltipHeight / 2
        }

        return left to top
    }

    /**
     * Clamps horizontal offset when content is wider than the padded container width.
     */
    internal fun coerceHorizontalMargin(
        desiredLeft: Int,
        contentWidth: Int,
        containerWidth: Int,
        horizontalMargin: Int,
    ): Int {
        if (containerWidth <= 0) return 0

        val minLeft = horizontalMargin
        val maxLeft = containerWidth - contentWidth - horizontalMargin

        return when {
            contentWidth >= containerWidth -> maxOf(0, (containerWidth - contentWidth) / 2)
            maxLeft < minLeft -> desiredLeft.coerceIn(0, containerWidth - contentWidth)
            else -> desiredLeft.coerceIn(minLeft, maxLeft)
        }
    }

    fun resolveDimBackground(presentation: GuidePresentation, dimBackground: Boolean?): Boolean {
        return dimBackground ?: when (presentation) {
            GuidePresentation.TOOLTIP -> false
            GuidePresentation.CARD,
            GuidePresentation.SPOTLIGHT,
            GuidePresentation.BANNER,
            GuidePresentation.FULL_SCREEN,
            -> true
        }
    }

    fun resolveHighlightTarget(
        presentation: GuidePresentation,
        highlightTarget: Boolean?,
    ): Boolean {
        return highlightTarget ?: when (presentation) {
            GuidePresentation.TOOLTIP,
            GuidePresentation.FULL_SCREEN,
            -> false
            GuidePresentation.CARD,
            GuidePresentation.SPOTLIGHT,
            GuidePresentation.BANNER,
            -> true
        }
    }
}
