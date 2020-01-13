package id.infiniteuny.mediapembelajaran.ui.soal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginStart
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizActivity
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizOnlineActivity
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
            val intent =Intent(this@SoalActivity,QuizOnlineActivity::class.java)
            intent.putExtra("key","Key1-Generated")
            startActivity(intent)
//            val input=EditText(this)
//            val builder=AlertDialog.Builder(this)
//            builder.apply {
//                setTitle("Masukan Key Quiz Anda : ")
//                setView(input)
//                setPositiveButton("Ok"){ _, _ ->
//                    val intent =Intent(this@SoalActivity,QuizOnlineActivity::class.java)
//                    intent.putExtra("key",input.text.toString())
//                    startActivity(intent)
//                }
//                setNegativeButton("Batal"){ _,_->
//
//                }
//            }
//            builder.create().show()

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
