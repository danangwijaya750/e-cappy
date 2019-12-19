package id.infiniteuny.mediapembelajaran.ui.materi

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.MaterialModel
import id.infiniteuny.mediapembelajaran.ui.detail_materi.DetailMateriActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_layout.tv_materi

/**
 * Created by wijaya on 16/12/19
 */
class MateriVH(override val containerView: View) : RecyclerView.ViewHolder(containerView)
    , RvAdapter.Binder<MaterialModel.Data>, LayoutContainer {

    override fun bindData(data: MaterialModel.Data) {
        tv_materi.text = data.title
        itemView.setOnClickListener {
            val intent = Intent(containerView.context!!, DetailMateriActivity::class.java)
            intent.putExtra("data", data)
            containerView.context.startActivity(intent)
        }
    }
}