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
import android.widget.LinearLayout
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
import id.infiniteuny.mediapembelajaran.ui.manual.ManualActivity
import id.infiniteuny.mediapembelajaran.ui.materi.MateriActivity
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizActivity
import id.infiniteuny.mediapembelajaran.ui.setting.SettingActivity
import id.infiniteuny.mediapembelajaran.ui.soal.SoalActivity
import kotlinx.android.synthetic.main.activity_main.btn_kikd
import kotlinx.android.synthetic.main.activity_main.btn_materi
import kotlinx.android.synthetic.main.activity_main.btn_nav
import kotlinx.android.synthetic.main.activity_main.btn_petunjuk
import kotlinx.android.synthetic.main.activity_main.btn_profile
import kotlinx.android.synthetic.main.activity_main.btn_soal
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.tv_hello
import kotlinx.android.synthetic.main.activity_main.tv_keluar
import kotlinx.android.synthetic.main.activity_main.tv_settings
import kotlinx.android.synthetic.main.activity_main.tv_username

class MainActivity : AppCompatActivity() {

    private lateinit var soundIntent:Intent

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
        initDrawer()
        tv_username.text= Pref(this).user_name
        soundIntent=Intent(this,SoundService::class.java)
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
        tv_settings.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        tv_keluar.setOnClickListener {
            val fAuth=FirebaseAuth.getInstance()
            if(fAuth.currentUser!=null){
                fAuth.signOut()
                Pref(this).user_name=""
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }
        btn_nav.setOnClickListener {
            when(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                true->{
                    drawerLayout.closeDrawers()
                }else->{
                    drawerLayout.openDrawer(Gravity.LEFT)
                }
            }
        }
        btn_profile.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val v = layoutInflater.inflate(R.layout.layout_profile, null)
            val tv = v.findViewById<TextView>(R.id.tv_username)
            val tv2=v.findViewById<TextView>(R.id.tv_email)
            tv.text = Pref(this).user_name
            tv2.text=FirebaseAuth.getInstance().currentUser!!.email

            builder.apply {
                setTitle("")
                setView(v)
            }
            builder.create().show()
        }
        serviceControl()


    }

    private fun initDrawer(){
        val drawerToggle= ActionBarDrawerToggle(this,drawerLayout, string.app_name, string.app_name)
        drawerLayout.apply {
            addDrawerListener(drawerToggle)
        }
        drawerToggle.syncState()
        drawerLayout.addDrawerListener(object: DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {

            }

            override fun onDrawerOpened(drawerView: View) {
            }
        })

    }

    private fun serviceControl(){
        when(Pref(this).sound_setting){
            true->{
                startService(soundIntent)
            }
            false->{
                stopService(soundIntent)
            }
        }
        Pref(this).sound_setting=!Pref(this).sound_setting
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
