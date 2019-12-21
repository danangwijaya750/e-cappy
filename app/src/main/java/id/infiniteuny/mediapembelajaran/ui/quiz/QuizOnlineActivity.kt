package id.infiniteuny.mediapembelajaran.ui.quiz

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.QuestionModel
import kotlinx.android.synthetic.main.activity_quiz.timeCounter
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

    private var qCounter: Int = 0
    private var qCountTotal: Int = 0

    private var ans: Boolean = false

    private var onBackPressedTime: Long = 0

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


        presenter = QuizPresenter(FirebaseFirestore.getInstance(), this)

        loadQuiz()
    }


    private fun loadQuiz(){

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
            }
        }.start()
    }

    private fun updateCountDown() {
        val min = (timeLeft / 1000).toInt() / 60
        val sec = (timeLeft / 1000).toInt() % 60

        val timeFormat = String.format(Locale.getDefault(), "%02d:%02d", min, sec)
        timeCounter!!.setText(timeFormat)

        if (timeLeft < 10000) {
            timeCounter!!.setTextColor(Color.RED)
        } else {
            timeCounter!!.setTextColor(colorStateListCountDown)
        }
    }

    override fun isLoading(state: Boolean) {
    }

    override fun isError(msg: String) {
    }

    override fun showQuestions(data: List<QuestionModel>) {
    }
}
