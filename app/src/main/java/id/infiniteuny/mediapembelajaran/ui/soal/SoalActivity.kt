package id.infiniteuny.mediapembelajaran.ui.soal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizActivity
import kotlinx.android.synthetic.main.activity_soal.btn_back
import kotlinx.android.synthetic.main.activity_soal.btn_latihan
import kotlinx.android.synthetic.main.activity_soal.btn_uji

class SoalActivity : AppCompatActivity() {

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_soal)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_latihan.setOnClickListener {
            startQuiz("train")
        }

        btn_uji.setOnClickListener {
            startQuiz("eval")
        }
    }

    private fun startQuiz(type: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
