package id.infiniteuny.mediapembelajaran.ui.main

import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.ui.manual.ManualActivity
import id.infiniteuny.mediapembelajaran.ui.materi.MateriActivity
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizActivity
import id.infiniteuny.mediapembelajaran.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.btn_materi
import kotlinx.android.synthetic.main.activity_main.btn_pengaturan
import kotlinx.android.synthetic.main.activity_main.btn_petunjuk
import kotlinx.android.synthetic.main.activity_main.btn_soal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_main)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()

        btn_pengaturan.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        btn_soal.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
        btn_materi.setOnClickListener {
            startActivity(Intent(this, MateriActivity::class.java))
        }
        btn_petunjuk.setOnClickListener {
            startActivity(Intent(this, ManualActivity::class.java))
        }
    }
}
