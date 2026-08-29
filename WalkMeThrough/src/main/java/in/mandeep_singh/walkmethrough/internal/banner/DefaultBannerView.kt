package `in`.mandeep_singh.walkmethrough.internal.banner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import `in`.mandeep_singh.walkmethrough.GuideActions
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.library.databinding.LayoutBannerBinding

internal object DefaultBannerView {

    fun create(
        context: Context,
        step: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: GuideActions,
    ): View {
        val binding = LayoutBannerBinding.inflate(LayoutInflater.from(context))
        bind(binding, step, actions)
        return binding.root
    }

    private fun bind(
        binding: LayoutBannerBinding,
        step: GuideStep,
        actions: GuideActions,
    ) {
        binding.bannerTitleTextView.isVisible = !step.title.isNullOrBlank()
        step.title?.let { binding.bannerTitleTextView.text = it }
        step.titleColor?.let { binding.bannerTitleTextView.setTextColor(it) }

        binding.bannerDescriptionTextView.isVisible = !step.description.isNullOrBlank()
        step.description?.let { binding.bannerDescriptionTextView.text = it }
        step.descriptionColor?.let { binding.bannerDescriptionTextView.setTextColor(it) }

        step.background?.let { binding.root.background = it }
        step.backgroundColor?.let { binding.root.setBackgroundColor(it) }

        binding.bannerNextButton.isVisible = !step.nextText.isNullOrBlank()
        binding.bannerNextButton.text = step.nextText
        step.nextBackground?.let { binding.bannerNextButton.background = it }
        step.nextBackgroundColor?.let { binding.bannerNextButton.setBackgroundColor(it) }
        step.nextTextColor?.let { binding.bannerNextButton.setTextColor(it) }
        binding.bannerNextButton.setOnClickListener { actions.onNext() }
    }
}
