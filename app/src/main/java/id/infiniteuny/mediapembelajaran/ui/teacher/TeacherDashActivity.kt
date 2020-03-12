package id.infiniteuny.mediapembelajaran.ui.teacher

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
import id.infiniteuny.mediapembelajaran.ui.login.LoginActivity
import id.infiniteuny.mediapembelajaran.ui.login.SelectAccountActivity
import id.infiniteuny.mediapembelajaran.ui.manual.ManualActivity
import id.infiniteuny.mediapembelajaran.ui.rekap.RekapNilaiActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_petunjuk
import kotlinx.android.synthetic.main.activity_main.btn_profile
import kotlinx.android.synthetic.main.activity_main.tv_username
import kotlinx.android.synthetic.main.activity_teacher_dash.*
import kotlinx.android.synthetic.main.activity_teacher_dash.btn_materi

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
        tv_username.text = Pref(this).user_name

        btn_materi.setOnClickListener {
            startActivity(Intent(this, RekapNilaiActivity::class.java))
        }
        btn_petunjuk.setOnClickListener {
            startActivity(Intent(this,ManualActivity::class.java))
        }

        btn_profile.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val v = layoutInflater.inflate(R.layout.layout_profile, null)
            val tv = v.findViewById<TextView>(R.id.tv_username)
            val tv2 = v.findViewById<TextView>(R.id.tv_email)
            val iv=v.findViewById<ImageView>(R.id.iv_profile)
            iv.setImageResource(R.drawable.ic_guru_profile)
            val iv_lo=v.findViewById<ImageView>(R.id.btn_logout)
            tv.text = Pref(this).user_name
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
                startActivity(Intent(this,SelectAccountActivity::class.java))
                this@TeacherDashActivity.finish()
            }

        }

    }

}
