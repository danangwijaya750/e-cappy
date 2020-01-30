package id.infiniteuny.mediapembelajaran.ui.teacher

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
import com.google.firebase.auth.FirebaseAuth
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.R.string
import id.infiniteuny.mediapembelajaran.data.Pref
import id.infiniteuny.mediapembelajaran.ui.login.LoginActivity
import id.infiniteuny.mediapembelajaran.ui.rekap.RekapNilaiActivity
import kotlinx.android.synthetic.main.activity_main.tv_username
import kotlinx.android.synthetic.main.activity_teacher_dash.btn_materi
import kotlinx.android.synthetic.main.activity_teacher_dash.btn_nav
import kotlinx.android.synthetic.main.activity_teacher_dash.drawerLayout
import kotlinx.android.synthetic.main.activity_teacher_dash.tv_keluar

class TeacherDashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_teacher_dash)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        initDrawer()
        tv_username.text = Pref(this).user_name

        btn_materi.setOnClickListener {
            startActivity(Intent(this, RekapNilaiActivity::class.java))
        }
        tv_keluar.setOnClickListener {
            val fAuth = FirebaseAuth.getInstance()
            if (fAuth.currentUser != null) {
                fAuth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }
        btn_nav.setOnClickListener {
            when (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                true -> {
                    drawerLayout.closeDrawers()
                }
                else -> {
                    drawerLayout.openDrawer(Gravity.LEFT)
                }
            }
        }
    }

    private fun initDrawer() {
        val drawerToggle = ActionBarDrawerToggle(this, drawerLayout, string.app_name, string.app_name)
        drawerLayout.apply {
            addDrawerListener(drawerToggle)
        }
        drawerToggle.syncState()
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
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
