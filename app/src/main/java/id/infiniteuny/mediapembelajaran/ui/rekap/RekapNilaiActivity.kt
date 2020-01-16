package id.infiniteuny.mediapembelajaran.ui.rekap

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.TableRow
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import kotlinx.android.synthetic.main.activity_rekap_nilai.tb_layout

class RekapNilaiActivity : AppCompatActivity() {

    private val dataGrades= mutableListOf<ScoreModel>()
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

        getGrades()
    }

    private fun getGrades(){
        val db=FirebaseFirestore.getInstance()
        clearData()
        db.collection("student_grade").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    it.forEach {data->
                        dataGrades.add(data.toObject(ScoreModel::class.java))
                    }
                    showData()
                }
            }
            .addOnFailureListener {

            }
    }

    private fun clearData(){
        dataGrades.clear()
        tb_layout.removeAllViews()
    }

    private fun showData(){
        dataGrades.forEachIndexed{index, scoreModel ->
            val tbRow=TableRow(this)
            val lParams=TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT)
            tbRow.layoutParams=lParams
            val tvName=TextView(this)
            val tvScore=TextView(this)
            tvName.width=600
            tvScore.width=500
            tvName.textSize=15f
            tvScore.textSize=15f
            tvName.text=scoreModel.name
            tvScore.text=scoreModel.grade
            tbRow.addView(tvName)
            tbRow.addView(tvScore)
            tb_layout.addView(tbRow,index)
        }
    }

}
