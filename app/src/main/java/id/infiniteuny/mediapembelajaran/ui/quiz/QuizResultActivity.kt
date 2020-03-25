package id.infiniteuny.mediapembelajaran.ui.quiz

import android.content.Intent
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.ui.pembahasan.ActivityPembahasan
import id.infiniteuny.mediapembelajaran.utils.logD
import kotlinx.android.synthetic.main.activity_quiz_result.*

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
        if (intent.getStringExtra("caller") == "eval") {
            root_lay.setBackgroundColor(resources.getColor(R.color.lightBlue))
            iv_type.setImageResource(R.drawable.ic_ujikemampuan_otak)
            iv_fireworks.setImageResource(R.drawable.ic_fireworks_ujikemampuan)
        }else if(intent.getStringExtra("caller") == "quizes"){
            root_lay.setBackgroundColor(resources.getColor(R.color.purple))
            iv_type.setImageResource(R.drawable.ic_bg_pialaa)
            btn_to_bahas.visibility= View.INVISIBLE
            iv_fireworks.setImageResource(R.drawable.ic_hasil_nilai_fireworks)
        }
        if(intent.getStringExtra("caller") == "quizes") {
            setChartForQuiz(intent.getIntExtra("score", 0))
        }else{
            setChartValue(intent.getIntExtra("score", 0))
        }


        btn_back.setOnClickListener {
            onBackPressed()
        }
        btn_to_home.setOnClickListener {
            onBackPressed()
        }
        btn_to_bahas.setOnClickListener {
            val caller = intent.getStringExtra("caller")
            val intent = Intent(this, ActivityPembahasan::class.java)
            intent.putExtra("caller", caller)
            startActivity(intent)
            finish()
        }
    }

    private fun setChartValue(data: Int) {
        logD(data.toString())
        pieChart.setUsePercentValues(true)
        val xvals = mutableListOf<PieEntry>()
        xvals.add(PieEntry(100f - data.toFloat(), "Score Losses"))
        xvals.add(PieEntry(data.toFloat(), "Your Score"))
        val dataSet = PieDataSet(xvals, "")
        dataSet.colors = listOf(Color.RED, Color.GREEN)
        val pieData = PieData(dataSet)
        pieChart.description.text = ""
        pieData.setValueFormatter(PercentFormatter())
        pieChart.data = pieData
        pieChart.isDrawHoleEnabled = true
        pieChart.setDrawEntryLabels(false)
        pieChart.data.setValueTextColor(Color.WHITE)
        pieChart.data.setValueTextSize(15f)

        logD(intent.getIntExtra("true_ans",0).toString())
        logD(intent.getIntExtra("false_ans",0).toString())
        tv_score.text=data.toString()
        tv_tr_ans.text=intent.getIntExtra("true_ans",0).toString()
        tv_fl_ans.text=intent.getIntExtra("false_ans",0).toString()
    }
    private fun setChartForQuiz(data: Int){
        logD(data.toString())
        pieChart.setUsePercentValues(true)
        val xvals = mutableListOf<PieEntry>()
        xvals.add(PieEntry(10f - data.toFloat(), "Score Losses"))
        xvals.add(PieEntry(data.toFloat(), "Your Score"))
        val dataSet = PieDataSet(xvals, "")
        dataSet.colors = listOf(Color.RED, Color.GREEN)

        val pieData = PieData(dataSet)
        pieChart.description.text = ""
        pieData.setValueFormatter(PercentFormatter())
        pieChart.data = pieData
        pieChart.isDrawHoleEnabled = true
        pieChart.setDrawEntryLabels(false)
        pieChart.data.setValueTextColor(Color.WHITE)
        pieChart.data.setValueTextSize(15f)
        logD(intent.getIntExtra("true_ans",0).toString())
        logD(intent.getIntExtra("false_ans",0).toString())
        tv_score.text=data.toString()
        tv_tr_ans.text=intent.getIntExtra("true_ans",0).toString()
        tv_fl_ans.text=intent.getIntExtra("false_ans",0).toString()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
