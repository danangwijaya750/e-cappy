package id.infiniteuny.mediapembelajaran.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.Pref
import id.infiniteuny.mediapembelajaran.ui.main.MainActivity
import id.infiniteuny.mediapembelajaran.ui.teacher.TeacherDashActivity
import id.infiniteuny.mediapembelajaran.utils.toastCnt
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.pg_bar
import kotlinx.android.synthetic.main.activity_select_account.iv_next
import kotlinx.android.synthetic.main.activity_select_account.rb_student
import kotlinx.android.synthetic.main.activity_select_account.rb_teacher

class SelectAccountActivity : AppCompatActivity() {
    private  var role=""
    lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_select_account)
        supportActionBar?.hide()
        fAuth=FirebaseAuth.getInstance()
        checkUser()

        rb_student.setOnClickListener{
            role="siswa"
        }
        rb_teacher.setOnClickListener {
            role="guru"
        }
        iv_next.setOnClickListener {
          when(role){
              "siswa"->{
                  val intent=Intent(this,LoginActivity::class.java)
                  intent.putExtra("role",role)
                  startActivity(intent)
                  finish()
              }
              "guru"->{
                  val intent=Intent(this,TeacherLoginActivity::class.java)
                  intent.putExtra("role",role)
                  startActivity(intent)
                  finish()
              }
          }
//            toastCnt(role)
        }
    }

    private fun checkUser() {
        if (fAuth.currentUser != null) {
            pg_bar.visibility = View.VISIBLE
            val db = FirebaseFirestore.getInstance()
            db.collection("student").whereEqualTo("uid", fAuth.uid)
                .get().addOnSuccessListener {
                    if (it.isEmpty) {
                        iv_next.isClickable=true
                        toastCnt("Wellcome Teacher")
                        Pref(this).user_name = "Teacher"
                        pg_bar.visibility = View.GONE
                        startActivity(Intent(this, TeacherDashActivity::class.java))
                        this.finish()
                    } else {
                        iv_next.isClickable=true
                        toastCnt("Wellcome Student")
                        Pref(this).user_name = it.documents[0]["name"].toString()
                        pg_bar.visibility = View.GONE
                        startActivity(Intent(this, MainActivity::class.java))
                        this.finish()
                    }
                }
                .addOnFailureListener {
                    toastCnt(it.localizedMessage)
                    pg_bar.visibility = View.GONE
                    iv_next.isClickable=true
                }
        } else {
            iv_next.isClickable=true
            pg_bar.visibility = View.GONE
            toastCnt("Pilih Peranmu!")
        }
    }
}
