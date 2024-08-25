WalkMeThrough
WalkMeThrough is an Android library designed to guide users through your app by highlighting specific views and displaying instructional dialogs. This README demonstrates how to set up and use the WalkthroughBuilder to create a walkthrough experience in your application.

Overview
The WalkthroughBuilder class helps you create and configure a walkthrough view that highlights a specific UI element and displays a dialog box with customizable content.

Usage
1. Add Dependency
Make sure to include the library dependency in your build.gradle file:

gradle
Copy code
dependencies {
    implementation 'in.mandeep_singh.walkmethrough:walk_me_through:1.0.0'
}
2. Set Up Your Activity
Here’s a detailed guide on setting up the walkthrough using the WalkthroughBuilder class in your MainActivity:

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
 */
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


3. Configuration Options
The WalkthroughBuilder class allows you to configure the following:

setViewToHighlight(view: View): The view to highlight in the walkthrough.
setParentViewGroup(viewGroup: ViewGroup): The parent view group where the walkthrough view will be added.
setTitleText(text: String): The title text to display in the dialog.
setDescriptionText(text: String): The description text to display in the dialog.
setNextButtonText(text: String): The text for the "Next" button.
setBackButtonText(text: String): The text for the "Back" button.
setBackButtonBackground(drawable: Drawable): Background drawable for the "Back" button.
setNextButtonBackground(drawable: Drawable): Background drawable for the "Next" button.
setBackButtonTextColor(color: Int): Text color for the "Back" button.
setNextButtonTextColor(color: Int): Text color for the "Next" button.
setOnBackClick(listener: () -> Unit): Callback for when the "Back" button is clicked.
setOnNextClick(listener: () -> Unit): Callback for when the "Next" button is clicked.
setDialogPosition(position: Position): Position of the dialog relative to the highlighted view.
setOnOutsideClickListener(listener: () -> Unit): Callback for clicks outside the dialog.
setOnCloseClick(listener: () -> Unit): Callback for the close button click.
4. Adding Images
To enhance your walkthrough with images, you can customize the dialog's appearance by using drawable resources. Add your image assets to the res/drawable directory and reference them in the WalkthroughBuilder.

Here’s how you can set an image as the background for the dialog or buttons:


5. Demo Video
To see a demonstration of how the WalkMeThrough library works, including image support and interactive walkthrough features, watch the following video:



Replace VIDEO_ID with the actual ID of your video on YouTube.

6. Screenshots
Here are some screenshots demonstrating the WalkMeThrough library in action, including how images are used:



Feel free to replace these placeholder images with actual screenshots of your app.

License
This project is licensed under the MIT License. See the LICENSE file for more details.

