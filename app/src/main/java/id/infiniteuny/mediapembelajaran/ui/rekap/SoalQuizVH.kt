package id.infiniteuny.mediapembelajaran.ui.rekap

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.QuizModel
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_circle.*
import kotlinx.android.synthetic.main.item_nilai.*

class SoalQuizVH(override val containerView: View,val listener:(QuizModel)->Unit) : RecyclerView.ViewHolder(containerView)
    , RvAdapter.Binder<QuizModel>, LayoutContainer {

    override fun bindData(data: QuizModel) {
//        btn_soal.text=data.id.toString()
        tv_soal.text=data.id.toString()

        itemView.setOnClickListener{listener(data)}
    }
}