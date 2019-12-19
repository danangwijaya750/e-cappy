package id.infiniteuny.mediapembelajaran.ui.materi

import id.infiniteuny.mediapembelajaran.data.MaterialModel

/**
 * Created by wijaya on 14/12/19
 */
interface MateriView {
    fun isLoading(state:Boolean)
    fun isError(msg:String)
    fun showMaterials(data:List<MaterialModel.Data>)
}