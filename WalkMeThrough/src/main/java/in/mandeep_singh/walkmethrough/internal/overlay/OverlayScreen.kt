package `in`.mandeep_singh.walkmethrough.internal.overlay

import android.content.Context
import android.graphics.Canvas
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
import `in`.mandeep_singh.walkmethrough.GuidePresentation
import `in`.mandeep_singh.walkmethrough.Position
import `in`.mandeep_singh.walkmethrough.internal.util.WalkthroughPositioning

internal class OverlayScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private var viewToHighlight: View? = null
    private var onOutsideClick: (() -> Unit)? = null
    private var dimBackground = true
    private var highlightTarget = true
    private var presentation = GuidePresentation.CARD

    private val overlayPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0x80000000.toInt()
        style = Paint.Style.FILL
    }

    private val clearPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    private val highlightPaddingPx = WalkthroughPositioning.dpToPx(context, 12f)
    private val horizontalMarginPx = WalkthroughPositioning.dpToPx(context, 12f)
    private val verticalMarginPx = WalkthroughPositioning.dpToPx(context, 6f)
    private val highlightStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0xFFFFFFFF.toInt()
        style = Paint.Style.STROKE
        strokeWidth = WalkthroughPositioning.dpToPx(context, 2f).toFloat()
    }
    private val highlightCornerRadiusPx = WalkthroughPositioning.dpToPx(context, 8f).toFloat()

    private val dialogParams = LayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT,
    ).apply {
        gravity = Gravity.CENTER_HORIZONTAL
        setMargins(horizontalMarginPx, verticalMarginPx, horizontalMarginPx, verticalMarginPx)
    }

    private val tooltipParams = LayoutParams(
        LayoutParams.WRAP_CONTENT,
        LayoutParams.WRAP_CONTENT,
    )

    fun show(
        parentViewGroup: ViewGroup,
        viewToHighlight: View,
        contentView: View,
        contentPosition: Position?,
        presentation: GuidePresentation,
        dimBackground: Boolean,
        highlightTarget: Boolean,
        onOutsideClick: (() -> Unit)?,
    ) {
        applyStepConfig(viewToHighlight, contentView, contentPosition, presentation, dimBackground, highlightTarget, onOutsideClick)
        if (parent == null) {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            parentViewGroup.addView(this)
        }
        invalidate()
    }

    fun updateStep(
        viewToHighlight: View,
        contentView: View,
        contentPosition: Position?,
        presentation: GuidePresentation,
        dimBackground: Boolean,
        highlightTarget: Boolean,
        onOutsideClick: (() -> Unit)?,
    ) {
        applyStepConfig(viewToHighlight, contentView, contentPosition, presentation, dimBackground, highlightTarget, onOutsideClick)
        invalidate()
    }

    fun dismiss() {
        (parent as? ViewGroup)?.removeView(this)
        removeAllViews()
        viewToHighlight = null
        onOutsideClick = null
    }

    private fun applyStepConfig(
        viewToHighlight: View,
        contentView: View,
        contentPosition: Position?,
        presentation: GuidePresentation,
        dimBackground: Boolean,
        highlightTarget: Boolean,
        onOutsideClick: (() -> Unit)?,
    ) {
        this.viewToHighlight = viewToHighlight
        this.onOutsideClick = onOutsideClick
        this.dimBackground = dimBackground
        this.highlightTarget = highlightTarget
        this.presentation = presentation
        removeAllViews()
        positionContent(contentView, viewToHighlight, contentPosition, presentation)
    }

    private fun positionContent(
        contentView: View,
        highlightedView: View,
        contentPosition: Position?,
        presentation: GuidePresentation,
    ) {
        if (presentation == GuidePresentation.TOOLTIP) {
            tooltipParams.topMargin = -3500
            tooltipParams.leftMargin = 0
            addView(contentView, tooltipParams)

            contentView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val overlayLocation = IntArray(2)
                    getLocationOnScreen(overlayLocation)
                    val viewLocation = IntArray(2)
                    highlightedView.getLocationOnScreen(viewLocation)

                    val targetLeft = viewLocation[0] - overlayLocation[0]
                    val targetTop = viewLocation[1] - overlayLocation[1]

                    val (left, top) = WalkthroughPositioning.getTooltipMargins(
                        overlayWidth = width,
                        targetLeft = targetLeft,
                        targetTop = targetTop,
                        targetWidth = highlightedView.width,
                        targetHeight = highlightedView.height,
                        tooltipWidth = contentView.width,
                        tooltipHeight = contentView.height,
                        position = contentPosition,
                        context = context,
                    )

                    tooltipParams.leftMargin = left
                    tooltipParams.topMargin = top
                    contentView.layoutParams = tooltipParams
                    contentView.visibility = View.VISIBLE
                    invalidate()
                }
            })
        } else {
            dialogParams.topMargin = -3500
            addView(contentView, dialogParams)

            contentView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val overlayLocation = IntArray(2)
                    getLocationOnScreen(overlayLocation)
                    val viewLocation = IntArray(2)
                    highlightedView.getLocationOnScreen(viewLocation)

                    val topOverlayHeight = viewLocation[1] - overlayLocation[1]
                    val highlightHeight = highlightedView.height
                    val bottomOverlayHeight = WalkthroughPositioning.windowHeight(context) -
                        (topOverlayHeight + highlightHeight)

                    dialogParams.topMargin = WalkthroughPositioning.getDialogTopMargin(
                        topOverlayHeight = topOverlayHeight,
                        bottomOverlayHeight = bottomOverlayHeight,
                        dialogHeight = contentView.height,
                        highlightHeight = highlightHeight,
                        dialogPosition = contentPosition,
                        context = context,
                    )
                    contentView.layoutParams = dialogParams
                    contentView.visibility = View.VISIBLE
                    invalidate()
                }
            })
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        when {
            dimBackground -> {
                val layer = canvas.saveLayer(
                    0f,
                    0f,
                    width.toFloat(),
                    height.toFloat(),
                    null,
                )
                canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), overlayPaint)
                if (highlightTarget) {
                    drawHighlightCutout(canvas)
                }
                canvas.restoreToCount(layer)
            }
            highlightTarget -> {
                viewToHighlight?.let { highlightedView ->
                    val rect = highlightRectOnOverlay(highlightedView)
                    canvas.drawRoundRect(
                        rect,
                        highlightCornerRadiusPx,
                        highlightCornerRadiusPx,
                        highlightStrokePaint,
                    )
                }
            }
        }
        super.dispatchDraw(canvas)
    }

    private fun drawHighlightCutout(canvas: Canvas) {
        viewToHighlight?.let { highlightedView ->
            val rect = highlightRectOnOverlay(highlightedView)
            canvas.drawRoundRect(
                rect,
                highlightCornerRadiusPx,
                highlightCornerRadiusPx,
                clearPaint,
            )
        }
    }

    private fun highlightRectOnOverlay(view: View): android.graphics.RectF {
        val overlayLocation = IntArray(2)
        getLocationOnScreen(overlayLocation)
        val viewLocation = IntArray(2)
        view.getLocationOnScreen(viewLocation)

        val left = viewLocation[0] - overlayLocation[0] - highlightPaddingPx
        val top = viewLocation[1] - overlayLocation[1] - highlightPaddingPx
        val right = left + view.width + highlightPaddingPx * 2
        val bottom = top + view.height + highlightPaddingPx * 2

        return android.graphics.RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            onOutsideClick?.invoke()
        }
        return true
    }
}
