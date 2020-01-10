package id.infiniteuny.mediapembelajaran.ui.grade

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import kotlinx.android.synthetic.main.activity_setting.btn_back
import kotlinx.android.synthetic.main.activity_student_grades.rv_grades

class StudentGradesActivity : AppCompatActivity() {

    private val gradesData = mutableListOf<ScoreModel>()
    private val rvAdapter = object : RvAdapter<Any>(gradesData) {
        override fun layoutId(position: Int, obj: Any): Int = R.layout.item_layout

        override fun viewHolder(view: View, viewType: Int): ViewHolder = GradesVH(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_grades)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()

        rv_grades.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(this@StudentGradesActivity)
        }


        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
