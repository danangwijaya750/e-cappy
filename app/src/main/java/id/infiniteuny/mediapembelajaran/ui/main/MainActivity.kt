package id.infiniteuny.mediapembelajaran.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import id.infiniteuny.mediapembelajaran.R.layout
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
        setContentView(layout.activity_main)
        supportActionBar?.hide()

        btn_pengaturan.setOnClickListener {
            startActivity(Intent(this,SettingActivity::class.java))
        }
        btn_soal.setOnClickListener {
            startActivity(Intent(this,QuizActivity::class.java))
        }
        btn_materi.setOnClickListener {
            startActivity(Intent(this,MateriActivity::class.java))
        }
        btn_petunjuk.setOnClickListener {
            startActivity(Intent(this,ManualActivity::class.java))
        }
    }
}
