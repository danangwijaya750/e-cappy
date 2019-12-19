package id.infiniteuny.mediapembelajaran.ui.soal

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import id.infiniteuny.mediapembelajaran.R

class SoalActivity : AppCompatActivity() {

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
    }
}
