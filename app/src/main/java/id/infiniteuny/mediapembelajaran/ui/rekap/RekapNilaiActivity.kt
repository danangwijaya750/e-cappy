package id.infiniteuny.mediapembelajaran.ui.rekap

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import id.infiniteuny.mediapembelajaran.utils.logD
import kotlinx.android.synthetic.main.activity_rekap_nilai.*

class RekapNilaiActivity : AppCompatActivity() {

    private val dataGrades = mutableListOf<ScoreModel>()
    private val dataGradesMaster= mutableListOf<ScoreModel>()
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
            showDataAfterSort()
        }
        tv_fld_score.setOnClickListener {
            when (isScoreDesc) {
                true -> dataGrades.sortBy { it.grade.toDoubleOrNull() }
                false -> dataGrades.sortByDescending { it.grade.toDoubleOrNull() }
            }
            isScoreDesc = !isScoreDesc
            showDataAfterSort()
        }
        btn_back.setOnClickListener {
            onBackPressed()
        }
        spinner_kelas.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                showData()
            }

        }
    }

    private fun getGrades() {
        val db = FirebaseFirestore.getInstance()
        clearData()
        db.collection("student_grade").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    it.forEach { data ->
                        dataGradesMaster.add(data.toObject(ScoreModel::class.java))
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
    private fun showDataAfterSort(){
        val temp= mutableListOf<ScoreModel>()
        temp.addAll(dataGrades)
        dataGrades.clear()
        tb_layout.removeAllViews()
        dataGrades.addAll(temp)
        rvAdapter.notifyDataSetChanged()
    }

    private fun showData() {
        clearData()
        dataGradesMaster.forEach {
            if(it.kelas==spinner_kelas.selectedItem.toString()) dataGrades.add(it)
        }
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
