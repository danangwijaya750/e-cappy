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
import id.infiniteuny.mediapembelajaran.ui.teacher.TeacherDashActivity
import id.infiniteuny.mediapembelajaran.utils.logE
import id.infiniteuny.mediapembelajaran.utils.toastCnt
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btn_change
import kotlinx.android.synthetic.main.activity_login.btn_login
import kotlinx.android.synthetic.main.activity_login.cntr
import kotlinx.android.synthetic.main.activity_login.et_email
import kotlinx.android.synthetic.main.activity_login.et_name
import kotlinx.android.synthetic.main.activity_login.et_pass
import kotlinx.android.synthetic.main.activity_login.iv_icon
import kotlinx.android.synthetic.main.activity_login.pg_bar
import kotlinx.android.synthetic.main.activity_teacher_login.*

class TeacherLoginActivity : AppCompatActivity() {
    private var createUser = false
    lateinit var fAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_teacher_login)
        supportActionBar?.hide()
        fAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            if (et_email.text.toString().isNotEmpty() && et_pass.text.toString().isNotEmpty()
                && et_email.text.toString().isNotBlank() && et_pass.text.toString().isNotBlank()
            ) {
                when (createUser) {
                    true -> signUpUser(et_email.text.toString().trim(), et_pass.text.toString().trim())
                    else -> loginUser(et_email.text.toString().trim(), et_pass.text.toString().trim())
                }
            }

        }
        btn_change.setOnClickListener {
            createUser = !createUser
            changeView()
        }
    }

    private fun changeView() {
        when (createUser) {
            true -> {
                cntr.visibility=View.GONE
                iv_icon.setImageResource(R.drawable.ic_sign_up_guru)
                et_name.visibility = View.VISIBLE
                btn_login.text = "Daftar"
                btn_change.text = "Masuk"
            }
            false -> {
                cntr.visibility= View.VISIBLE
                iv_icon.setImageResource(R.drawable.ic_selamat_datang)
                et_name.visibility = View.GONE
                btn_login.text = "Masuk"
                btn_change.text = "Daftar"
            }
        }
    }

    private fun checkUser() {
        if (fAuth.currentUser != null) {
            pg_bar.visibility = View.VISIBLE
                teacherCheck()
        } else {
            btn_login.isClickable=true
            pg_bar.visibility = View.GONE
            toastCnt("Silahkan Login")
        }
    }
    private   fun  teacherCheck(){
        val db=FirebaseFirestore.getInstance()
        db.collection("teacher").whereEqualTo("uid",fAuth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {
                btn_login.isClickable=true
                toastCnt("Welcome Teacher")
                Pref(this).user_name = it.documents[0]["name"].toString()
                pg_bar.visibility = View.GONE
                startActivity(Intent(this@TeacherLoginActivity,TeacherDashActivity::class.java))
                this@TeacherLoginActivity.finish()
            }
            .addOnFailureListener {
                logE(it.message)
            }


    }
    private fun loginUser(email: String, password: String) {
        pg_bar.visibility = View.VISIBLE
        fAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    checkUser()
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
                    addToSystem(it.result!!.user!!.uid)
                } else {
                    toastCnt("Gagal Membuat User")
                    pg_bar.visibility = View.GONE
                }
            }
    }

    private fun addToSystem(uid: String) {
        val db = FirebaseFirestore.getInstance()
        val data = hashMapOf<String, String>(
            "name" to et_name.text.toString().trim(),
            "uid" to uid
        )
        db.collection("teacher").add(data)
            .addOnSuccessListener {
                toastCnt("Berhasil Membuat User")
                checkUser()
            }
            .addOnFailureListener {
                toastCnt("Gagal Membuat User")
                pg_bar.visibility = View.GONE
                logE(it.message)
            }
    }
}
