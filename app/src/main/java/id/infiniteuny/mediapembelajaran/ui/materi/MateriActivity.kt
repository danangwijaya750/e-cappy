package id.infiniteuny.mediapembelajaran.ui.materi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import id.infiniteuny.mediapembelajaran.R
import kotlinx.android.synthetic.main.activity_setting.btn_back

class MateriActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_materi)
        supportActionBar?.hide()

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
