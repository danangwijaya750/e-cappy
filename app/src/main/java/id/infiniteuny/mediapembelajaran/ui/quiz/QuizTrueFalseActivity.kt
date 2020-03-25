package id.infiniteuny.mediapembelajaran.ui.quiz

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.*
import id.infiniteuny.mediapembelajaran.utils.logD
import id.infiniteuny.mediapembelajaran.utils.logE
import id.infiniteuny.mediapembelajaran.utils.toastCnt
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.activity_quiz_true_false.*
import kotlinx.android.synthetic.main.activity_quiz_true_false.btn_back
import kotlinx.android.synthetic.main.activity_quiz_true_false.btn_submit
import kotlinx.android.synthetic.main.activity_quiz_true_false.pg_bar
import kotlinx.android.synthetic.main.activity_quiz_true_false.progressbar
import kotlinx.android.synthetic.main.activity_quiz_true_false.questionCount
import kotlinx.android.synthetic.main.activity_quiz_true_false.rv_soal_pos
import kotlinx.android.synthetic.main.activity_quiz_true_false.timeCounter
import kotlinx.android.synthetic.main.activity_quiz_true_false.tv_question
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*
import java.util.Collections.shuffle

class QuizTrueFalseActivity : AppCompatActivity() {
    private val quizQuestion = mutableListOf<QuizModel>()
    private var answerData = ""
    private var questionPos = 0
    private var score = 0

    private var colorStateList: ColorStateList? = null
    private var colorStateListCountDown: ColorStateList? = null
    private var countDownTimer: CountDownTimer? = null

    private var timeLeft: Long = 0

    private var qCounter: Int = 0
    private var qCountTotal: Int = 0

    private var ans: Boolean = false

    private var onBackPressedTime: Long = 0

    private var pgBarControl = 1
    private var keyQuiz = ""
    private lateinit var db:FirebaseFirestore

