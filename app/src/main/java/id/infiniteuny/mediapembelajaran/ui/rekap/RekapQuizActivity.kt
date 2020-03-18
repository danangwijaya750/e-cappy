package id.infiniteuny.mediapembelajaran.ui.rekap

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.base.RvAdapterUnit
import id.infiniteuny.mediapembelajaran.data.QuizModel
import id.infiniteuny.mediapembelajaran.data.Quizes
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import id.infiniteuny.mediapembelajaran.utils.logD
import id.infiniteuny.mediapembelajaran.utils.logE
import kotlinx.android.synthetic.main.activity_rekap_quiz.*
import kotlinx.android.synthetic.main.activity_rekap_quiz.iv_question
import kotlinx.android.synthetic.main.activity_rekap_quiz.tv_question
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class RekapQuizActivity : AppCompatActivity() {

    lateinit var db:FirebaseFirestore
    private val dataSoal= mutableListOf<QuizModel>()
    private val rvAdapter=RvAdapterUnit(dataSoal){
        showData(it)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun showData(data:QuizModel){
        tv_soal.text="Soal ${data.id}"
        tv_question.text = data.question.replace("\\n", "\n", false)
        if(data.img.isNotEmpty()){
            Glide.with(this).load(
                    Uri
                    .parse("android.resource://id.infiniteuny.mediapembelajaran/raw/a${data.img}"))
                .into(iv_question)
        }
        if(data.pembahas.isNotEmpty()){
            Glide.with(this).load(Uri
                    .parse("android.resource://id.infiniteuny.mediapembelajaran/raw/a${data.pembahas}"))
                .into(iv_pembahasan)
        }
        db.collection("jawaban")
            .document(data.id.toString())
            .get().addOnSuccessListener {
                jawab_true.text=" = ${it["jawab_true"] as Long}"
                jawab_false.text=" = ${it["jawab_false"] as Long}"

            }
            .addOnFailureListener {
                logE(it.message)
            }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekap_quiz)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        db= FirebaseFirestore.getInstance()
        val layManager=LinearLayoutManager(this)
        layManager.orientation=LinearLayoutManager.HORIZONTAL
        rv_pos.apply {
            layoutManager=layManager
            adapter=rvAdapter
        }
        btn_back.setOnClickListener {
            onBackPressed()
        }

        readData()
        showData(dataSoal[0])

    }

    private fun readData(){
        dataSoal.clear()
        val inStream = this.resources.openRawResource(R.raw.quiz)
        val bufferedReader = BufferedReader(InputStreamReader(inStream))
        val read = bufferedReader.use { it.readText() }
        val data = Gson().fromJson(read, Quizes::class.java)
        bufferedReader.close()
        dataSoal.addAll(data.data)
        dataSoal.sortBy { it.id }
        rvAdapter.notifyDataSetChanged()
        logD("${dataSoal.size}")
    }
}
