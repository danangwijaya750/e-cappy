package id.infiniteuny.mediapembelajaran.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.QuizModel
import id.infiniteuny.mediapembelajaran.ui.rekap.SoalQuizVH

class RvAdapterUnit (val data: List<QuizModel>, val listener: (QuizModel) -> Unit) :
    RecyclerView.Adapter<SoalQuizVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoalQuizVH {
        return SoalQuizVH(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_circle,parent,false),listener)
    }

    override fun getItemCount(): Int =data.size

    override fun onBindViewHolder(holder: SoalQuizVH, position: Int) {
        holder.bindData(data[position])
    }

}