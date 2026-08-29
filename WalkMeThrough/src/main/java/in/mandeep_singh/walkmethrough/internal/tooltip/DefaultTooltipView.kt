package `in`.mandeep_singh.walkmethrough.internal.tooltip

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.Position
import `in`.mandeep_singh.walkmethrough.WalkthroughActions
import `in`.mandeep_singh.walkmethrough.WalkthroughTooltipContent
import `in`.mandeep_singh.walkmethrough.library.databinding.LayoutTooltipBinding

internal object DefaultTooltipView {

    fun create(
        context: Context,
        guideStep: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View {
        val binding = LayoutTooltipBinding.inflate(LayoutInflater.from(context))
        bind(binding, guideStep, actions)
        return binding.root
    }

    private fun bind(
        binding: LayoutTooltipBinding,
        guideStep: GuideStep,
        actions: WalkthroughActions,
    ) {
        val resolvedPosition = guideStep.placement ?: Position.BOTTOM
        val showUpArrow = resolvedPosition == Position.BOTTOM
        val showDownArrow = resolvedPosition == Position.TOP

        binding.tooltipArrowUp.isVisible = guideStep.showArrow && showUpArrow
        binding.tooltipArrowDown.isVisible = guideStep.showArrow && showDownArrow

        binding.tooltipTitleTextView.isVisible = !guideStep.titleText.isNullOrBlank()
        guideStep.titleText?.let { binding.tooltipTitleTextView.text = it }
        guideStep.titleTextColor?.let { binding.tooltipTitleTextView.setTextColor(it) }

        binding.tooltipDescriptionTextView.isVisible = !guideStep.descriptionText.isNullOrBlank()
        guideStep.descriptionText?.let { binding.tooltipDescriptionTextView.text = it }
        guideStep.descriptionTextColor?.let { binding.tooltipDescriptionTextView.setTextColor(it) }

        setBackground(binding, guideStep.dialogBackground, guideStep.dialogBackgroundColor)

        binding.root.setOnClickListener { actions.onNext() }
    }

    private fun setBackground(
        binding: LayoutTooltipBinding,
        background: Drawable?,
        backgroundColor: Int?,
    ) {
        background?.let { binding.tooltipBody.background = it }
        backgroundColor?.let { binding.tooltipBody.setBackgroundColor(it) }
    }
}

internal class DefaultWalkthroughTooltipContent : WalkthroughTooltipContent {
    override fun createView(
        context: Context,
        guideStep: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View = DefaultTooltipView.create(context, guideStep, stepIndex, totalSteps, actions)
}
