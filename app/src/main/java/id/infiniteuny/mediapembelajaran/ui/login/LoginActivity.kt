package id.infiniteuny.mediapembelajaran.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.Pref
import id.infiniteuny.mediapembelajaran.ui.main.MainActivity
import id.infiniteuny.mediapembelajaran.ui.teacher.TeacherDashActivity
import id.infiniteuny.mediapembelajaran.utils.toastCnt
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_pass
import kotlinx.android.synthetic.main.activity_login.pg_bar

class LoginActivity : AppCompatActivity() {

    lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        fAuth = FirebaseAuth.getInstance()
        checkUser()

        btn_login.setOnClickListener {
            loginUser(et_email.text.toString().trim(), et_pass.text.toString().trim())
        }
    }

    private fun checkUser() {
        if (fAuth.currentUser != null) {
            pg_bar.visibility = View.VISIBLE
            val db = FirebaseFirestore.getInstance()
            db.collection("student").whereEqualTo("uid", fAuth.uid)
                .get().addOnSuccessListener {
                    if (it.isEmpty) {
                        toastCnt("Wellcome Teacher")
                        Pref(this).user_name="Teacher"
                        startActivity(Intent(this@LoginActivity, TeacherDashActivity::class.java))
                        this@LoginActivity.finish()
                    } else {
                        toastCnt("Wellcome Student")
                        Pref(this).user_name=it.documents[0]["name"].toString()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        this@LoginActivity.finish()
                    }
                    pg_bar.visibility = View.GONE
                }
                .addOnFailureListener {
                    toastCnt(it.localizedMessage)
                    pg_bar.visibility = View.GONE
                }
        }
    }

    private fun loginUser(email: String, password: String) {
        pg_bar.visibility = View.VISIBLE
        fAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    checkUser()
                    pg_bar.visibility = View.GONE
                } else {
                    toastCnt("Login Gagal")
                    pg_bar.visibility = View.GONE
                }
            }
    }

    private fun signUpUser(email: String, password: String) {
        pg_bar.visibility = View.VISIBLE
        fAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    checkUser()
                    pg_bar.visibility = View.GONE
                } else {
                    toastCnt("Gagal Membuat User")
                    pg_bar.visibility = View.GONE
                }
            }
    }
}
