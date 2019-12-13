package id.infiniteuny.mediapembelajaran.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.ui.main.MainActivity
import id.infiniteuny.mediapembelajaran.utils.toastCnt

class LoginActivity : AppCompatActivity() {

    lateinit var fAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        fAuth=FirebaseAuth.getInstance()
        checkUser()
    }

    private fun checkUser(){
        if(fAuth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

    private fun loginUser(email:String, password:String){
        fAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    checkUser()
                }else{
                    toastCnt("Login Gagal")
                }
            }
    }
}
