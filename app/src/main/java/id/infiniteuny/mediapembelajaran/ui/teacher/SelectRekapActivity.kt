package id.infiniteuny.mediapembelajaran.ui.teacher

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.ui.rekap.RekapNilaiActivity
import id.infiniteuny.mediapembelajaran.ui.rekap.RekapQuizActivity
import kotlinx.android.synthetic.main.activity_soal.*

class SelectRekapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_select_rekap)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        btn_back.setOnClickListener {
            onBackPressed()
        }
        btn_tf.setOnClickListener {
            startActivity(Intent(this, RekapQuizActivity::class.java))
        }
        btn_uji.setOnClickListener {
            startActivity(Intent(this, RekapNilaiActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
