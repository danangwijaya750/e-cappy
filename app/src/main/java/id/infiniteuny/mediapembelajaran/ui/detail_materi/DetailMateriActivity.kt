package id.infiniteuny.mediapembelajaran.ui.detail_materi

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.MaterialModel
import kotlinx.android.synthetic.main.activity_detail_materi.btn_back
import kotlinx.android.synthetic.main.activity_detail_materi.tv_title
import kotlinx.android.synthetic.main.activity_detail_materi.web_view

class DetailMateriActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_detail_materi)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        val data = intent.getParcelableExtra<MaterialModel.Data>("data")
        tv_title.text = data.title
        web_view.loadUrl("file:///android_res/raw/${data.file}")
        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
