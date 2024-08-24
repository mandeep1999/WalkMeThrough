package `in`.mandeep_singh.walkmethrough.walk_me_through.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import `in`.mandeep_singh.walkmethrough.R
import `in`.mandeep_singh.walkmethrough.databinding.LayoutDialogBoxBinding
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.models.DialogModel
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.models.PaddingModel
import `in`.mandeep_singh.walkmethrough.walk_me_through.utils.Utility
import `in`.mandeep_singh.walkmethrough.walk_me_through.utils.Utility.setTextHexColor
import `in`.mandeep_singh.walkmethrough.walk_me_through.utils.Utility.toDp

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

    private fun setUpTitleTextView(titleText: String?, titleTextColor: Int?) {
        binding.titleTextView.isVisible = !titleText.isNullOrBlank()
        titleText?.let { binding.titleTextView.text = it }
        titleTextColor?.let { binding.titleTextView.setTextColor(it) }
    }

    private fun setUpDescriptionTextView(descriptionText: String?, descriptionTextColor: Int?) {
        binding.descriptionTextView.isVisible = !descriptionText.isNullOrBlank()
        descriptionText?.let { binding.descriptionTextView.text = it }
        descriptionTextColor?.let { binding.descriptionTextView.setTextColor(it) }
    }

    private fun setUpBackground(dialogBackground: Drawable?, dialogBackgroundColor: Int?) {
        dialogBackground?.let { binding.root.background = it }
        dialogBackgroundColor?.let { binding.root.setBackgroundColor(it) }
    }

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

    private fun setUpClickListeners(
        onBackClick: (() -> Unit)?,
        onNextClick: (() -> Unit)?,
        onCloseClick: (() -> Unit)?
    ) {
        binding.backButton.setOnClickListener {
            onBackClick?.let {
                binding.backButton.visibility = View.VISIBLE
                onBackClick.invoke()
            }
        }
        binding.nextButton.setOnClickListener {
            onNextClick?.let {
                binding.nextButton.visibility = View.VISIBLE
                onNextClick.invoke()
            }
        }
        binding.closeIcon.setOnClickListener { onCloseClick?.invoke() }

        if (onBackClick != null && onNextClick != null) {
            binding.space.visibility = View.VISIBLE
        }
    }

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

    // The builder class is placed at the bottom
    class DialogBoxBuilder(private val context: Context) {
        private var dialogModel = DialogModel()

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

        fun build(): DialogBox {
            val dialogBox = DialogBox(context)
            dialogBox.buildDialog(dialogModel)
            return dialogBox
        }
    }
}
