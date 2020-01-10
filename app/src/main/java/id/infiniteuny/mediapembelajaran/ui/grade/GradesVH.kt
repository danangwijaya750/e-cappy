package id.infiniteuny.mediapembelajaran.ui.grade

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by wijaya on 10/01/20
 */
class GradesVH(override val containerView: View) : RecyclerView.ViewHolder(containerView)
    , RvAdapter.Binder<ScoreModel>, LayoutContainer {

    override fun bindData(data: ScoreModel) {
    }
}
