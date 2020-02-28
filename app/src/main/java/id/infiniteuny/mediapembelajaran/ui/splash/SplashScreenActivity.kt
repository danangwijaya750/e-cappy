package id.infiniteuny.mediapembelajaran.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.ui.login.LoginActivity
import id.infiniteuny.mediapembelajaran.ui.login.SelectAccountActivity

class SplashScreenActivity : AppCompatActivity() {
    var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        handler = Handler()
        handler!!.postDelayed({
            startActivity(Intent(this, SelectAccountActivity::class.java))
            //startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler = null
    }
}