    private val dataQuizPosition= mutableListOf<Int>()
    private var rvAdapter=object : RvAdapter<Any>(dataQuizPosition){
        override fun layoutId(position: Int, obj: Any): Int =R.layout.item_circle

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =SoalPositionVH(view)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_quiz_true_false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        db= FirebaseFirestore.getInstance()
        progressbar.progress = pgBarControl
        val layMan= LinearLayoutManager(this)
        layMan.orientation= LinearLayoutManager.HORIZONTAL
        rv_soal_pos.apply {
            adapter=rvAdapter
            layoutManager=layMan
        }

        colorStateListCountDown = timeCounter!!.textColors
        timeLeft = 180000

        getTrainQuestions()
        btn_true.setOnClickListener {
            answerData = "true"
            toastCnt("Anda Memilih $answerData")
        }
        btn_false.setOnClickListener {
            answerData = "false"
            toastCnt("Anda Memilih $answerData")
        }

        btn_submit.setOnClickListener {
            if (answerData.isNotEmpty()) {
                checkAnswer()
            } else {
                toastCnt("Pilih 1 Jawaban Dahulu")
            }
        }
        btn_back.setOnClickListener { 
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        countDownTimer!!.cancel()
        finish()
    }

    private fun checkAnswer() {
        if (questionPos <= quizQuestion.size - 1) {
            pg_bar.visibility=View.VISIBLE
            logD(quizQuestion[questionPos].key)
            logD(answerData)
            if (answerData == quizQuestion[questionPos].key) {
//                toastCnt("jawaban benar")
//                logD("here call")
                ans=true
                countDownTimer!!.cancel()
                score++
            } else {
//                toastCnt("jawaban salah")
                ans=false
                countDownTimer!!.cancel()
            }
            if(answerData=="true"){
                db.collection("jawaban").
                    document(quizQuestion[questionPos].id.toString())
                    .update("jawab_true",FieldValue.increment(1))
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            showRightAns()
                        }else{
                            logE(it.exception.toString())
                        }
                    }
            }else if(answerData=="false"){
                db.collection("jawaban").
                    document(quizQuestion[questionPos].id.toString())
                    .update("jawab_false",FieldValue.increment(1))
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            showRightAns()
                        }else{
                            logE(it.exception.toString())
                        }
                    }
            }else{
                showRightAns()
            }
        }
    }
    private fun afterCheck(){
        answerData=""
        questionPos++
        if (questionPos <= quizQuestion.size - 1) {
            showQuestion()
            QuizPosHelper.quizPos=questionPos+1
            rvAdapter.notifyDataSetChanged()
            rv_soal_pos.layoutManager!!.scrollToPosition(QuizPosHelper.quizPos-1)
        } else {
            inLastQuestion()
        }
    }

    private fun showRightAns(){
        val builder = AlertDialog.Builder(this)
        val v = layoutInflater.inflate(R.layout.layout_true_answer, null)
        val tvTrue=v.findViewById<TextView>(R.id.jawab_true)
        val tvFalse=v.findViewById<TextView>(R.id.jawab_false)
        val btnNext=v.findViewById<Button>(R.id.btn_next)
        val tvState=v.findViewById<TextView>(R.id.tv_state)
        val tvState2=v.findViewById<TextView>(R.id.tv_state2)
        val iv_state=v.findViewById<ImageView>(R.id.iv_jawab)
        val iv_pembahas=v.findViewById<ImageView>(R.id.iv_pembahasan)
        if(quizQuestion[questionPos].pembahas.isNotEmpty()){
            Glide.with(this).load(Uri
                    .parse("android.resource://id.infiniteuny.mediapembelajaran/raw/a${quizQuestion[questionPos].pembahas}"))
                .into(iv_pembahas)
        }
        when(quizQuestion[questionPos].key){
            "true"->{iv_state.setImageResource(R.drawable.ic_true)}
            "false"->{iv_state.setImageResource(R.drawable.ic_false)}
        }
        when(ans){
            true->{
                tvState.text="YEAY!"
                tvState2.text="Kamu Benar!"
            }
            else->{
                tvState.text="YAH!"
                tvState2.text="Kamu Salah"
            }
        }
        db.collection("jawaban")
            .document(quizQuestion[questionPos].id.toString())
            .get().addOnSuccessListener {
                tvTrue.text=" = ${it["jawab_true"] as Long}"
                tvFalse.text=" = ${it["jawab_false"] as Long}"
                builder.apply {
                    pg_bar.visibility=View.GONE
                    setTitle("")
                    setView(v)
                    setCancelable(false)
                }
                val alert = builder.create()
                alert.show()
                btnNext.setOnClickListener {
                    alert.dismiss()
                    afterCheck()
                }
            }
            .addOnFailureListener {
                logE(it.message)
            }
    }

    private fun showQuestion() {
        tv_question.text = quizQuestion[questionPos].question.replace("\\n", "\n", false)
        if(quizQuestion[questionPos].img.isNotEmpty()){
            Glide.with(this).load(Uri
                    .parse("android.resource://id.infiniteuny.mediapembelajaran/raw/a${quizQuestion[questionPos].img}"))
                .into(iv_question)
        }
        questionCount.text = "${questionPos + 1} / ${quizQuestion.size}"
        startCountDown()
    }

    private fun startCountDown() {
        timeLeft=180000
        pgBarControl=1
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                pgBarControl++
                progressbar.progress = pgBarControl * 100 / (180000 / 1000)
                updateCountDown()
            }

            override fun onFinish() {
                timeLeft = 0
                pgBarControl++
                progressbar.progress = 100
                updateCountDown()
                toastCnt("Waktu Habis")
                showResultQuiz()
            }
        }.start()
    }

    private fun updateCountDown() {
        val min = (timeLeft / 1000).toInt() / 60
        val sec = (timeLeft / 1000).toInt() % 60
        val timeFormat = String.format(Locale.getDefault(), "%02d:%02d", min, sec)
        timeCounter!!.text = "Sisa Waktu : ${timeFormat}"

        if (timeLeft < 10000) {
            timeCounter!!.setTextColor(Color.RED)
        } else {
            timeCounter!!.setTextColor(colorStateListCountDown)
        }
    }


    private fun showResultQuiz() {
        countDownTimer!!.cancel()
        checkAnswer()
    }

    private fun getTrainQuestions() {
        quizQuestion.clear()
        val dataQuestions = mutableListOf<QuizModel>()
        val inStream = this.resources.openRawResource(R.raw.quiz)
        val bufferedReader = BufferedReader(InputStreamReader(inStream))
        val read = bufferedReader.use { it.readText() }
        val data = Gson().fromJson(read, Quizes::class.java)
        bufferedReader.close()
        dataQuestions.addAll(data.data)
        shuffle(dataQuestions)
        quizQuestion.addAll(dataQuestions.take(10))
        logD("${quizQuestion.size}")
        for (i in 1..quizQuestion.size){
            dataQuizPosition.add(i)
        }
        rvAdapter.notifyDataSetChanged()
        showQuestion()
    }
    private fun inLastQuestion(){
        countDownTimer!!.cancel()
        logD("score $score")
        val intent = Intent(this, QuizResultActivity::class.java)
        intent.putExtra("caller", "quizes")
        intent.putExtra("score", score*10)
        intent.putExtra("true_ans",score)
        intent.putExtra("false_ans",quizQuestion.size-score)
        startActivity(intent)
        finish()
    }
}
