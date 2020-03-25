@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package id.infiniteuny.mediapembelajaran.ui.quiz

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.base.RvAdapter
import id.infiniteuny.mediapembelajaran.data.QuestionModel
import id.infiniteuny.mediapembelajaran.utils.logD
import id.infiniteuny.mediapembelajaran.utils.toastCnt
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.activity_quiz_online.progressbar
import kotlinx.android.synthetic.main.activity_quiz_online.questionCount
import kotlinx.android.synthetic.main.activity_setting.btn_back
import java.util.Locale

class QuizActivity : AppCompatActivity(), QuizView {

    private lateinit var presenter: QuizPresenter
    private var quizType = ""
    private val dataQuiz = mutableListOf<QuestionModel>()
    private var questionPos = 0
    private var score = 0

    private var colorStateList: ColorStateList? = null
    private var colorStateListCountDown: ColorStateList? = null
    private var countDownTimer: CountDownTimer? = null

    private var timeLeft: Long = 0
    private var answered = false

    private var qCounter: Int = 0
    private var qCountTotal: Int = 0

    private var ans: Boolean = false

    private var onBackPressedTime: Long = 0

    private var pgBarControl = 1
    private var keyQuiz = ""

    private val dataQuizPosition= mutableListOf<Int>()
    private var rvAdapter=object : RvAdapter<Any>(dataQuizPosition){
        override fun layoutId(position: Int, obj: Any): Int =R.layout.item_circle

        override fun viewHolder(view: View, viewType: Int): RecyclerView.ViewHolder =SoalPositionVH(view)

    }



    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_quiz)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        progressbar.progress = pgBarControl

        colorStateListCountDown = timeCounter!!.textColors

        timeLeft = COUNTDOWN_TIMER

        quizType = intent.getStringExtra("type")

        presenter = QuizPresenter(FirebaseFirestore.getInstance(), this)
        val layMan=LinearLayoutManager(this)
        layMan.orientation=LinearLayoutManager.HORIZONTAL
        rv_soal_pos.apply {
            adapter=rvAdapter
            layoutManager=layMan
        }

        quizTypeDecision()

        btn_back.setOnClickListener {
            onBackPressed()
        }
        btn_submit.setOnClickListener {

            if (radioButton1.isChecked || radioButton2.isChecked || radioButton3.isChecked
                || radioButton4.isChecked || radioButton5.isChecked
            ) {
                checkAnswer()
            } else {
                toastCnt("Pilih 1 Jawaban Dahulu")
            }

        }
    }

    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                pgBarControl++
                progressbar.progress = pgBarControl * 100 / (QuizActivity.COUNTDOWN_TIMER.toInt() / 1000)
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

    private fun quizTypeDecision() {
        when (quizType) {
            "eval" -> {

            }
            "train" -> {
                presenter.getTrainQuestions()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        countDownTimer!!.cancel()
        finish()
    }

    override fun onPause() {
        super.onPause()
        val activityManager = applicationContext
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        activityManager.moveTaskToFront(taskId, 0)
    }

    override fun isLoading(state: Boolean) {
    }

    override fun isError(msg: String) {
    }

    override fun showQuestions(data: List<QuestionModel>) {
        dataQuiz.clear()
        dataQuiz.addAll(data)
        startCountDown()
        for (i in 1..data.size){
            dataQuizPosition.add(i)
        }
        rvAdapter.notifyDataSetChanged()
        loadQuestion()
    }

    override fun resultUpload(state: Boolean, score: Int) {
    }

    override fun resultLoad(state: Boolean) {
    }

    override fun context(): Context = this

    private fun loadQuestion() {
        radioGroup.clearCheck()
        tv_question.text = dataQuiz[questionPos].question
        radioButton1.text = dataQuiz[questionPos].option1
        radioButton2.text = dataQuiz[questionPos].option2
        radioButton3.text = dataQuiz[questionPos].option3
        radioButton4.text = dataQuiz[questionPos].option4
        radioButton5.text = dataQuiz[questionPos].option5
        questionCount.text = "${questionPos + 1} / ${dataQuiz.size}"
    }

    private fun checkAnswer() {
        if (questionPos <= dataQuiz.size - 1) {
            val radioSelected = findViewById<View>(radioGroup!!.checkedRadioButtonId) as RadioButton
            val answer = radioGroup!!.indexOfChild(radioSelected) + 1
            if (answer == dataQuiz[questionPos].rightAns) {
                //toastCnt("True")
                score++
            } else {
                //toastCnt("false")
            }
            questionPos++
            if (questionPos <= dataQuiz.size - 1) {
                loadQuestion()
                QuizPosHelper.quizPos=questionPos+1
                rvAdapter.notifyDataSetChanged()
                rv_soal_pos.layoutManager!!.scrollToPosition(QuizPosHelper.quizPos-1)
            } else {
                showResultQuiz()
//                toastCnt(score.toString())
            }
        }
    }

    private fun showResultQuiz() {
        countDownTimer!!.cancel()
        val intent = Intent(this, QuizResultActivity::class.java)
        logD("score $score")
        val trueAns=score
        score = (score * 5)
        intent.putExtra("caller", "practice")
        intent.putExtra("score", score)
        intent.putExtra("true_ans",trueAns)
        intent.putExtra("false_ans",dataQuiz.size-trueAns)
        startActivity(intent)
        finish()
    }

    companion object {

        val FINAL_SCORE = "FinalScore"
        private val COUNTDOWN_TIMER: Long = 2700000
    }
}
