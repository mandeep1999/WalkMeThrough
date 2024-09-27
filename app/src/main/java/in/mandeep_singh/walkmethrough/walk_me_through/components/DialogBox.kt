package `in`.mandeep_singh.walkmethrough.walk_me_through.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import `in`.mandeep_singh.walkmethrough.R
import `in`.mandeep_singh.walkmethrough.databinding.LayoutDialogBoxBinding
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.models.DialogModel
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.models.PaddingModel
import `in`.mandeep_singh.walkmethrough.walk_me_through.utils.Utility.toDp

/**
 * Custom view that represents a dialog box with various customizable attributes.
 *
 * The dialog box can display a title, description, and buttons for navigation. It is designed
 * to be used within an overlay screen to guide users through a walkthrough.
 *
 * @param context The context in which the view is running.
 * @param attributeSet Optional attribute set for custom XML attributes.
 * @param attrStyleInt Optional default style attributes.
 */
internal class DialogBox @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    attrStyleInt: Int = 0
) : BaseConstraintLayout(context, attributeSet, attrStyleInt) {

    private val binding = DataBindingUtil.inflate<LayoutDialogBoxBinding>(
        LayoutInflater.from(context),
        R.layout.layout_dialog_box,
        this,
        true
    )

    /**
     * Configures the dialog box based on the provided [dialogModel].
     *
     * This method sets up the title, description, background, buttons, and padding for the dialog box
     * using the attributes specified in the [dialogModel].
     *
     * @param dialogModel The model containing attributes to configure the dialog box.
     */
    private fun buildDialog(dialogModel: DialogModel) {
        with(dialogModel) {
            setUpTitleTextView(titleText, titleTextColor)
            setUpDescriptionTextView(descriptionText, descriptionTextColor)
            setUpBackground(dialogBackground, dialogBackgroundColor)
            setUpNextButton(
                nextButtonBackground,
                nextButtonText,
                nextButtonBackgroundColor,
                nextButtonTextColor
            )
            setUpBackButton(
                backButtonBackground,
                backButtonText,
                backButtonBackgroundColor,
                backButtonTextColor
            )
            setUpClickListeners(onBackClick, onNextClick, onCloseClick)
            setUpPadding(dialogPadding)
        }
    }

    /**
     * Configures the title text view with the specified text and color.
     *
     * @param titleText The text to display in the title view.
     * @param titleTextColor The color to set for the title text.
     */
    private fun setUpTitleTextView(titleText: String?, titleTextColor: Int?) {
        binding.titleTextView.isVisible = !titleText.isNullOrBlank()
        titleText?.let { binding.titleTextView.text = it }
        titleTextColor?.let { binding.titleTextView.setTextColor(it) }
    }

    /**
     * Configures the description text view with the specified text and color.
     *
     * @param descriptionText The text to display in the description view.
     * @param descriptionTextColor The color to set for the description text.
     */
    private fun setUpDescriptionTextView(descriptionText: String?, descriptionTextColor: Int?) {
        binding.descriptionTextView.isVisible = !descriptionText.isNullOrBlank()
        descriptionText?.let { binding.descriptionTextView.text = it }
        descriptionTextColor?.let { binding.descriptionTextView.setTextColor(it) }
    }

    /**
     * Configures the background of the dialog box.
     *
     * @param dialogBackground The drawable to use as the background.
     * @param dialogBackgroundColor The color to set for the background.
     */
    private fun setUpBackground(dialogBackground: Drawable?, dialogBackgroundColor: Int?) {
        dialogBackground?.let { binding.root.background = it }
        dialogBackgroundColor?.let { binding.root.setBackgroundColor(it) }
    }

    /**
     * Configures the next button with the specified attributes.
     *
     * @param background The drawable to use as the background for the next button.
     * @param text The text to display on the next button.
     * @param backgroundColor The color to set for the next button background.
     * @param textColor The color to set for the next button text.
     */
    private fun setUpNextButton(
        background: Drawable?,
        text: String?,
        backgroundColor: Int?,
        textColor: Int?
    ) {
        background?.let { binding.nextButton.background = it }
        backgroundColor?.let { binding.nextButton.setBackgroundColor(it) }
        binding.nextButton.isVisible = !text.isNullOrBlank()
        binding.nextButton.text = text
        textColor?.let { binding.nextButton.setTextColor(it) }
    }

    /**
     * Configures the back button with the specified attributes.
     *
     * @param background The drawable to use as the background for the back button.
     * @param text The text to display on the back button.
     * @param backgroundColor The color to set for the back button background.
     * @param textColor The color to set for the back button text.
     */
    private fun setUpBackButton(
        background: Drawable?,
        text: String?,
        backgroundColor: Int?,
        textColor: Int?
    ) {
        background?.let { binding.backButton.background = it }
        backgroundColor?.let {
            binding.backButton.setBackgroundColor(
                it
            )
        }
        binding.backButton.isVisible = !text.isNullOrBlank()
        binding.backButton.text = text
        textColor?.let { binding.backButton.setTextColor(it) }
    }

    /**
     * Removes this view from its parent, if the parent is an instance of `OverlayScreen`.
     */
    private fun removeViewFromParent() {
        (parent as? OverlayScreen)?.let { overlayScreen ->
            (overlayScreen.parent as? ViewGroup)?.removeView(overlayScreen)
        }
    }

    /**
     * Sets up click listeners for the back, next, and close buttons.
     *
     * @param onBackClick Callback to be invoked when the back button is clicked.
     * @param onNextClick Callback to be invoked when the next button is clicked.
     * @param onCloseClick Callback to be invoked when the close button is clicked.
     */
    private fun setUpClickListeners(
        onBackClick: (() -> Unit)?,
        onNextClick: (() -> Unit)?,
        onCloseClick: (() -> Unit)?
    ) {
        binding.backButton.setOnClickListener {
            onBackClick?.let {
                removeViewFromParent()
                onBackClick.invoke()
            }
        }
        binding.nextButton.setOnClickListener {
            onNextClick?.let {
                removeViewFromParent()
                onNextClick.invoke()
            }
        }
        binding.closeIcon.setOnClickListener {
            removeViewFromParent()
            onCloseClick?.invoke()
        }

        if (onBackClick != null && onNextClick != null) {
            binding.space.visibility = View.VISIBLE
        }
    }

    /**
     * Sets padding for the dialog box using the specified [dialogPadding] model.
     *
     * @param dialogPadding The model containing padding values for the dialog box.
     */
    private fun setUpPadding(dialogPadding: PaddingModel?) {
        dialogPadding?.let {
            binding.root.setPadding(
                it.left.toDp(context),
                it.top.toDp(context),
                it.right.toDp(context),
                it.bottom.toDp(context)
            )
        }
    }

    /**
     * Builder class for constructing `DialogBox` instances with customizable attributes.
     *
     * This builder provides a fluent API to set various properties of the dialog box.
     *
     * @param context The context in which the dialog box will be used.
     */
    internal class DialogBoxBuilder(private val context: Context) {
        private var dialogModel = DialogModel()

        /**
         * Sets the text for the title of the dialog box.
         *
         * @param text The title text.
         * @return The builder instance for chaining.
         */
        fun setTitleText(text: String?) = apply { dialogModel = dialogModel.copy(titleText = text) }

        /**
         * Sets the color for the title text.
         *
         * @param color The color for the title text.
         * @return The builder instance for chaining.
         */
        fun setTitleTextColor(color: Int?) =
            apply { dialogModel = dialogModel.copy(titleTextColor = color) }

        /**
         * Sets the text for the description of the dialog box.
         *
         * @param text The description text.
         * @return The builder instance for chaining.
         */
        fun setDescriptionText(text: String?) =
            apply { dialogModel = dialogModel.copy(descriptionText = text) }

        /**
         * Sets the color for the description text.
         *
         * @param color The color for the description text.
         * @return The builder instance for chaining.
         */
        fun setDescriptionTextColor(color: Int?) =
            apply { dialogModel = dialogModel.copy(descriptionTextColor = color) }

        /**
         * Sets the drawable background for the dialog box.
         *
         * @param background The drawable to use as the background.
         * @return The builder instance for chaining.
         */
        fun setDialogBackground(background: Drawable?) =
            apply { dialogModel = dialogModel.copy(dialogBackground = background) }

        /**
         * Sets the color for the dialog box background.
         *
         * @param color The color to set for the background.
         * @return The builder instance for chaining.
         */
        fun setDialogBackgroundColor(color: Int?) =
            apply { dialogModel = dialogModel.copy(dialogBackgroundColor = color) }

        /**
         * Sets the drawable background for the next button.
         *
         * @param background The drawable to use as the background for the next button.
         * @return The builder instance for chaining.
         */
        fun setNextButtonBackground(background: Drawable?) =
            apply { dialogModel = dialogModel.copy(nextButtonBackground = background) }

        /**
         * Sets the text for the next button.
         *
         * @param text The text to display on the next button.
         * @return The builder instance for chaining.
         */
        fun setNextButtonText(text: String?) =
            apply { dialogModel = dialogModel.copy(nextButtonText = text) }

        /**
         * Sets the background color for the next button.
         *
         * @param color The color to set for the next button background.
         * @return The builder instance for chaining.
         */
        fun setNextButtonBackgroundColor(color: Int?) =
            apply { dialogModel = dialogModel.copy(nextButtonBackgroundColor = color) }

        /**
         * Sets the text color for the next button.
         *
         * @param color The color to set for the next button text.
         * @return The builder instance for chaining.
         */
        fun setNextButtonTextColor(color: Int?) =
            apply { dialogModel = dialogModel.copy(nextButtonTextColor = color) }

        /**
         * Sets the drawable background for the back button.
         *
         * @param background The drawable to use as the background for the back button.
         * @return The builder instance for chaining.
         */
        fun setBackButtonBackground(background: Drawable?) =
            apply { dialogModel = dialogModel.copy(backButtonBackground = background) }

        /**
         * Sets the text for the back button.
         *
         * @param text The text to display on the back button.
         * @return The builder instance for chaining.
         */
        fun setBackButtonText(text: String?) =
            apply { dialogModel = dialogModel.copy(backButtonText = text) }

        /**
         * Sets the background color for the back button.
         *
         * @param color The color to set for the back button background.
         * @return The builder instance for chaining.
         */
        fun setBackButtonBackgroundColor(color: Int?) =
            apply { dialogModel = dialogModel.copy(backButtonBackgroundColor = color) }

        /**
         * Sets the text color for the back button.
         *
         * @param color The color to set for the back button text.
         * @return The builder instance for chaining.
         */
        fun setBackButtonTextColor(color: Int?) =
            apply { dialogModel = dialogModel.copy(backButtonTextColor = color) }

        /**
         * Sets padding for the dialog box.
         *
         * @param padding The model containing padding values for the dialog box.
         * @return The builder instance for chaining.
         */
        fun setDialogPadding(padding: PaddingModel?) =
            apply { dialogModel = dialogModel.copy(dialogPadding = padding) }

        /**
         * Sets the callback for the next button click event.
         *
         * @param onClick The callback to invoke when the next button is clicked.
         * @return The builder instance for chaining.
         */
        fun setOnNextClick(onClick: (() -> Unit)?) =
            apply { dialogModel = dialogModel.copy(onNextClick = onClick) }

        /**
         * Sets the callback for the back button click event.
         *
         * @param onClick The callback to invoke when the back button is clicked.
         * @return The builder instance for chaining.
         */
        fun setOnBackClick(onClick: (() -> Unit)?) =
            apply { dialogModel = dialogModel.copy(onBackClick = onClick) }

        /**
         * Sets the callback for the close button click event.
         *
         * @param onClick The callback to invoke when the close button is clicked.
         * @return The builder instance for chaining.
         */
        fun setOnCloseClick(onClick: (() -> Unit)?) =
            apply { dialogModel = dialogModel.copy(onCloseClick = onClick) }

        /**
         * Builds and returns a `DialogBox` instance configured with the specified attributes.
         *
         * @return A fully constructed `DialogBox` instance.
         */
        fun build(): DialogBox {
            val dialogBox = DialogBox(context)
            dialogBox.buildDialog(dialogModel)
            return dialogBox
        }
    }
}
