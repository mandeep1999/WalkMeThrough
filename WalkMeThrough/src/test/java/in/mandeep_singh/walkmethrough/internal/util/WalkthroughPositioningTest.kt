package `in`.mandeep_singh.walkmethrough.internal.util

import `in`.mandeep_singh.walkmethrough.GuidePresentation
import `in`.mandeep_singh.walkmethrough.Position
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
    fun topPositionWhenSpaceAboveIsLarger() {
        val topMargin = WalkthroughPositioning.getDialogTopMargin(
            topOverlayHeight = 400,
            bottomOverlayHeight = 100,
            dialogHeight = 80,
            highlightHeight = 50,
            dialogPosition = Position.TOP,
            context = context,
        )
        assertEquals(WalkthroughPositioning.dpToPx(context, 16f), topMargin)
    }

    @Test
    fun tooltipBelowTarget() {
        val (left, top) = WalkthroughPositioning.getTooltipMargins(
            overlayWidth = 400,
            targetLeft = 100,
            targetTop = 100,
            targetWidth = 48,
            targetHeight = 48,
            tooltipWidth = 120,
            tooltipHeight = 60,
            position = Position.BOTTOM,
            context = context,
        )
        assertEquals(100 + 48 / 2 - 120 / 2, left)
        assertEquals(100 + 48 + WalkthroughPositioning.dpToPx(context, 8f), top)
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
