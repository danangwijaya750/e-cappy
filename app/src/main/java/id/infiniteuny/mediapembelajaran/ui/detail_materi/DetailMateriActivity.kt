package id.infiniteuny.mediapembelajaran.ui.detail_materi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import id.infiniteuny.mediapembelajaran.R
import kotlinx.android.synthetic.main.activity_detail_materi.web_view

class DetailMateriActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_detail_materi)
        supportActionBar?.hide()

        web_view.loadUrl("file:///android_res/raw/materi1.html")
    }
}
