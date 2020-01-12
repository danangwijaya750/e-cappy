package id.infiniteuny.mediapembelajaran.ui.kikd

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import id.infiniteuny.mediapembelajaran.R
import kotlinx.android.synthetic.main.activity_kikd.btn_back
import kotlinx.android.synthetic.main.activity_kikd.web_view

class KikdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_kikd)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()

        loadData()

        btn_back.setOnClickListener {
            onBackPressed()
        }

    }
    private fun loadData(){
        web_view.loadUrl("file:///android_res/raw/kikd.html")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
