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


/**
 * The main activity for the application.
 *
 * This activity serves as the entry point and is responsible for setting up the user interface and initializing
 * the walkthrough functionality. It uses data binding to manage the layout and contains methods to handle
 * interactions with the walkthrough view.
 */
class MainActivity : AppCompatActivity() {

    // Backing property for the activity's view binding
    private var _binding: ActivityMainBinding? = null

    // Property to access the binding object, ensuring it's not null
    private val binding get() = _binding!!

    /**
     * Called when the activity is first created.
     *
     * This method sets up the view binding and initializes the walkthrough view. It ensures that the layout is
     * inflated and bound to the activity's lifecycle.
     *
     * @param savedInstanceState A Bundle containing the activity's previously saved state. If the activity has
     * been newly created, this value will be null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set up data binding with the activity's layout
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Initialize the walkthrough view
        init()
    }
    private fun init() {

        /**
         * Sets up and initializes the Walkthrough view using the `WalkthroughBuilder` class.
         *
         * This method demonstrates how to create and configure a walkthrough view by using the builder pattern.
         * It requires specifying the parent layout view group and the view that needs to be highlighted.
         * Additionally, it provides various customization options such as setting the title and description text,
         * configuring button texts, backgrounds, and colors, and defining callback methods for button clicks and outside clicks.
         *
         * The configured `WalkthroughBuilder` instance is used to create the final `Walkthrough` view.
         */
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


    /**
     * Handles the click event for the back button.
     *
     * Displays a toast message indicating that the back button was clicked.
     */
    private fun onBackClick() {
        Toast.makeText(this, "Back Button", Toast.LENGTH_SHORT).show()
    }

    /**
     * Handles the click event for the next button.
     *
     * Displays a toast message indicating that the next button was clicked.
     */
    private fun onNextClick() {
        Toast.makeText(this, "Next Button", Toast.LENGTH_SHORT).show()
    }

    /**
     * Handles the click event for clicking outside the dialog.
     *
     * Displays a toast message indicating that an area outside the dialog was clicked.
     */
    private fun onOutsideClick() {
        Toast.makeText(this, "Outside Button", Toast.LENGTH_SHORT).show()
    }

    /**
     * Handles the click event for the close button.
     *
     * Displays a toast message indicating that the close button was clicked.
     */
    private fun onCloseClick() {
        Toast.makeText(this, "Close Button", Toast.LENGTH_SHORT).show()
    }


}