package `in`.mandeep_singh.walkmethrough.walk_me_through.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums.Position
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.models.DialogModel
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.models.PaddingModel

class WalkthroughBuilder(private val context: Context) {

    private var viewToHighlight: View? = null
    private var parentViewGroup: ViewGroup? = null
    private var dialogModel = DialogModel()
    private var dialogPosition: Position? = null
    private var onOutsideClick: (() -> Unit)? = null

    // Methods to configure the OverlayScreen
    fun setViewToHighlight(view: View) = apply { this.viewToHighlight = view }

    fun setParentViewGroup(viewGroup: ViewGroup) = apply { this.parentViewGroup = viewGroup }

    fun setDialogPosition(position: Position) = apply { this.dialogPosition = position }

    fun setOnOutsideClickListener(listener: () -> Unit) = apply { this.onOutsideClick = listener }

    // Methods to configure the `in`.mandeep_singh.walkmethrough.walk_me_through.components.DialogBox
    fun setTitleText(text: String?) = apply { dialogModel = dialogModel.copy(titleText = text) }

    fun setTitleTextColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(titleTextColor = color) }

    fun setDescriptionText(text: String?) =
        apply { dialogModel = dialogModel.copy(descriptionText = text) }

    fun setDescriptionTextColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(descriptionTextColor = color) }

    fun setDialogBackground(background: Drawable?) =
        apply { dialogModel = dialogModel.copy(dialogBackground = background) }

    fun setDialogBackgroundColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(dialogBackgroundColor = color) }

    fun setNextButtonBackground(background: Drawable?) =
        apply { dialogModel = dialogModel.copy(nextButtonBackground = background) }

    fun setNextButtonText(text: String?) =
        apply { dialogModel = dialogModel.copy(nextButtonText = text) }

    fun setNextButtonBackgroundColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(nextButtonBackgroundColor = color) }

    fun setNextButtonTextColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(nextButtonTextColor = color) }

    fun setBackButtonBackground(background: Drawable?) =
        apply { dialogModel = dialogModel.copy(backButtonBackground = background) }

    fun setBackButtonText(text: String?) =
        apply { dialogModel = dialogModel.copy(backButtonText = text) }

    fun setBackButtonBackgroundColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(backButtonBackgroundColor = color) }

    fun setBackButtonTextColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(backButtonTextColor = color) }

    fun setDialogPadding(padding: PaddingModel?) =
        apply { dialogModel = dialogModel.copy(dialogPadding = padding) }

    fun setOnNextClick(onClick: (() -> Unit)?) =
        apply { dialogModel = dialogModel.copy(onNextClick = onClick) }

    fun setOnBackClick(onClick: (() -> Unit)?) =
        apply { dialogModel = dialogModel.copy(onBackClick = onClick) }

    fun setOnCloseClick(onClick: (() -> Unit)?) =
        apply { dialogModel = dialogModel.copy(onCloseClick = onClick) }

    // Build method to create the combined component
    internal fun build(): OverlayScreen {
        // Ensure required parameters are set
        if (viewToHighlight == null) {
            throw IllegalArgumentException("View to highlight must be provided")
        }

        if (parentViewGroup == null) {
            throw IllegalArgumentException("Parent View Group must be provided")
        }

        val dialogBox = DialogBox.DialogBoxBuilder(context)
            .setTitleText(dialogModel.titleText)
            .setTitleTextColor(dialogModel.titleTextColor)
            .setDescriptionText(dialogModel.descriptionText)
            .setDescriptionTextColor(dialogModel.descriptionTextColor)
            .setDialogBackground(dialogModel.dialogBackground)
            .setDialogBackgroundColor(dialogModel.dialogBackgroundColor)
            .setNextButtonBackground(dialogModel.nextButtonBackground)
            .setNextButtonText(dialogModel.nextButtonText)
            .setNextButtonBackgroundColor(dialogModel.nextButtonBackgroundColor)
            .setNextButtonTextColor(dialogModel.nextButtonTextColor)
            .setBackButtonBackground(dialogModel.backButtonBackground)
            .setBackButtonText(dialogModel.backButtonText)
            .setBackButtonBackgroundColor(dialogModel.backButtonBackgroundColor)
            .setBackButtonTextColor(dialogModel.backButtonTextColor)
            .setDialogPadding(dialogModel.dialogPadding)
            .setOnNextClick(dialogModel.onNextClick)
            .setOnBackClick(dialogModel.onBackClick)
            .setOnCloseClick(dialogModel.onCloseClick)
            .build()

        // Create OverlayScreen using OverlayScreenBuilder

        return OverlayScreen.OverlayScreenBuilder(context)
            .setViewToHighlight(viewToHighlight!!)
            .setParentViewGroup(parentViewGroup!!)
            .setDialogBox(dialogBox)
            .setDialogPosition(dialogPosition)
            .setOnOutsideClickListener(onOutsideClick)
            .build()
    }
}
