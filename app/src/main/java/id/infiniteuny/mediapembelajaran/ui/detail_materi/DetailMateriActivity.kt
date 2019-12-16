package id.infiniteuny.mediapembelajaran.ui.detail_materi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import id.infiniteuny.mediapembelajaran.R

class DetailMateriActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_detail_materi)
        supportActionBar?.hide()

    }
}
