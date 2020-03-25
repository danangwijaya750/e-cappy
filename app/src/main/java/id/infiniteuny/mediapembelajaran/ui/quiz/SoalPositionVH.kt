package id.infiniteuny.mediapembelajaran.ui.quiz

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_circle.*
import kotlinx.android.synthetic.main.item_circle.view.*
import kotlinx.android.synthetic.main.item_circle.view.liner

class SoalPositionVH(override val containerView: View):RecyclerView.ViewHolder(containerView)
    ,RvAdapter.Binder<Int>,LayoutContainer {
    override fun bindData(data: Int) {
        tv_soal.text="$data"
        if (data==QuizPosHelper.quizPos) {
            liner.background=containerView.context.resources.getDrawable(R.drawable.circle_layout_selected)
            tv_soal.setTextColor(Color.parseColor("#000000"))
        }else{
            liner.background=containerView.context.resources.getDrawable(R.drawable.circle_layout_transparent)
            tv_soal.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }
}