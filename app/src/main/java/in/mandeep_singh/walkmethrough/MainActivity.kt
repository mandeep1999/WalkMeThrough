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
            .setTitleText("Demo Title Text")
            .setDescriptionText("This is a demo description")
            .setNextButtonText("Next btn")
            .setBackButtonText("Back btn")
            .setTitleTextColor(Color.parseColor("#FFA500"))
            .setDescriptionTextColor(Color.parseColor("#3F00FF"))
            .setBackButtonBackground(getBorderedDrawable())
            .setNextButtonBackground(getSolidDrawable())
            .setBackButtonTextColor(Color.parseColor("#FFA500"))
            .setNextButtonTextColor(Color.parseColor("#FFFFFF"))
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

    private fun getBorderedDrawable(): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(ContextCompat.getColor(this, R.color.white))
        drawable.cornerRadius = 28f // Set corner radius
        drawable.setStroke(2, ContextCompat.getColor(this, R.color.orange))
        return drawable
    }

    private fun getSolidDrawable(): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(ContextCompat.getColor(this, R.color.orange))
        drawable.cornerRadius = 28f // Set corner radius
        drawable.setStroke(2, ContextCompat.getColor(this, R.color.orange))
        return drawable
    }

}