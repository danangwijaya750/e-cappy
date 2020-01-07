package id.infiniteuny.mediapembelajaran.ui.quiz

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
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.QuestionModel
import id.infiniteuny.mediapembelajaran.utils.logD
import id.infiniteuny.mediapembelajaran.utils.toastCnt
import kotlinx.android.synthetic.main.activity_quiz.btn_submit
import kotlinx.android.synthetic.main.activity_quiz.radioButton1
import kotlinx.android.synthetic.main.activity_quiz.radioButton2
import kotlinx.android.synthetic.main.activity_quiz.radioButton3
import kotlinx.android.synthetic.main.activity_quiz.radioButton4
import kotlinx.android.synthetic.main.activity_quiz.radioButton5
import kotlinx.android.synthetic.main.activity_quiz.radioGroup
import kotlinx.android.synthetic.main.activity_quiz.timeCounter
import kotlinx.android.synthetic.main.activity_quiz.tv_question
import kotlinx.android.synthetic.main.activity_quiz_online.pg_bar
import kotlinx.android.synthetic.main.activity_quiz_online.progressbar
import kotlinx.android.synthetic.main.activity_quiz_online.questionCount
import kotlinx.android.synthetic.main.activity_quiz_online.quiz_container
import kotlinx.android.synthetic.main.activity_setting.btn_back
import java.util.Collections.shuffle
import java.util.Locale

class QuizOnlineActivity : AppCompatActivity(), QuizView {

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

    private var pgBarControl=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_quiz_online)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()
        progressbar.progressDrawable.setColorFilter(R.color.softYellow, android.graphics.PorterDuff.Mode.SRC_IN)
        progressbar.progress = pgBarControl

        colorStateListCountDown = timeCounter!!.textColors

        presenter = QuizPresenter(FirebaseFirestore.getInstance(), this)

        timeLeft = COUNTDOWN_TIMER
        loadQuiz()

        btn_back.setOnClickListener {
            onBackPressed()
        }
        btn_submit.setOnClickListener {

            checkAnswer()

        }
    }

    private fun loadQuiz() {
        presenter.getAllQuestions(intent.getStringExtra("key")!!)
    }

    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                updateCountDown()
            }

            override fun onFinish() {
                timeLeft = 0
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
        timeCounter!!.text = timeFormat

        if (timeLeft < 10000) {
            timeCounter!!.setTextColor(Color.RED)
        } else {
            timeCounter!!.setTextColor(colorStateListCountDown)
        }
    }

    override fun isLoading(state: Boolean) {
        when (state) {
            true -> {
                pg_bar.visibility = View.VISIBLE
                quiz_container.visibility = View.GONE
            }
            else -> {
                pg_bar.visibility = View.GONE
                quiz_container.visibility = View.VISIBLE
            }
        }
    }

    override fun isError(msg: String) {
        toastCnt("Error Saat mengambil Soal, Pastikan Key Anda benar dan terhubung dengan Internet")
    }

    override fun showQuestions(data: List<QuestionModel>) {
        dataQuiz.clear()
        dataQuiz.addAll(data)
        shuffle(dataQuiz)
        startCountDown()
        loadQuestion()
    }

    private fun loadQuestion() {
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
                toastCnt("True")
                score++
            } else {
                toastCnt("false")
            }
            questionPos++
            if (questionPos <= dataQuiz.size - 1) {
                loadQuestion()
            } else {
                showResultQuiz()
            }
        }
    }

    private fun showResultQuiz() {
        countDownTimer!!.cancel()
        val intent = Intent(this, QuizResultActivity::class.java)
        logD("score $score")
        score = (score * 100) / dataQuiz.size
        intent.putExtra("score", score)
        startActivity(intent)
        finish()
//        toastCnt(score.toString())
    }

    companion object {

        val FINAL_SCORE = "FinalScore"
        private val COUNTDOWN_TIMER: Long = 60000
    }
}