package `in`.mandeep_singh.walkmethrough.walk_me_through.data.models

import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName

/**
 * Data model representing the configuration for a dialog in a walkthrough.
 *
 * @property titleText The text to display as the dialog's title.
 * @property descriptionText The text to display as the dialog's description.
 * @property backButtonText The text to display on the back button.
 * @property nextButtonText The text to display on the next button.
 * @property dialogBackgroundColor The color of the dialog's background.
 * @property titleTextColor The color of the title text.
 * @property descriptionTextColor The color of the description text.
 * @property backButtonTextColor The color of the back button text.
 * @property nextButtonTextColor The color of the next button text.
 * @property backButtonBackgroundColor The background color of the back button.
 * @property nextButtonBackgroundColor The background color of the next button.
 * @property dialogBackground The drawable resource for the dialog's background.
 * @property nextButtonBackground The drawable resource for the next button's background.
 * @property backButtonBackground The drawable resource for the back button's background.
 * @property dialogPadding Padding for the dialog content.
 * @property onNextClick Lambda function to be invoked when the next button is clicked.
 * @property onBackClick Lambda function to be invoked when the back button is clicked.
 * @property onCloseClick Lambda function to be invoked when the close button is clicked.
 */
data class DialogModel(
    @SerializedName("title_text")
    val titleText: String? = null,

    @SerializedName("description_text")
    val descriptionText: String? = null,

    @SerializedName("back_button_text")
    val backButtonText: String? = null,

    @SerializedName("next_button_text")
    val nextButtonText: String? = null,

    @SerializedName("dialog_back_ground_color")
    val dialogBackgroundColor: Int? = null,

    @SerializedName("title_text_color")
    val titleTextColor: Int? = null,

    @SerializedName("description_text_color")
    val descriptionTextColor: Int? = null,

    @SerializedName("back_button_text_color")
    val backButtonTextColor: Int? = null,

    @SerializedName("next_button_text_color")
    val nextButtonTextColor: Int? = null,

    @SerializedName("back_button_background_color")
    val backButtonBackgroundColor: Int? = null,

    @SerializedName("next_button_background_color")
    val nextButtonBackgroundColor: Int? = null,

    @SerializedName("dialog_background")
    val dialogBackground: Drawable? = null,

    @SerializedName("next_button_background")
    val nextButtonBackground: Drawable? = null,

    @SerializedName("back_button_background")
    val backButtonBackground: Drawable? = null,

    @SerializedName("dialog_padding")
    val dialogPadding: PaddingModel? = null,

    @SerializedName("on_next_click")
    val onNextClick: (() -> Unit)? = null,

    @SerializedName("on_back_click")
    val onBackClick: (() -> Unit)? = null,

    @SerializedName("on_close_click")
    val onCloseClick: (() -> Unit)? = null
)

/**
 * Data model representing padding values for a dialog.
 *
 * @property top The top padding value in density-independent pixels (dp).
 * @property right The right padding value in density-independent pixels (dp).
 * @property bottom The bottom padding value in density-independent pixels (dp).
 * @property left The left padding value in density-independent pixels (dp).
 */
data class PaddingModel(
    @SerializedName("top")
    val top: Float? = null,

    @SerializedName("right")
    val right: Float? = null,

    @SerializedName("bottom")
    val bottom: Float? = null,

    @SerializedName("left")
    val left: Float? = null
)
