package id.infiniteuny.mediapembelajaran.ui.rekap

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.QuizModel
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizPosHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_circle.*
import kotlinx.android.synthetic.main.item_nilai.*

class SoalQuizVH(override val containerView: View,val listener:(QuizModel)->Unit) : RecyclerView.ViewHolder(containerView)
    , RvAdapter.Binder<QuizModel>, LayoutContainer {

    override fun bindData(data: QuizModel) {
//        btn_soal.text=data.id.toString()
        tv_soal.text=data.id.toString()
        if (data.id== RekapPosHelper.rekapPos) {
            liner.background=containerView.context.resources.getDrawable(R.drawable.circle_layout)
            tv_soal.setTextColor(Color.parseColor("#000000"))
            tv_soal.setTextColor(Color.parseColor("#FFFFFF"))
        }else{
            liner.background=containerView.context.resources.getDrawable(R.drawable.circle_layout_selected)
            tv_soal.setTextColor(Color.parseColor("#000000"))
        }
        itemView.setOnClickListener{listener(data)}
    }
}