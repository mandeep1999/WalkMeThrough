package `in`.mandeep_singh.walkmethrough.walk_me_through.components

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.GuidePresentation
import `in`.mandeep_singh.walkmethrough.Walkthrough
import `in`.mandeep_singh.walkmethrough.internal.dialog.LegacyCallbackDialogContent
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums.Position
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.models.PaddingModel

/**
 * Fluent builder for a single card-style guide step. For multiple steps, use [Walkthrough.from].
 */
class WalkthroughBuilder(private val context: Context) {

    private var viewToHighlight: View? = null
    private var parentViewGroup: ViewGroup? = null
    private var titleText: String? = null
    private var descriptionText: String? = null
    private var backButtonText: String? = null
    private var nextButtonText: String? = null
    private var dialogBackgroundColor: Int? = null
    private var titleTextColor: Int? = null
    private var descriptionTextColor: Int? = null
    private var backButtonTextColor: Int? = null
    private var nextButtonTextColor: Int? = null
    private var backButtonBackgroundColor: Int? = null
    private var nextButtonBackgroundColor: Int? = null
    private var dialogBackground: Drawable? = null
    private var nextButtonBackground: Drawable? = null
    private var backButtonBackground: Drawable? = null
    private var dialogPadding: PaddingModel? = null
    private var placement: Position? = null
    private var onOutsideClick: (() -> Unit)? = null
    private var onBackClick: (() -> Unit)? = null
    private var onNextClick: (() -> Unit)? = null
    private var onCloseClick: (() -> Unit)? = null

    fun setViewToHighlight(view: View) = apply { viewToHighlight = view }

    /** Optional overlay parent. When omitted, the activity content root is used. */
    fun setParentViewGroup(viewGroup: ViewGroup) = apply { parentViewGroup = viewGroup }

    fun setDialogPosition(position: Position) = apply { placement = position }

    fun setOnOutsideClickListener(listener: () -> Unit) = apply { onOutsideClick = listener }

    fun setTitleText(text: String?) = apply { titleText = text }

    fun setTitleTextColor(color: Int?) = apply { titleTextColor = color }

    fun setDescriptionText(text: String?) = apply { descriptionText = text }

    fun setDescriptionTextColor(color: Int?) = apply { descriptionTextColor = color }

    fun setDialogBackground(background: Drawable?) = apply { dialogBackground = background }

    fun setDialogBackgroundColor(color: Int?) = apply { dialogBackgroundColor = color }

    fun setNextButtonBackground(background: Drawable?) = apply { nextButtonBackground = background }

    fun setNextButtonText(text: String?) = apply { nextButtonText = text }

    fun setNextButtonBackgroundColor(color: Int?) = apply { nextButtonBackgroundColor = color }

    fun setNextButtonTextColor(color: Int?) = apply { nextButtonTextColor = color }

    fun setBackButtonBackground(background: Drawable?) = apply { backButtonBackground = background }

    fun setBackButtonText(text: String?) = apply { backButtonText = text }

    fun setBackButtonBackgroundColor(color: Int?) = apply { backButtonBackgroundColor = color }

    fun setBackButtonTextColor(color: Int?) = apply { backButtonTextColor = color }

    fun setDialogPadding(padding: PaddingModel?) = apply { dialogPadding = padding }

    fun setOnNextClick(onClick: (() -> Unit)?) = apply { onNextClick = onClick }

    fun setOnBackClick(onClick: (() -> Unit)?) = apply { onBackClick = onClick }

    fun setOnCloseClick(onClick: (() -> Unit)?) = apply { onCloseClick = onClick }

    fun build() {
        val activity = context as? Activity
            ?: throw IllegalArgumentException("WalkthroughBuilder requires an Activity context")

        val target = viewToHighlight
            ?: throw IllegalArgumentException("View to highlight must be provided")

        val guideStep = GuideStep(
            targetView = target,
            presentation = GuidePresentation.CARD,
            titleText = titleText,
            descriptionText = descriptionText,
            backButtonText = backButtonText,
            nextButtonText = nextButtonText,
            dialogBackgroundColor = dialogBackgroundColor,
            titleTextColor = titleTextColor,
            descriptionTextColor = descriptionTextColor,
            backButtonTextColor = backButtonTextColor,
            nextButtonTextColor = nextButtonTextColor,
            backButtonBackgroundColor = backButtonBackgroundColor,
            nextButtonBackgroundColor = nextButtonBackgroundColor,
            dialogBackground = dialogBackground,
            nextButtonBackground = nextButtonBackground,
            backButtonBackground = backButtonBackground,
            dialogPadding = dialogPadding,
            placement = placement,
        )

        val session = Walkthrough.from(activity)
            .add(guideStep)
            .onOutsideClick { onOutsideClick?.invoke() }
            .setDialogContent(
                LegacyCallbackDialogContent(
                    onBack = onBackClick,
                    onNext = onNextClick,
                    onClose = onCloseClick,
                )
            )

        parentViewGroup?.let { session.setOverlayParent(it) }
        session.show()
    }
}
