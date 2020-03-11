package id.infiniteuny.mediapembelajaran.ui.main

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.R.string
import id.infiniteuny.mediapembelajaran.data.Pref
import id.infiniteuny.mediapembelajaran.service.SoundService
import id.infiniteuny.mediapembelajaran.ui.kikd.KikdActivity
import id.infiniteuny.mediapembelajaran.ui.login.LoginActivity
import id.infiniteuny.mediapembelajaran.ui.login.SelectAccountActivity
import id.infiniteuny.mediapembelajaran.ui.manual.ManualActivity
import id.infiniteuny.mediapembelajaran.ui.materi.MateriActivity
import id.infiniteuny.mediapembelajaran.ui.setting.SettingActivity
import id.infiniteuny.mediapembelajaran.ui.soal.SoalActivity
import id.infiniteuny.mediapembelajaran.ui.tentang.TentangActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var soundIntent: Intent

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_main)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        tv_username.text = Pref(this).user_name
        when(Pref(this).jk){
            "laki"->btn_profile.setImageResource(R.drawable.ic_male)
            else->btn_profile.setImageResource(R.drawable.ic_female)
        }
        soundIntent = Intent(this, SoundService::class.java)
        btn_kikd.setOnClickListener {
            startActivity(Intent(this, KikdActivity::class.java))
        }
        btn_soal.setOnClickListener {
            startActivity(Intent(this, SoalActivity::class.java))
        }
        btn_materi.setOnClickListener {
            startActivity(Intent(this, MateriActivity::class.java))
        }
        btn_petunjuk.setOnClickListener {
            startActivity(Intent(this, ManualActivity::class.java))
        }
        btn_pegaturan.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        btn_tentang.setOnClickListener {
            startActivity(Intent(this, TentangActivity::class.java))
        }
        btn_profile.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val v = layoutInflater.inflate(R.layout.layout_profile, null)
            val tv = v.findViewById<TextView>(R.id.tv_username)
            val tv2 = v.findViewById<TextView>(R.id.tv_email)
            val iv=v.findViewById<ImageView>(R.id.iv_profile)
            val iv_lo=v.findViewById<ImageView>(R.id.btn_logout)
            tv.text = Pref(this).user_name
            when(Pref(this).jk){
                "laki"->iv.setImageResource(R.drawable.ic_pilih_laki)
                else->iv.setImageResource(R.drawable.ic_pilih_perempuan)
            }
            tv2.text = FirebaseAuth.getInstance().currentUser!!.email
            builder.apply {
                setTitle("")
                setView(v)
            }
            val alert = builder.create()
            alert.show()
            iv_lo.setOnClickListener {
                val fAuth=FirebaseAuth.getInstance()
                fAuth.signOut()
                alert.dismiss()
                startActivity(Intent(this, SelectAccountActivity::class.java))
                this.finish()
            }
        }
//        iv_sound.setOnClickListener {
//            serviceControl()
//            setIvSound()
//        }
        serviceControl()
    }


     fun serviceControl() {
        when (Pref(this).sound_setting) {
            true -> {
                soundIntent.addCategory(SoundService.SERVICE_TAG)
                startService(soundIntent)
            }
            false -> {
                soundIntent.addCategory(SoundService.SERVICE_TAG)
                stopService(soundIntent)
            }
        }
//        Pref(this).sound_setting = !Pref(this).sound_setting
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {

                return true
            }
        }
        return false
    }
}
