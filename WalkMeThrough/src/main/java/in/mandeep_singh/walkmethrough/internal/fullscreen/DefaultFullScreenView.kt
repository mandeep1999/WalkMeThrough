package `in`.mandeep_singh.walkmethrough.internal.fullscreen

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.Padding
import `in`.mandeep_singh.walkmethrough.WalkthroughActions
import `in`.mandeep_singh.walkmethrough.library.databinding.LayoutFullScreenBinding
import `in`.mandeep_singh.walkmethrough.internal.util.WalkthroughPositioning

internal object DefaultFullScreenView {

    fun create(
        context: Context,
        guideStep: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View {
        val binding = LayoutFullScreenBinding.inflate(LayoutInflater.from(context))
        bind(binding, guideStep, stepIndex, actions)
        return binding.root
    }

    private fun bind(
        binding: LayoutFullScreenBinding,
        guideStep: GuideStep,
        stepIndex: Int,
        actions: WalkthroughActions,
    ) {
        val showBack = stepIndex > 0
        val context = binding.root.context

        binding.fullScreenTitleTextView.isVisible = !guideStep.titleText.isNullOrBlank()
        guideStep.titleText?.let { binding.fullScreenTitleTextView.text = it }
        guideStep.titleTextColor?.let { binding.fullScreenTitleTextView.setTextColor(it) }

        binding.fullScreenDescriptionTextView.isVisible = !guideStep.descriptionText.isNullOrBlank()
        guideStep.descriptionText?.let { binding.fullScreenDescriptionTextView.text = it }
        guideStep.descriptionTextColor?.let { binding.fullScreenDescriptionTextView.setTextColor(it) }

        setBackground(binding, guideStep.dialogBackground, guideStep.dialogBackgroundColor)
        setUpNextButton(binding, guideStep, actions)
        setUpBackButton(binding, guideStep, showBack, actions)
        binding.fullScreenCloseIcon.setOnClickListener { actions.onClose() }
        binding.fullScreenSpace.isVisible = showBack && binding.fullScreenNextButton.isVisible
        setUpPadding(binding, guideStep.dialogPadding, context)
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
        guideStep: GuideStep,
        actions: WalkthroughActions,
    ) {
        guideStep.nextButtonBackground?.let { binding.fullScreenNextButton.background = it }
        guideStep.nextButtonBackgroundColor?.let { binding.fullScreenNextButton.setBackgroundColor(it) }
        binding.fullScreenNextButton.isVisible = !guideStep.nextButtonText.isNullOrBlank()
        binding.fullScreenNextButton.text = guideStep.nextButtonText
        guideStep.nextButtonTextColor?.let { binding.fullScreenNextButton.setTextColor(it) }
        binding.fullScreenNextButton.setOnClickListener { actions.onNext() }
    }

    private fun setUpBackButton(
        binding: LayoutFullScreenBinding,
        guideStep: GuideStep,
        showBack: Boolean,
        actions: WalkthroughActions,
    ) {
        guideStep.backButtonBackground?.let { binding.fullScreenBackButton.background = it }
        guideStep.backButtonBackgroundColor?.let { binding.fullScreenBackButton.setBackgroundColor(it) }
        binding.fullScreenBackButton.isVisible = showBack && !guideStep.backButtonText.isNullOrBlank()
        binding.fullScreenBackButton.text = guideStep.backButtonText
        guideStep.backButtonTextColor?.let { binding.fullScreenBackButton.setTextColor(it) }
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
