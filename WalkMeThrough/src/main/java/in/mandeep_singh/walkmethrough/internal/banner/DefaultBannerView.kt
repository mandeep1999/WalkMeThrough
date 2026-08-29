package `in`.mandeep_singh.walkmethrough.internal.banner

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.WalkthroughActions
import `in`.mandeep_singh.walkmethrough.library.databinding.LayoutBannerBinding

internal object DefaultBannerView {

    fun create(
        context: Context,
        guideStep: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View {
        val binding = LayoutBannerBinding.inflate(LayoutInflater.from(context))
        bind(binding, guideStep, actions)
        return binding.root
    }

    private fun bind(
        binding: LayoutBannerBinding,
        guideStep: GuideStep,
        actions: WalkthroughActions,
    ) {
        binding.bannerTitleTextView.isVisible = !guideStep.titleText.isNullOrBlank()
        guideStep.titleText?.let { binding.bannerTitleTextView.text = it }
        guideStep.titleTextColor?.let { binding.bannerTitleTextView.setTextColor(it) }

        binding.bannerDescriptionTextView.isVisible = !guideStep.descriptionText.isNullOrBlank()
        guideStep.descriptionText?.let { binding.bannerDescriptionTextView.text = it }
        guideStep.descriptionTextColor?.let { binding.bannerDescriptionTextView.setTextColor(it) }

        guideStep.dialogBackground?.let { binding.root.background = it }
        guideStep.dialogBackgroundColor?.let { binding.root.setBackgroundColor(it) }

        binding.bannerNextButton.isVisible = !guideStep.nextButtonText.isNullOrBlank()
        binding.bannerNextButton.text = guideStep.nextButtonText
        guideStep.nextButtonBackground?.let { binding.bannerNextButton.background = it }
        guideStep.nextButtonBackgroundColor?.let { binding.bannerNextButton.setBackgroundColor(it) }
        guideStep.nextButtonTextColor?.let { binding.bannerNextButton.setTextColor(it) }
        binding.bannerNextButton.setOnClickListener { actions.onNext() }
    }
}
