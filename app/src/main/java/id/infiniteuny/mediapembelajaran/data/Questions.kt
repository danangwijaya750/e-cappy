package id.infiniteuny.mediapembelajaran.data

import com.google.gson.annotations.SerializedName

/**
 * Created by wijaya on 06/01/20
 */
data class Questions(
    @SerializedName("data")
    val data:List<QuestionModel>
)