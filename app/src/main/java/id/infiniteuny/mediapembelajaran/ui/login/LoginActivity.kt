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
import id.infiniteuny.mediapembelajaran.utils.logE
import id.infiniteuny.mediapembelajaran.utils.toastCnt
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var createUser = false
    lateinit var fAuth: FirebaseAuth
    private var gender="perempuan"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        fAuth = FirebaseAuth.getInstance()
//        btn_login.isClickable=false
        if(intent.getStringExtra("role")=="siswa"){
            btn_change.visibility=View.VISIBLE
            tv_atau.visibility=View.VISIBLE
        }else{
            btn_change.visibility=View.GONE
            tv_atau.visibility=View.GONE
        }

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
        rb_student_female.setOnClickListener {
            gender="perempuan"
        }
        rb_student_male.setOnClickListener {
            gender="laki"
        }
        btn_change.setOnClickListener {
            createUser = !createUser
            changeView()
        }
    }

    private fun changeView() {
        when (createUser) {
            true -> {
                et_name.visibility = View.VISIBLE
                header_daftar.visibility=View.VISIBLE
                header_login.visibility=View.GONE
                cntr.visibility=View.VISIBLE
                layout_kelas.visibility=View.VISIBLE
                iv_icon.visibility=View.INVISIBLE
                btn_login.text = "Daftar"
                btn_change.text = "Masuk"
            }
            false -> {
                header_daftar.visibility=View.GONE
                header_login.visibility=View.VISIBLE
                cntr.visibility=View.GONE
                iv_icon.visibility=View.VISIBLE
                et_name.visibility = View.GONE
                layout_kelas.visibility=View.GONE
                btn_login.text = "Masuk"
                btn_change.text = "Daftar"
            }
        }
    }

    private fun checkUser() {
        if (fAuth.currentUser != null) {
            pg_bar.visibility = View.VISIBLE
            if(intent.getStringExtra("role")=="siswa"){
                studentCheck()
            }else{
                teacherCheck()
            }
        } else {
            btn_login.isClickable=true
            pg_bar.visibility = View.GONE
            toastCnt("Silahkan Login")
        }
    }
    private   fun  teacherCheck(){
        startActivity(Intent(this@LoginActivity, TeacherDashActivity::class.java))
        this@LoginActivity.finish()
    }
    private fun studentCheck(){
        val db = FirebaseFirestore.getInstance()
        db.collection("student").whereEqualTo("uid", fAuth.uid)
            .get().addOnSuccessListener {
                btn_login.isClickable=true
                if(it.isEmpty){
                    toastCnt("Username atau Password salah")
                    fAuth.signOut()
                    pg_bar.visibility = View.GONE
                }else {
                    toastCnt("Welcome Student")
                    Pref(this).user_name = it.documents[0]["name"].toString()
                    Pref(this).jk = it.documents[0]["jk"].toString()
                    Pref(this).kls = it.documents[0]["kelas"].toString()
                    pg_bar.visibility = View.GONE
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    this@LoginActivity.finish()
                }
            }
            .addOnFailureListener {
                toastCnt(it.localizedMessage)
                pg_bar.visibility = View.GONE
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
//                    val jk= when(rb_student_female.isSelected){
//                        true->"perempuan"
//                        else->"laki"
//                    }
                    addToSystem(it.result!!.user!!.uid,spinner_kelas.selectedItem.toString(),gender)
                } else {
                    toastCnt("Gagal Membuat User")
                    pg_bar.visibility = View.GONE
                }
            }
    }

    private fun addToSystem(uid: String,kelas:String,jk:String) {
        val db = FirebaseFirestore.getInstance()
        val data = hashMapOf<String, String>(
            "name" to et_name.text.toString().trim(),
            "uid" to uid,
            "kelas" to kelas,
            "jk" to jk
        )
        db.collection("student").add(data)
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
