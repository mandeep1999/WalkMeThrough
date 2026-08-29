package `in`.mandeep_singh.walkmethrough.internal.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import `in`.mandeep_singh.walkmethrough.Padding
import `in`.mandeep_singh.walkmethrough.WalkthroughActions
import `in`.mandeep_singh.walkmethrough.WalkthroughDialogContent
import `in`.mandeep_singh.walkmethrough.WalkthroughStep
import `in`.mandeep_singh.walkmethrough.library.databinding.LayoutDialogBoxBinding
import `in`.mandeep_singh.walkmethrough.internal.util.WalkthroughPositioning

internal object DefaultDialogView {

    fun create(
        context: Context,
        step: WalkthroughStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View {
        val binding = LayoutDialogBoxBinding.inflate(LayoutInflater.from(context))
        bind(binding, step, stepIndex, totalSteps, actions)
        return binding.root
    }

    private fun bind(
        binding: LayoutDialogBoxBinding,
        step: WalkthroughStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ) {
        val context = binding.root.context
        val showBack = stepIndex > 0

        setUpTitleTextView(binding, step.titleText, step.titleTextColor)
        setUpDescriptionTextView(binding, step.descriptionText, step.descriptionTextColor)
        setUpBackground(binding, step.dialogBackground, step.dialogBackgroundColor)
        setUpNextButton(
            binding,
            step.nextButtonBackground,
            step.nextButtonText,
            step.nextButtonBackgroundColor,
            step.nextButtonTextColor,
        )
        setUpBackButton(
            binding,
            step.backButtonBackground,
            step.backButtonText,
            step.backButtonBackgroundColor,
            step.backButtonTextColor,
            visible = showBack,
        )
        setUpClickListeners(binding, actions, showBack)
        setUpPadding(binding, step.dialogPadding, context)
    }

    private fun setUpTitleTextView(
        binding: LayoutDialogBoxBinding,
        titleText: String?,
        titleTextColor: Int?,
    ) {
        binding.titleTextView.isVisible = !titleText.isNullOrBlank()
        titleText?.let { binding.titleTextView.text = it }
        titleTextColor?.let { binding.titleTextView.setTextColor(it) }
    }

    private fun setUpDescriptionTextView(
        binding: LayoutDialogBoxBinding,
        descriptionText: String?,
        descriptionTextColor: Int?,
    ) {
        binding.descriptionTextView.isVisible = !descriptionText.isNullOrBlank()
        descriptionText?.let { binding.descriptionTextView.text = it }
        descriptionTextColor?.let { binding.descriptionTextView.setTextColor(it) }
    }

    private fun setUpBackground(
        binding: LayoutDialogBoxBinding,
        dialogBackground: Drawable?,
        dialogBackgroundColor: Int?,
    ) {
        dialogBackground?.let { binding.root.background = it }
        dialogBackgroundColor?.let { binding.root.setBackgroundColor(it) }
    }

    private fun setUpNextButton(
        binding: LayoutDialogBoxBinding,
        background: Drawable?,
        text: String?,
        backgroundColor: Int?,
        textColor: Int?,
    ) {
        background?.let { binding.nextButton.background = it }
        backgroundColor?.let { binding.nextButton.setBackgroundColor(it) }
        binding.nextButton.isVisible = !text.isNullOrBlank()
        binding.nextButton.text = text
        textColor?.let { binding.nextButton.setTextColor(it) }
    }

    private fun setUpBackButton(
        binding: LayoutDialogBoxBinding,
        background: Drawable?,
        text: String?,
        backgroundColor: Int?,
        textColor: Int?,
        visible: Boolean,
    ) {
        background?.let { binding.backButton.background = it }
        backgroundColor?.let { binding.backButton.setBackgroundColor(it) }
        binding.backButton.isVisible = visible && !text.isNullOrBlank()
        binding.backButton.text = text
        textColor?.let { binding.backButton.setTextColor(it) }
    }

    private fun setUpClickListeners(
        binding: LayoutDialogBoxBinding,
        actions: WalkthroughActions,
        showBack: Boolean,
    ) {
        binding.backButton.setOnClickListener {
            if (showBack) actions.onBack()
        }
        binding.nextButton.setOnClickListener { actions.onNext() }
        binding.closeIcon.setOnClickListener { actions.onClose() }
        binding.space.isVisible = showBack && binding.nextButton.isVisible
    }

    private fun setUpPadding(
        binding: LayoutDialogBoxBinding,
        dialogPadding: Padding?,
        context: Context,
    ) {
        dialogPadding?.let {
            binding.root.setPadding(
                WalkthroughPositioning.dpToPx(context, it.left?.toFloat()),
                WalkthroughPositioning.dpToPx(context, it.top?.toFloat()),
                WalkthroughPositioning.dpToPx(context, it.right?.toFloat()),
                WalkthroughPositioning.dpToPx(context, it.bottom?.toFloat()),
            )
        }
    }
}

internal class DefaultWalkthroughDialogContent : WalkthroughDialogContent {
    override fun createView(
        context: Context,
        step: WalkthroughStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View = DefaultDialogView.create(context, step, stepIndex, totalSteps, actions)
}
