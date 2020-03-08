package id.infiniteuny.mediapembelajaran.ui.setting

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.Pref
import id.infiniteuny.mediapembelajaran.service.SoundService
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_setting)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        changeView()
        btn_back.setOnClickListener {
            onBackPressed()
        }

        iv_musik.setOnClickListener {
            Pref(this).sound_setting=!Pref(this).sound_setting
            soundControl()
            changeView()
        }
    }
    private fun soundControl(){
        when(Pref(this).sound_setting){
            true->startSound()
            false->stopSound()
        }
    }
    private fun startSound(){
        val intent= Intent(applicationContext, SoundService::class.java)
        intent.addCategory(SoundService.SERVICE_TAG)
        startService(intent)
    }
    private fun stopSound(){
        val intent= Intent(applicationContext, SoundService::class.java)
        intent.addCategory(SoundService.SERVICE_TAG)
        stopService(intent)
    }

    private fun changeView(){
        when(Pref(this).sound_setting){
            true->iv_musik.setImageResource(R.drawable.unmute)
            false->iv_musik.setImageResource(R.drawable.mute)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
