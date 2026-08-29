package `in`.mandeep_singh.walkmethrough.internal.util

import `in`.mandeep_singh.walkmethrough.GuidePresentation
import `in`.mandeep_singh.walkmethrough.Placement
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class WalkthroughPositioningTest {

    private val context = RuntimeEnvironment.getApplication()

    @Test
    fun topPlacementWhenSpaceAboveIsLarger() {
        val topMargin = WalkthroughPositioning.getDialogTopMargin(
            topOverlayHeight = 400,
            bottomOverlayHeight = 100,
            dialogHeight = 80,
            highlightHeight = 50,
            placement = Placement.TOP,
            overlayHeight = 600,
            context = context,
        )
        assertEquals(WalkthroughPositioning.dpToPx(context, 16f), topMargin)
    }

    @Test
    fun dialogTopMarginClampedToOverlay() {
        val margin = WalkthroughPositioning.getDialogTopMargin(
            topOverlayHeight = 50,
            bottomOverlayHeight = 500,
            dialogHeight = 80,
            highlightHeight = 40,
            placement = Placement.BOTTOM,
            overlayHeight = 400,
            context = context,
        )
        assertEquals(320, margin)
    }

    @Test
    fun tooltipBelowTarget() {
        val (left, top) = WalkthroughPositioning.getTooltipMargins(
            overlayWidth = 400,
            overlayHeight = 800,
            targetLeft = 100,
            targetTop = 100,
            targetWidth = 48,
            targetHeight = 48,
            tooltipWidth = 120,
            tooltipHeight = 60,
            placement = Placement.BOTTOM,
            context = context,
        )
        assertEquals(100 + 48 / 2 - 120 / 2, left)
        assertEquals(100 + 48 + WalkthroughPositioning.dpToPx(context, 8f), top)
    }

    @Test
    fun tooltipVerticalPositionClampedToOverlay() {
        val (_, top) = WalkthroughPositioning.getTooltipMargins(
            overlayWidth = 400,
            overlayHeight = 200,
            targetLeft = 0,
            targetTop = 180,
            targetWidth = 48,
            targetHeight = 48,
            tooltipWidth = 120,
            tooltipHeight = 60,
            placement = Placement.BOTTOM,
            context = context,
        )
        assertEquals(140, top)
    }

    @Test
    fun tooltipWiderThanOverlayDoesNotCrash() {
        val horizontalMargin = WalkthroughPositioning.dpToPx(context, 12f)
        val (left, _) = WalkthroughPositioning.getTooltipMargins(
            overlayWidth = 400,
            overlayHeight = 800,
            targetLeft = 0,
            targetTop = 100,
            targetWidth = 400,
            targetHeight = 200,
            tooltipWidth = 400,
            tooltipHeight = 60,
            placement = Placement.BOTTOM,
            context = context,
        )
        assertEquals(0, left)

        val centered = WalkthroughPositioning.coerceHorizontalMargin(
            desiredLeft = 50,
            contentWidth = 500,
            containerWidth = 400,
            horizontalMargin = horizontalMargin,
        )
        assertEquals(0, centered)
    }

    @Test
    fun presentationDimDefaults() {
        assertFalse(WalkthroughPositioning.resolveDimBackground(GuidePresentation.TOOLTIP, null))
        assertTrue(WalkthroughPositioning.resolveDimBackground(GuidePresentation.SPOTLIGHT, null))
        assertTrue(WalkthroughPositioning.resolveDimBackground(GuidePresentation.BANNER, null))
        assertTrue(WalkthroughPositioning.resolveDimBackground(GuidePresentation.FULL_SCREEN, null))
    }

    @Test
    fun presentationHighlightDefaults() {
        assertFalse(WalkthroughPositioning.resolveHighlightTarget(GuidePresentation.TOOLTIP, null))
        assertFalse(WalkthroughPositioning.resolveHighlightTarget(GuidePresentation.FULL_SCREEN, null))
        assertTrue(WalkthroughPositioning.resolveHighlightTarget(GuidePresentation.SPOTLIGHT, null))
        assertTrue(WalkthroughPositioning.resolveHighlightTarget(GuidePresentation.BANNER, null))
    }
}
