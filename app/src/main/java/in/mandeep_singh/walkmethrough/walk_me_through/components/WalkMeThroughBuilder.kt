package `in`.mandeep_singh.walkmethrough.walk_me_through.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums.Position
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.models.DialogModel
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.models.PaddingModel


/**
 * A builder class for constructing a `Walkthrough` instance.
 *
 * This class provides a fluent API for configuring the various aspects of the walkthrough, including
 * the view to be highlighted, the parent view group for the overlay, dialog settings, and button actions.
 *
 * @param context The context used for building the dialog and overlay.
 *
 * Created by Mandeep Singh on 25th August 2024.
 */
class WalkthroughBuilder(private val context: Context) {

    private var viewToHighlight: View? = null
    private var parentViewGroup: ViewGroup? = null
    private var dialogModel = DialogModel()
    private var dialogPosition: Position? = null
    private var onOutsideClick: (() -> Unit)? = null

    /**
     * Specifies the view that should be highlighted by the overlay.
     *
     * The provided view will be the focal point of the overlay effect.
     *
     * @param view The `View` to be highlighted.
     * @return The current instance with the updated highlighted view.
     */
    fun setViewToHighlight(view: View) = apply { this.viewToHighlight = view }

    /**
     * Assigns the parent `ViewGroup` over which the translucent overlay will be displayed.
     *
     * This `ViewGroup` will serve as the base layer for the overlay.
     *
     * @param viewGroup The `ViewGroup` to be used as the parent container for the overlay.
     * @return The current instance with the updated parent `ViewGroup`.
     */
    fun setParentViewGroup(viewGroup: ViewGroup) = apply { this.parentViewGroup = viewGroup }

    /**
     * Sets the position of the dialog relative to the view being highlighted.
     *
     * By default, the dialog is centered between the highlighted view and the top or bottom edge,
     * depending on which has more available space. You can override this behavior by specifying
     * a custom position using the `Position` enum.
     *
     * For example, if there is more space above the highlighted view but you prefer the dialog
     * to be positioned closer to the view, you can set the position to `bottom`.
     *
     * @param position The desired position for the dialog, specified as a value from the `Position` enum.
     * @return The current instance with the updated dialog position.
     */
    fun setDialogPosition(position: Position) = apply { this.dialogPosition = position }

    /**
     * Sets a listener that triggers when the user clicks outside the dialog.
     *
     * @param listener A lambda function to be invoked on an outside click event.
     * @return The current instance with the updated outside click listener.
     */
    fun setOnOutsideClickListener(listener: () -> Unit) =
        apply { this.onOutsideClick = listener }

    /**
     * Sets the title text of the dialog.
     *
     * The provided text will be used as the title in the dialog's header. If `null` is passed,
     * the title will be cleared.
     *
     * @param text The title text to display in the dialog. Can be `null` to clear the title.
     * @return The current instance with the updated title text.
     */
    fun setTitleText(text: String?) = apply { dialogModel = dialogModel.copy(titleText = text) }

