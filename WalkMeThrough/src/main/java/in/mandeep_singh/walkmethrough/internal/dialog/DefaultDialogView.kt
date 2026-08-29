package `in`.mandeep_singh.walkmethrough.internal.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import `in`.mandeep_singh.walkmethrough.GuideActions
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.Padding
import `in`.mandeep_singh.walkmethrough.library.databinding.LayoutDialogBoxBinding
import `in`.mandeep_singh.walkmethrough.internal.util.WalkthroughPositioning

internal object DefaultDialogView {

    fun create(
        context: Context,
        step: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: GuideActions,
    ): View {
        val binding = LayoutDialogBoxBinding.inflate(LayoutInflater.from(context))
        bind(binding, step, stepIndex, actions)
        return binding.root
    }

    private fun bind(
        binding: LayoutDialogBoxBinding,
        step: GuideStep,
        stepIndex: Int,
        actions: GuideActions,
    ) {
        val context = binding.root.context
        val showBack = stepIndex > 0

        setUpTitleTextView(binding, step.title, step.titleColor)
        setUpDescriptionTextView(binding, step.description, step.descriptionColor)
        setUpBackground(binding, step.background, step.backgroundColor)
        setUpNextButton(
            binding,
            step.nextBackground,
            step.nextText,
            step.nextBackgroundColor,
            step.nextTextColor,
        )
        setUpBackButton(
            binding,
            step.backBackground,
            step.backText,
            step.backBackgroundColor,
            step.backTextColor,
            visible = showBack,
        )
        setUpClickListeners(binding, actions, showBack)
        setUpPadding(binding, step.padding, context)
    }

    private fun setUpTitleTextView(
        binding: LayoutDialogBoxBinding,
        title: String?,
        titleColor: Int?,
    ) {
        binding.titleTextView.isVisible = !title.isNullOrBlank()
        title?.let { binding.titleTextView.text = it }
        titleColor?.let { binding.titleTextView.setTextColor(it) }
    }

    private fun setUpDescriptionTextView(
        binding: LayoutDialogBoxBinding,
        description: String?,
        descriptionColor: Int?,
    ) {
        binding.descriptionTextView.isVisible = !description.isNullOrBlank()
        description?.let { binding.descriptionTextView.text = it }
        descriptionColor?.let { binding.descriptionTextView.setTextColor(it) }
    }

    private fun setUpBackground(
        binding: LayoutDialogBoxBinding,
        background: Drawable?,
        backgroundColor: Int?,
    ) {
        background?.let { binding.root.background = it }
        backgroundColor?.let { binding.root.setBackgroundColor(it) }
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
        actions: GuideActions,
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
        padding: Padding?,
        context: Context,
    ) {
        padding?.let {
            binding.root.setPadding(
                WalkthroughPositioning.dpToPx(context, it.left?.toFloat()),
                WalkthroughPositioning.dpToPx(context, it.top?.toFloat()),
                WalkthroughPositioning.dpToPx(context, it.right?.toFloat()),
                WalkthroughPositioning.dpToPx(context, it.bottom?.toFloat()),
            )
        }
    }
}
