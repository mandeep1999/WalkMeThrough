package `in`.mandeep_singh.walkmethrough.walk_me_through.data.models

import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName

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

data class PaddingModel(
    @SerializedName("top")
    val top: Float? = null,
    @SerializedName("right")
    val right: Float? = null,
    @SerializedName("bottom")
    val bottom: Float? = null,
    @SerializedName("left")
    val left: Float? = null,
)
