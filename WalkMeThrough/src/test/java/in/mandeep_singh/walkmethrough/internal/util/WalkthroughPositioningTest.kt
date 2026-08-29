package `in`.mandeep_singh.walkmethrough.internal.util

import `in`.mandeep_singh.walkmethrough.Position
import org.junit.Assert.assertEquals
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
    fun centerPositionWhenSpaceBelowIsLarger() {
        val topMargin = WalkthroughPositioning.getDialogTopMargin(
            topOverlayHeight = 100,
            bottomOverlayHeight = 400,
            dialogHeight = 80,
            highlightHeight = 50,
            dialogPosition = Position.CENTER,
            context = context,
        )
        val expected = 100 + 50 + (400 / 2) - (80 / 2)
        assertEquals(expected, topMargin)
    }
}
