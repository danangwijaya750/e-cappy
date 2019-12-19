package id.infiniteuny.mediapembelajaran.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by wijaya on 16/12/19
 */
@Parcelize
data class MaterialModel(
    @SerializedName("data")
    var data: List<Data>
) : Parcelable {

    @Parcelize
    data class Data(
        @SerializedName("title")
        var title: String,
        @SerializedName("file")
        var file: String
    ) : Parcelable
}