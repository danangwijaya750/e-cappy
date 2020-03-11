package id.infiniteuny.mediapembelajaran.utils

import android.content.Context
import com.google.gson.Gson
import id.infiniteuny.mediapembelajaran.data.MaterialModel
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by wijaya on 19/12/19
 */
object JsonReader {

    fun readMaterials(context: Context, file: Int): List<MaterialModel.Data> {
        val inStream = context.resources.openRawResource(file)
        val bufferedReader = BufferedReader(InputStreamReader(inStream))
        val read = bufferedReader.use { it.readText() }
        val data = Gson().fromJson(read, MaterialModel::class.java)
        bufferedReader.close()
        return data.data
    }

}