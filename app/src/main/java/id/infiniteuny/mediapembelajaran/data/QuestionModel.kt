package id.infiniteuny.mediapembelajaran.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by wijaya on 20/12/19
 */
@Parcelize
data class QuestionModel(
    var question: String,
    var option1: String,
    var option2: String,
    var option3: String,
    var option4: String,
    var option5: String,
    var rightAns: Int
) : Parcelable{
    constructor():this("","","","","","",0)
}