    /**
     * Sets the color of the dialog's title text.
     *
     * The provided color will be applied to the title text. If `null` is passed, the title text
     * color will be reset to its default value.
     *
     * @param color The color resource or ARGB value to apply to the title text. Can be `null` to reset to default.
     * @return The current instance with the updated title text color.
     */
    fun setTitleTextColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(titleTextColor = color) }

    /**
     * Sets the description text of the dialog.
     *
     * The provided text will be used for the dialog's description. If `null` is passed,
     * the description will be cleared.
     *
     * @param text The description text to display in the dialog. Can be `null` to clear the description.
     * @return The current instance with the updated description text.
     */
    fun setDescriptionText(text: String?) =
        apply { dialogModel = dialogModel.copy(descriptionText = text) }

    /**
     * Sets the color of the dialog's description text.
     *
     * The provided color will be applied to the description text. If `null` is passed, the description
     * text color will be reset to its default value.
     *
     * @param color The color resource or ARGB value to apply to the description text. Can be `null` to reset to default.
     * @return The current instance with the updated description text color.
     */
    fun setDescriptionTextColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(descriptionTextColor = color) }

    /**
     * Sets the background drawable for the dialog.
     *
     * The provided drawable will be used as the background for the dialog. If `null` is passed,
     * the dialog will use its default background.
     *
     * @param background The `Drawable` to set as the dialog's background. Can be `null` to use the default background.
     * @return The current instance with the updated dialog background.
     */
    fun setDialogBackground(background: Drawable?) =
        apply { dialogModel = dialogModel.copy(dialogBackground = background) }

    /**
     * Sets the background color of the dialog.
     *
     * The provided color will be applied to the dialog's background. If `null` is passed, the dialog
     * will use its default background color.
     *
     * @param color The color resource or ARGB value to apply to the dialog's background. Can be `null` to use the default color.
     * @return The current instance with the updated dialog background color.
     */
    fun setDialogBackgroundColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(dialogBackgroundColor = color) }

    /**
     * Sets the background drawable for the next button.
     *
     * The provided drawable will be used as the background for the next button. If `null` is passed,
     * the button will use its default background.
     *
     * @param background The `Drawable` to set as the next button's background. Can be `null` to use the default background.
     * @return The current instance with the updated next button background.
     */
    fun setNextButtonBackground(background: Drawable?) =
        apply { dialogModel = dialogModel.copy(nextButtonBackground = background) }

    /**
     * Sets the text of the next button.
     *
     * The provided text will be displayed on the next button. If `null` is passed, the button's text will be cleared.
     *
     * @param text The text to display on the next button. Can be `null` to clear the text.
     * @return The current instance with the updated next button text.
     */
    fun setNextButtonText(text: String?) =
        apply { dialogModel = dialogModel.copy(nextButtonText = text) }

    /**
     * Sets the background color of the next button.
     *
     * The provided color will be applied to the next button's background. If `null` is passed, the button
     * will use its default background color.
     *
     * @param color The color resource or ARGB value to apply to the next button's background. Can be `null` to use the default color.
     * @return The current instance with the updated next button background color.
     */
    fun setNextButtonBackgroundColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(nextButtonBackgroundColor = color) }

    /**
     * Sets the color of the next button's text.
     *
     * The provided color will be applied to the next button's text. If `null` is passed, the button's
     * text color will be reset to its default value.
     *
     * @param color The color resource or ARGB value to apply to the next button's text. Can be `null` to reset to default.
     * @return The current instance with the updated next button text color.
     */
    fun setNextButtonTextColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(nextButtonTextColor = color) }

    /**
     * Sets the background drawable for the back button.
     *
     * The provided drawable will be used as the background for the back button. If `null` is passed,
     * the button will use its default background.
     *
     * @param background The `Drawable` to set as the back button's background. Can be `null` to use the default background.
     * @return The current instance with the updated back button background.
     */
    fun setBackButtonBackground(background: Drawable?) =
        apply { dialogModel = dialogModel.copy(backButtonBackground = background) }

    /**
     * Sets the text of the back button.
     *
     * The provided text will be displayed on the back button. If `null` is passed, the button's text will be cleared.
     *
     * @param text The text to display on the back button. Can be `null` to clear the text.
     * @return The current instance with the updated back button text.
     */
    fun setBackButtonText(text: String?) =
        apply { dialogModel = dialogModel.copy(backButtonText = text) }

    /**
     * Sets the background color of the back button.
     *
     * The provided color will be applied to the back button's background. If `null` is passed, the button
     * will use its default background color.
     *
     * @param color The color resource or ARGB value to apply to the back button's background. Can be `null` to use the default color.
     * @return The current instance with the updated back button background color.
     */
    fun setBackButtonBackgroundColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(backButtonBackgroundColor = color) }

    /**
     * Sets the color of the back button's text.
     *
     * The provided color will be applied to the back button's text. If `null` is passed, the button's
     * text color will be reset to its default value.
     *
     * @param color The color resource or ARGB value to apply to the back button's text. Can be `null` to reset to default.
     * @return The current instance with the updated back button text color.
     */
    fun setBackButtonTextColor(color: Int?) =
        apply { dialogModel = dialogModel.copy(backButtonTextColor = color) }

    /**
     * Sets the padding for the dialog.
     *
     * The provided `PaddingModel` will be used to set the padding around the dialog's content. If `null` is passed,
     * default padding values will be used.
     *
     * @param padding The `PaddingModel` containing the padding values. Can be `null` to use default padding.
     * @return The current instance with the updated dialog padding.
     */
    fun setDialogPadding(padding: PaddingModel?) =
        apply { dialogModel = dialogModel.copy(dialogPadding = padding) }

    /**
     * Sets the action to be triggered when the next button is clicked.
     *
     * The provided lambda function will be invoked when the next button is clicked. If `null` is passed,
     * no action will be performed on a next button click.
     *
     * @param onClick A lambda function to be called when the next button is clicked. Can be `null` to disable click action.
     * @return The current instance with the updated next button click action.
     */
    fun setOnNextClick(onClick: (() -> Unit)?) =
        apply { dialogModel = dialogModel.copy(onNextClick = onClick) }

    /**
     * Sets the action to be triggered when the back button is clicked.
     *
     * The provided lambda function will be invoked when the back button is clicked. If `null` is passed,
     * no action will be performed on a back button click.
     *
     * @param onClick A lambda function to be called when the back button is clicked. Can be `null` to disable click action.
     * @return The current instance with the updated back button click action.
     */
    fun setOnBackClick(onClick: (() -> Unit)?) =
        apply { dialogModel = dialogModel.copy(onBackClick = onClick) }

    /**
     * Sets the action to be triggered when the close button is clicked.
     *
     * The provided lambda function will be invoked when the close button is clicked. If `null` is passed,
     * no action will be performed on a close button click.
     *
     * @param onClick A lambda function to be called when the close button is clicked. Can be `null` to disable click action.
     * @return The current instance with the updated close button click action.
     */
    fun setOnCloseClick(onClick: (() -> Unit)?) =
        apply { dialogModel = dialogModel.copy(onCloseClick = onClick) }


    /**
     * Constructs and returns an `OverlayScreen` instance using the specified parameters and settings.
     *
     * This method ensures that all required parameters (`viewToHighlight` and `parentViewGroup`) are provided.
     * It then builds a `DialogBox` with the configured properties and uses it to create an `OverlayScreen`.
     *
     * @throws IllegalArgumentException if `viewToHighlight` or `parentViewGroup` are not set.
     * @return The constructed `OverlayScreen` instance.
     */
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
