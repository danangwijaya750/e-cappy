package id.infiniteuny.mediapembelajaran.ui.rekap

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_nilai.tv_name
import kotlinx.android.synthetic.main.item_nilai.tv_score

/**
 * Created by wijaya on 16/12/19
 */
class ScoreVH(override val containerView: View) : RecyclerView.ViewHolder(containerView)
    , RvAdapter.Binder<ScoreModel>, LayoutContainer {

    override fun bindData(data: ScoreModel) {
        tv_name.text = data.name
        tv_score.text = data.grade
        itemView.setOnClickListener {
            //            val intent = Intent(containerView.context!!, DetailMateriActivity::class.java)
//            intent.putExtra("data", data)
//            containerView.context.startActivity(intent)
        }
    }
}