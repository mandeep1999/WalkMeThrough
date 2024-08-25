package `in`.mandeep_singh.walkmethrough

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import `in`.mandeep_singh.walkmethrough.databinding.ActivityMainBinding
import `in`.mandeep_singh.walkmethrough.walk_me_through.components.WalkthroughBuilder
import `in`.mandeep_singh.walkmethrough.walk_me_through.data.enums.Position


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        WalkthroughBuilder(this)
            .setViewToHighlight(binding.textView)
            .setParentViewGroup(binding.content)
            .setTitleText(getString(R.string.this_is_title_text))
            .setDescriptionText(getString(R.string.this_is_description_text))
            .setNextButtonText(getString(R.string.next))
            .setBackButtonText(getString(R.string.back))
            .setBackButtonBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.curve_background_with_indigo_border
                )
            )
            .setNextButtonBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.curve_solid_indigo_background_with_indigo_border
                )
            )
            .setBackButtonTextColor(getColor(R.color.indigo))
            .setNextButtonTextColor(getColor(R.color.white))
            .setOnBackClick(::onBackClick)
            .setOnNextClick(::onNextClick)
            .setDialogPosition(Position.CENTER)
            .setOnOutsideClickListener(::onOutsideClick)
            .setOnCloseClick(::onCloseClick)
            .build()
    }


    private fun onBackClick() {
        Toast.makeText(this, "Back Button", Toast.LENGTH_SHORT).show()
    }

    private fun onNextClick() {
        Toast.makeText(this, "Next Button", Toast.LENGTH_SHORT).show()
    }

    private fun onOutsideClick() {
        Toast.makeText(this, "Outside Button", Toast.LENGTH_SHORT).show()
    }

    private fun onCloseClick() {
        Toast.makeText(this, "Close Button", Toast.LENGTH_SHORT).show()
    }

}