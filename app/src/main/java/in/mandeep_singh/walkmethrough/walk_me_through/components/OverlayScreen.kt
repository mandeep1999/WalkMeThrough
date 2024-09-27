package `in`.mandeep_singh.walkmethrough.walk_me_through.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums.Position
import `in`.mandeep_singh.walkmethrough.walk_me_through.utils.Utility.getPositionOfDialog
import `in`.mandeep_singh.walkmethrough.walk_me_through.utils.Utility.getWindowHeight
import `in`.mandeep_singh.walkmethrough.walk_me_through.utils.Utility.statusBarHeight
import `in`.mandeep_singh.walkmethrough.walk_me_through.utils.Utility.toDp

/**
 * Custom view that displays an overlay with a highlighted area and a dialog box.
 *
 * The overlay obscures the entire screen except for the area around the view to be highlighted.
 * A dialog box is positioned relative to the highlighted view, with an option to specify its position.
 * The overlay also responds to clicks outside the dialog box.
 *
 * @param context The context in which the view is running.
 * @param attrs Optional attribute set for custom XML attributes.
 * @param defStyleAttr Optional default style attributes.
 */
internal class OverlayScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private var viewToHighlight: View? = null
    private var onOutsideClick: (() -> Unit)? = null

    private val overlayPaint = Paint().apply {
        color = 0x80000000.toInt() // Semi-transparent black
        style = Paint.Style.FILL
    }
    private val clearPaint = Paint().apply {
        color = Color.TRANSPARENT
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    private val twelveDP = 12f.toDp(context)
    private val sixDP = 6f.toDp(context)

    private val dialogParams =
        LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            setMargins(twelveDP, sixDP, twelveDP, sixDP)
        }

    /**
     * Builds and displays the overlay with the specified parameters.
     *
     * This method removes any existing highlights, calculates the dimensions for the overlay, and positions
     * the dialog box relative to the highlighted view. It also handles layout changes to ensure proper positioning
     * of the dialog box.
     *
     * @param viewToHighlight The view that will be highlighted by the overlay.
     * @param parentViewGroup The parent view group that will contain the overlay.
     * @param dialogBox The dialog box to be displayed.
     * @param dialogPosition Optional position for the dialog box relative to the highlighted view.
     * @param onOutsideClick Optional callback to be invoked when clicking outside the dialog box.
     */
    internal fun build(
        viewToHighlight: View,
        parentViewGroup: ViewGroup,
        dialogBox: DialogBox,
        dialogPosition: Position? = null,
        onOutsideClick: (() -> Unit)? = null
    ) {
        // Remove any existing highlight
        removeAllViews()

        // Add the view to highlight
        this.viewToHighlight = viewToHighlight
        this.onOutsideClick = onOutsideClick

        // Get the coordinates and dimensions of the view relative to its parent
        val parentLocation = IntArray(2)
        getLocationOnScreen(parentLocation)
        val viewLocation = IntArray(2)
        viewToHighlight.getLocationOnScreen(viewLocation)

        val topOverlayHeight = viewLocation[1] - parentLocation[1]
        val highlightHeight = viewToHighlight.height
        val bottomOverlayHeight = getWindowHeight(context) - (topOverlayHeight + highlightHeight)

        dialogParams.topMargin = -3500

        addView(dialogBox, dialogParams)

        dialogBox.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val infoDialogHeight = dialogBox.height

                val centerY = getPositionOfDialog(
                    topOverlayHeight,
                    bottomOverlayHeight,
                    infoDialogHeight,
                    highlightHeight,
                    dialogPosition, context
                )

                dialogParams.topMargin = centerY
                dialogBox.layoutParams = dialogParams
                dialogBox.visibility = View.VISIBLE
                invalidate()
            }
        })

        viewToHighlight.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewToHighlight.viewTreeObserver.removeOnGlobalLayoutListener(this)
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
                )
                parentViewGroup.addView(this@OverlayScreen)
                invalidate()
            }
        })
    }

    /**
     * Draws the overlay with a hole cut out around the highlighted view.
     *
     * This method creates a bitmap for the overlay and draws a transparent area over the highlighted view,
     * creating a "hole" effect. The overlay is then drawn onto the main canvas.
     *
     * @param canvas The canvas on which to draw the overlay.
     */
    override fun dispatchDraw(canvas: Canvas) {
        // Create a bitmap and canvas to draw the holeRect on
        val overlayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val overlayCanvas = Canvas(overlayBitmap)

        // Draw the overlay
        overlayCanvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), overlayPaint)

        // Punch a hole in the overlay
        viewToHighlight?.let {
            val location = IntArray(2)
            it.getLocationOnScreen(location)
            val x = location[0]
            val y = location[1] - statusBarHeight(context)
            val width = it.width
            val height = it.height
            overlayCanvas.drawRect(
                x.toFloat() - twelveDP,
                y.toFloat() - twelveDP,
                (x + width).toFloat() + twelveDP,
                (y + height).toFloat() + twelveDP,
                clearPaint,
            )
        }

        // Draw the combined overlay onto the main canvas
        canvas.drawBitmap(overlayBitmap, 0f, 0f, null)

        // Call super to draw child views
        super.dispatchDraw(canvas)
    }

    /**
     * Handles touch events on the overlay.
     *
     * This method invokes the `onOutsideClick` callback if it is set, and returns true to indicate that the
     * touch event has been handled.
     *
     * @param event The motion event that triggered this method.
     * @return True to indicate that the touch event has been handled.
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        onOutsideClick?.invoke()
        return true
    }

    // Builder class for constructing OverlayScreen instances
    internal class OverlayScreenBuilder(private val context: Context) {
        private var viewToHighlight: View? = null
        private var parentViewGroup: ViewGroup? = null
        private var dialogBox: DialogBox? = null
        private var dialogPosition: Position? = null
        private var onOutsideClick: (() -> Unit)? = null

        /**
         * Sets the view to be highlighted by the overlay.
         *
         * @param view The view to highlight.
         * @return The builder instance for chaining.
         */
        fun setViewToHighlight(view: View) = apply {
            this.viewToHighlight = view
        }

        /**
         * Sets the parent view group for the overlay.
         *
         * @param viewGroup The view group that will contain the overlay.
         * @return The builder instance for chaining.
         */
        fun setParentViewGroup(viewGroup: ViewGroup) = apply {
            this.parentViewGroup = viewGroup
        }

        /**
         * Sets the dialog box to be displayed on the overlay.
         *
         * @param dialogBox The dialog box to show.
         * @return The builder instance for chaining.
         */
        fun setDialogBox(dialogBox: DialogBox) = apply {
            this.dialogBox = dialogBox
        }

        /**
         * Sets the position of the dialog box relative to the highlighted view.
         *
         * @param position The position of the dialog box.
         * @return The builder instance for chaining.
         */
        fun setDialogPosition(position: Position?) = apply {
            this.dialogPosition = position
        }

        /**
         * Sets a callback to be invoked when clicking outside the dialog box.
         *
         * @param listener The callback to invoke on outside click.
         * @return The builder instance for chaining.
         */
        fun setOnOutsideClickListener(listener: (() -> Unit)?) = apply {
            this.onOutsideClick = listener
        }

        /**
         * Builds and returns an `OverlayScreen` instance with the configured parameters.
         *
         * @return The constructed `OverlayScreen` instance.
         * @throws IllegalArgumentException if any required parameters are missing.
         */
        fun build(): OverlayScreen {
            // Ensure required parameters are set
            if (viewToHighlight == null) {
                throw IllegalArgumentException("View to highlight must be provided")
            }
            if (parentViewGroup == null) {
                throw IllegalArgumentException("Parent View Group must be provided")
            }
            if (dialogBox == null) {
                throw IllegalArgumentException("DialogBox must be provided")
            }

            // Create OverlayScreen instance and build it with the specified parameters
            val overlayScreen = OverlayScreen(context)
            overlayScreen.build(
                viewToHighlight = viewToHighlight!!,
                parentViewGroup = parentViewGroup!!,
                dialogBox = dialogBox!!,
                dialogPosition = dialogPosition,
                onOutsideClick = onOutsideClick
            )
            return overlayScreen
        }
    }
}
