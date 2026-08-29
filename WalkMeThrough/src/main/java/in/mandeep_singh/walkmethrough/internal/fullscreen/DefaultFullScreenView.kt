package `in`.mandeep_singh.walkmethrough.internal.fullscreen

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import `in`.mandeep_singh.walkmethrough.GuideActions
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.Padding
import `in`.mandeep_singh.walkmethrough.library.databinding.LayoutFullScreenBinding
import `in`.mandeep_singh.walkmethrough.internal.util.WalkthroughPositioning

internal object DefaultFullScreenView {

    fun create(
        context: Context,
        step: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: GuideActions,
    ): View {
        val binding = LayoutFullScreenBinding.inflate(LayoutInflater.from(context))
        bind(binding, step, stepIndex, actions)
        return binding.root
    }

    private fun bind(
        binding: LayoutFullScreenBinding,
        step: GuideStep,
        stepIndex: Int,
        actions: GuideActions,
    ) {
        val showBack = stepIndex > 0
        val context = binding.root.context

        binding.fullScreenTitleTextView.isVisible = !step.title.isNullOrBlank()
        step.title?.let { binding.fullScreenTitleTextView.text = it }
        step.titleColor?.let { binding.fullScreenTitleTextView.setTextColor(it) }

        binding.fullScreenDescriptionTextView.isVisible = !step.description.isNullOrBlank()
        step.description?.let { binding.fullScreenDescriptionTextView.text = it }
        step.descriptionColor?.let { binding.fullScreenDescriptionTextView.setTextColor(it) }

        setBackground(binding, step.background, step.backgroundColor)
        setUpNextButton(binding, step, actions)
        setUpBackButton(binding, step, showBack, actions)
        binding.fullScreenCloseIcon.setOnClickListener { actions.onClose() }
        binding.fullScreenSpace.isVisible = showBack && binding.fullScreenNextButton.isVisible
        setUpPadding(binding, step.padding, context)
    }

    private fun setBackground(
        binding: LayoutFullScreenBinding,
        background: Drawable?,
        backgroundColor: Int?,
    ) {
        background?.let { binding.fullScreenBody.background = it }
        backgroundColor?.let { binding.fullScreenBody.setBackgroundColor(it) }
    }

    private fun setUpNextButton(
        binding: LayoutFullScreenBinding,
        step: GuideStep,
        actions: GuideActions,
    ) {
        step.nextBackground?.let { binding.fullScreenNextButton.background = it }
        step.nextBackgroundColor?.let { binding.fullScreenNextButton.setBackgroundColor(it) }
        binding.fullScreenNextButton.isVisible = !step.nextText.isNullOrBlank()
        binding.fullScreenNextButton.text = step.nextText
        step.nextTextColor?.let { binding.fullScreenNextButton.setTextColor(it) }
        binding.fullScreenNextButton.setOnClickListener { actions.onNext() }
    }

    private fun setUpBackButton(
        binding: LayoutFullScreenBinding,
        step: GuideStep,
        showBack: Boolean,
        actions: GuideActions,
    ) {
        step.backBackground?.let { binding.fullScreenBackButton.background = it }
        step.backBackgroundColor?.let { binding.fullScreenBackButton.setBackgroundColor(it) }
        binding.fullScreenBackButton.isVisible = showBack && !step.backText.isNullOrBlank()
        binding.fullScreenBackButton.text = step.backText
        step.backTextColor?.let { binding.fullScreenBackButton.setTextColor(it) }
        binding.fullScreenBackButton.setOnClickListener {
            if (showBack) actions.onBack()
        }
    }

    private fun setUpPadding(
        binding: LayoutFullScreenBinding,
        padding: Padding?,
        context: Context,
    ) {
        padding?.let {
            binding.fullScreenBody.setPadding(
                WalkthroughPositioning.dpToPx(context, it.left?.toFloat()),
                WalkthroughPositioning.dpToPx(context, it.top?.toFloat()),
                WalkthroughPositioning.dpToPx(context, it.right?.toFloat()),
                WalkthroughPositioning.dpToPx(context, it.bottom?.toFloat()),
            )
        }
    }
}
