package id.infiniteuny.mediapembelajaran.ui.materi

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.MateriModel
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by wijaya on 16/12/19
 */
class MateriVH(override val containerView: View):RecyclerView.ViewHolder(containerView)
,RvAdapter.Binder<MateriModel>,LayoutContainer{

    override fun bindData(data: MateriModel) {

    }
}