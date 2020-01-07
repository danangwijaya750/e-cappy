package id.infiniteuny.mediapembelajaran.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.R.string
import id.infiniteuny.mediapembelajaran.ui.manual.ManualActivity
import id.infiniteuny.mediapembelajaran.ui.materi.MateriActivity
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizActivity
import id.infiniteuny.mediapembelajaran.ui.setting.SettingActivity
import id.infiniteuny.mediapembelajaran.ui.soal.SoalActivity
import kotlinx.android.synthetic.main.activity_main.btn_kikd
import kotlinx.android.synthetic.main.activity_main.btn_materi
import kotlinx.android.synthetic.main.activity_main.btn_nav
import kotlinx.android.synthetic.main.activity_main.btn_petunjuk
import kotlinx.android.synthetic.main.activity_main.btn_soal
import kotlinx.android.synthetic.main.activity_main.drawerLayout
import kotlinx.android.synthetic.main.activity_main.tv_keluar
import kotlinx.android.synthetic.main.activity_main.tv_settings

class MainActivity : AppCompatActivity() {

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
        btn_kikd.setOnClickListener {
            //startActivity(Intent(this, SettingActivity::class.java))
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
            finish()
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


}
