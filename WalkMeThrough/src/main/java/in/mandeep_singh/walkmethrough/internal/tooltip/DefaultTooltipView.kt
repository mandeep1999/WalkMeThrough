package `in`.mandeep_singh.walkmethrough.internal.tooltip

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import `in`.mandeep_singh.walkmethrough.GuideActions
import `in`.mandeep_singh.walkmethrough.GuideStep
import `in`.mandeep_singh.walkmethrough.Placement
import `in`.mandeep_singh.walkmethrough.library.databinding.LayoutTooltipBinding

internal object DefaultTooltipView {

    fun create(
        context: Context,
        step: GuideStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: GuideActions,
    ): View {
        val binding = LayoutTooltipBinding.inflate(LayoutInflater.from(context))
        bind(binding, step, actions)
        return binding.root
    }

    private fun bind(
        binding: LayoutTooltipBinding,
        step: GuideStep,
        actions: GuideActions,
    ) {
        val resolvedPlacement = step.placement ?: Placement.BOTTOM
        val showUpArrow = resolvedPlacement == Placement.BOTTOM
        val showDownArrow = resolvedPlacement == Placement.TOP

        binding.tooltipArrowUp.isVisible = step.showArrow && showUpArrow
        binding.tooltipArrowDown.isVisible = step.showArrow && showDownArrow

        binding.tooltipTitleTextView.isVisible = !step.title.isNullOrBlank()
        step.title?.let { binding.tooltipTitleTextView.text = it }
        step.titleColor?.let { binding.tooltipTitleTextView.setTextColor(it) }

        binding.tooltipDescriptionTextView.isVisible = !step.description.isNullOrBlank()
        step.description?.let { binding.tooltipDescriptionTextView.text = it }
        step.descriptionColor?.let { binding.tooltipDescriptionTextView.setTextColor(it) }

        setBackground(binding, step.background, step.backgroundColor)

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
