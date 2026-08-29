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
import `in`.mandeep_singh.walkmethrough.Placement
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

    private val bannerParams = LayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.WRAP_CONTENT,
    ).apply {
        gravity = Gravity.BOTTOM
    }

    private val fullScreenParams = LayoutParams(
        LayoutParams.MATCH_PARENT,
        LayoutParams.MATCH_PARENT,
    )

    init {
        isClickable = true
        isFocusable = true
    }

    fun show(
        parentViewGroup: ViewGroup,
        viewToHighlight: View,
        contentView: View?,
        contentPlacement: Placement?,
        presentation: GuidePresentation,
        dimBackground: Boolean,
        highlightTarget: Boolean,
        onOutsideClick: (() -> Unit)?,
    ) {
        applyStepConfig(viewToHighlight, contentView, contentPlacement, presentation, dimBackground, highlightTarget, onOutsideClick)
        if (parent == null) {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            parentViewGroup.addView(this)
        }
        invalidate()
    }

    fun updateStep(
        viewToHighlight: View,
        contentView: View?,
        contentPlacement: Placement?,
        presentation: GuidePresentation,
        dimBackground: Boolean,
        highlightTarget: Boolean,
        onOutsideClick: (() -> Unit)?,
    ) {
        applyStepConfig(viewToHighlight, contentView, contentPlacement, presentation, dimBackground, highlightTarget, onOutsideClick)
        invalidate()
    }

    fun dismiss() {
        (parent as? ViewGroup)?.removeView(this)
        removeAllViews()
        viewToHighlight = null
        onOutsideClick = null
        setOnClickListener(null)
    }

    private fun applyStepConfig(
        viewToHighlight: View,
        contentView: View?,
        contentPlacement: Placement?,
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
        positionContent(contentView, viewToHighlight, contentPlacement, presentation)
        configureTouchHandling(contentView)
    }

    private fun configureTouchHandling(contentView: View?) {
        if (contentView == null) {
            setOnClickListener { onOutsideClick?.invoke() }
        } else {
            setOnClickListener(null)
        }
    }

    private fun positionContent(
        contentView: View?,
        highlightedView: View,
        contentPlacement: Placement?,
        presentation: GuidePresentation,
    ) {
        if (contentView == null) {
            return
        }

        when (presentation) {
            GuidePresentation.TOOLTIP -> positionTooltip(contentView, highlightedView, contentPlacement)
            GuidePresentation.BANNER -> positionBanner(contentView)
            GuidePresentation.FULL_SCREEN -> positionFullScreen(contentView)
            GuidePresentation.CARD,
            GuidePresentation.SPOTLIGHT,
            -> positionCard(contentView, highlightedView, contentPlacement)
        }
    }

    private fun positionTooltip(
        contentView: View,
        highlightedView: View,
        contentPlacement: Placement?,
    ) {
        tooltipParams.topMargin = -3500
        tooltipParams.leftMargin = 0
        addView(contentView, tooltipParams)

        contentView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (contentView.height <= 0 || width <= 0 || height <= 0 || highlightedView.height <= 0) {
                    return
                }
                contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val overlayLocation = IntArray(2)
                getLocationOnScreen(overlayLocation)
                val viewLocation = IntArray(2)
                highlightedView.getLocationOnScreen(viewLocation)

                val targetLeft = viewLocation[0] - overlayLocation[0]
                val targetTop = viewLocation[1] - overlayLocation[1]

                val (left, top) = WalkthroughPositioning.getTooltipMargins(
                    overlayWidth = width,
                    overlayHeight = height,
                    targetLeft = targetLeft,
                    targetTop = targetTop,
                    targetWidth = highlightedView.width,
                    targetHeight = highlightedView.height,
                    tooltipWidth = contentView.width,
                    tooltipHeight = contentView.height,
                    placement = contentPlacement,
                    context = context,
                )

                tooltipParams.leftMargin = left
                tooltipParams.topMargin = top
                contentView.layoutParams = tooltipParams
                contentView.visibility = View.VISIBLE
                invalidate()
            }
        })
    }

    private fun positionBanner(contentView: View) {
        addView(contentView, bannerParams)
        contentView.visibility = View.VISIBLE
    }

    private fun positionFullScreen(contentView: View) {
        addView(contentView, fullScreenParams)
        contentView.visibility = View.VISIBLE
    }

    private fun positionCard(
        contentView: View,
        highlightedView: View,
        contentPlacement: Placement?,
    ) {
        dialogParams.topMargin = -3500
        addView(contentView, dialogParams)

        contentView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (contentView.height <= 0 || width <= 0 || height <= 0 || highlightedView.height <= 0) {
                    return
                }
                contentView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                val overlayLocation = IntArray(2)
                getLocationOnScreen(overlayLocation)
                val viewLocation = IntArray(2)
                highlightedView.getLocationOnScreen(viewLocation)

                val topOverlayHeight = viewLocation[1] - overlayLocation[1]
                val highlightHeight = highlightedView.height
                val bottomOverlayHeight = (height - topOverlayHeight - highlightHeight).coerceAtLeast(0)

                dialogParams.topMargin = WalkthroughPositioning.getDialogTopMargin(
                    topOverlayHeight = topOverlayHeight,
                    bottomOverlayHeight = bottomOverlayHeight,
                    dialogHeight = contentView.height,
                    highlightHeight = highlightHeight,
                    placement = contentPlacement,
                    overlayHeight = height,
                    context = context,
                )
                contentView.layoutParams = dialogParams
                contentView.visibility = View.VISIBLE
                invalidate()
            }
        })
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
        if (event?.action == MotionEvent.ACTION_UP && !isTouchOnChild(event)) {
            onOutsideClick?.invoke()
        }
        return true
    }

    private fun isTouchOnChild(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if (child.visibility != View.VISIBLE) continue
            if (x >= child.left && x <= child.right && y >= child.top && y <= child.bottom) {
                return true
            }
        }
        return false
    }
}
