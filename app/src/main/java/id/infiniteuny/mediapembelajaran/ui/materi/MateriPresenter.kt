package id.infiniteuny.mediapembelajaran.ui.materi

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.utils.JsonReader

/**
 * Created by wijaya on 14/12/19
 */
class MateriPresenter(private val view: MateriView,private val context: Context) {

    fun getMaterials(){
        view.showMaterials(JsonReader.readMaterials(context, R.raw.materials))
    }
}