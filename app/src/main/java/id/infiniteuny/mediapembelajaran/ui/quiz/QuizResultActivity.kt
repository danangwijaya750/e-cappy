package id.infiniteuny.mediapembelajaran.ui.quiz

import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.utils.logD
import kotlinx.android.synthetic.main.activity_quiz_result.btn_back
import kotlinx.android.synthetic.main.activity_quiz_result.pieChart

class QuizResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_quiz_result)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()

        setChartValue(intent.getIntExtra("score",0))

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setChartValue(data:Int){
        logD(data.toString())
        pieChart.setUsePercentValues(true)
        val xvals=  mutableListOf<PieEntry>()
        xvals.add(PieEntry(100f - data.toFloat(),"Score Losses"))
        xvals.add(PieEntry(data.toFloat(),"Your Score"))
        val dataSet=PieDataSet(xvals,"")
        dataSet.colors = listOf(Color.RED,Color.GREEN)

        val pieData= PieData(dataSet)
        pieChart.description.text = ""
        pieData.setValueFormatter(PercentFormatter())
        pieChart.data=pieData
        pieChart.isDrawHoleEnabled = true
        pieChart.setDrawEntryLabels(false)
        pieChart.data.setValueTextColor(Color.WHITE)
        pieChart.data.setValueTextSize(15f)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}