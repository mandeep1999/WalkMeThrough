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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        onOutsideClick?.invoke()
        return true
    }

    // Builder class moved to the bottom
    class OverlayScreenBuilder(private val context: Context) {
        private var viewToHighlight: View? = null
        private var parentViewGroup: ViewGroup? = null
        private var dialogBox: DialogBox? = null
        private var dialogPosition: Position? = null
        private var onOutsideClick: (() -> Unit)? = null

        fun setViewToHighlight(view: View) = apply {
            this.viewToHighlight = view
        }

        fun setParentViewGroup(viewGroup: ViewGroup) = apply {
            this.parentViewGroup = viewGroup
        }

        fun setDialogBox(dialogBox: DialogBox) = apply {
            this.dialogBox = dialogBox
        }

        fun setDialogPosition(position: Position?) = apply {
            this.dialogPosition = position
        }

        fun setOnOutsideClickListener(listener: (() -> Unit)?) = apply {
            this.onOutsideClick = listener
        }

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
