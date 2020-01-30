package id.infiniteuny.mediapembelajaran.ui.rekap

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import id.infiniteuny.mediapembelajaran.utils.logD
import kotlinx.android.synthetic.main.activity_rekap_nilai.btn_back
import kotlinx.android.synthetic.main.activity_rekap_nilai.rv_nilai
import kotlinx.android.synthetic.main.activity_rekap_nilai.tb_layout
import kotlinx.android.synthetic.main.activity_rekap_nilai.tv_fld_name
import kotlinx.android.synthetic.main.activity_rekap_nilai.tv_fld_score

class RekapNilaiActivity : AppCompatActivity() {

    private val dataGrades = mutableListOf<ScoreModel>()
    private var isNameDesc = true
    private var isScoreDesc = true
    private val rvAdapter = object : RvAdapter<Any>(dataGrades) {
        override fun layoutId(position: Int, obj: Any): Int = R.layout.item_nilai

        override fun viewHolder(view: View, viewType: Int): ViewHolder = ScoreVH(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_rekap_nilai)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        rv_nilai.apply {
            layoutManager = LinearLayoutManager(this@RekapNilaiActivity)
            adapter = rvAdapter
        }
        getGrades()
        tv_fld_name.setOnClickListener {
            when (isNameDesc) {
                true -> dataGrades.sortBy { it.name }
                false -> dataGrades.sortByDescending { it.name }
            }
            isNameDesc = !isNameDesc
            showData()
        }
        tv_fld_score.setOnClickListener {
            when (isScoreDesc) {
                true -> dataGrades.sortBy { it.grade.toDoubleOrNull() }
                false -> dataGrades.sortByDescending { it.grade.toDoubleOrNull() }
            }
            isScoreDesc = !isScoreDesc
            showData()
        }
        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getGrades() {
        val db = FirebaseFirestore.getInstance()
        clearData()
        db.collection("student_grade").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    it.forEach { data ->
                        dataGrades.add(data.toObject(ScoreModel::class.java))
                    }
                    logD(dataGrades.size.toString())
                    showData()
                }
            }
            .addOnFailureListener {

            }
    }

    private fun clearData() {
        dataGrades.clear()
        tb_layout.removeAllViews()
    }

    private fun showData() {
        rvAdapter.notifyDataSetChanged()
//        dataGrades.forEachIndexed{index, scoreModel ->
//            val tbRow=TableRow(this)
//            val lParams=TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT)
//            tbRow.layoutParams=lParams
//            val tvName=TextView(this)
//            val tvScore=TextView(this)
//            tvName.width=600
//            tvScore.width=500
//            tvName.textSize=15f
//            tvScore.textSize=15f
//            tvName.text=scoreModel.name
//            tvScore.text=scoreModel.grade
//            tbRow.addView(tvName)
//            tbRow.addView(tvScore)
//            tb_layout.addView(tbRow,index)
//        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
