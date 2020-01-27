package id.infiniteuny.mediapembelajaran.ui.pembahasan

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import id.infiniteuny.mediapembelajaran.R
import kotlinx.android.synthetic.main.activity_detail_materi.btn_back
import kotlinx.android.synthetic.main.activity_detail_materi.web_view
import kotlinx.android.synthetic.main.activity_quiz_result.tool_bar

class ActivityPembahasan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_pembahasan)
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
            finish()
        }
    }

    private fun loadData() {
        when (intent.getStringExtra("caller")) {
            "eval" -> {
                tool_bar.setBackgroundColor(resources.getColor(R.color.lightBlue))
                web_view.loadUrl("file:///android_res/raw/pmbhs_eval")
            }
            else -> {
                tool_bar.setBackgroundColor(resources.getColor(R.color.softYellow))
                web_view.loadUrl("file:///android_res/raw/pmbhs_train")
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
