package id.infiniteuny.mediapembelajaran.ui.quiz

import android.app.ActivityManager
import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import kotlinx.android.synthetic.main.activity_setting.btn_back

class QuizActivity : AppCompatActivity(),QuizView {

    private lateinit var presenter: QuizPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_quiz)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        presenter= QuizPresenter(FirebaseFirestore.getInstance(),this)

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    override fun onPause() {
        super.onPause()
        val activityManager = applicationContext
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        activityManager.moveTaskToFront(taskId, 0)
    }

    override fun isLoading(state: Boolean) {

    }

    override fun isError(msg: String) {

    }
}
