package `in`.mandeep_singh.walkmethrough.internal.tooltip

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import `in`.mandeep_singh.walkmethrough.Position
import `in`.mandeep_singh.walkmethrough.WalkthroughActions
import `in`.mandeep_singh.walkmethrough.WalkthroughStep
import `in`.mandeep_singh.walkmethrough.WalkthroughTooltipContent
import `in`.mandeep_singh.walkmethrough.library.databinding.LayoutTooltipBinding

internal object DefaultTooltipView {

    fun create(
        context: Context,
        step: WalkthroughStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View {
        val binding = LayoutTooltipBinding.inflate(LayoutInflater.from(context))
        bind(binding, step, actions)
        return binding.root
    }

    private fun bind(
        binding: LayoutTooltipBinding,
        step: WalkthroughStep,
        actions: WalkthroughActions,
    ) {
        val resolvedPosition = step.dialogPosition ?: Position.BOTTOM
        val showUpArrow = resolvedPosition == Position.BOTTOM
        val showDownArrow = resolvedPosition == Position.TOP

        binding.tooltipArrowUp.isVisible = step.showTooltipArrow && showUpArrow
        binding.tooltipArrowDown.isVisible = step.showTooltipArrow && showDownArrow

        binding.tooltipTitleTextView.isVisible = !step.titleText.isNullOrBlank()
        step.titleText?.let { binding.tooltipTitleTextView.text = it }
        step.titleTextColor?.let { binding.tooltipTitleTextView.setTextColor(it) }

        binding.tooltipDescriptionTextView.isVisible = !step.descriptionText.isNullOrBlank()
        step.descriptionText?.let { binding.tooltipDescriptionTextView.text = it }
        step.descriptionTextColor?.let { binding.tooltipDescriptionTextView.setTextColor(it) }

        setBackground(binding, step.dialogBackground, step.dialogBackgroundColor)

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
        step: WalkthroughStep,
        stepIndex: Int,
        totalSteps: Int,
        actions: WalkthroughActions,
    ): View = DefaultTooltipView.create(context, step, stepIndex, totalSteps, actions)
}
