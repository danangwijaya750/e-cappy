@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package id.infiniteuny.mediapembelajaran.ui.quiz

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.QuestionModel
import id.infiniteuny.mediapembelajaran.utils.toastCnt
import kotlinx.android.synthetic.main.activity_quiz.btn_submit
import kotlinx.android.synthetic.main.activity_quiz.radioButton1
import kotlinx.android.synthetic.main.activity_quiz.radioButton2
import kotlinx.android.synthetic.main.activity_quiz.radioButton3
import kotlinx.android.synthetic.main.activity_quiz.radioButton4
import kotlinx.android.synthetic.main.activity_quiz.radioButton5
import kotlinx.android.synthetic.main.activity_quiz.radioGroup
import kotlinx.android.synthetic.main.activity_quiz.tv_question
import kotlinx.android.synthetic.main.activity_setting.btn_back

class QuizActivity : AppCompatActivity(), QuizView {

    private lateinit var presenter: QuizPresenter
    private var quizType = ""
    private val dataQuiz = mutableListOf<QuestionModel>()
    private var questionPos = 0
    private var score = 0

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

        quizType = intent.getStringExtra("type")

        presenter = QuizPresenter(FirebaseFirestore.getInstance(), this)

        quizTypeDecision()

        btn_back.setOnClickListener {
            onBackPressed()
        }
        btn_submit.setOnClickListener {

            checkAnswer()

        }
    }

    private fun quizTypeDecision() {
        when (quizType) {
            "eval" -> {
                presenter.getAllQuestions()
            }
            "train" -> {
                presenter.getTrainQuestions()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
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
        loadQuestion()
    }

    private fun loadQuestion() {
        tv_question.text = dataQuiz[questionPos].question
        radioButton1.text = dataQuiz[questionPos].option1
        radioButton2.text = dataQuiz[questionPos].option2
        radioButton3.text = dataQuiz[questionPos].option3
        radioButton4.text = dataQuiz[questionPos].option4
        radioButton5.text = dataQuiz[questionPos].option5
    }

    private fun checkAnswer() {

        val radioSelected = findViewById<View>(radioGroup!!.checkedRadioButtonId) as RadioButton
        val answer = radioGroup!!.indexOfChild(radioSelected) + 1
        if (answer == dataQuiz[questionPos].rightAns) {
            toastCnt("True")
        } else {
            toastCnt("false")
        }

        questionPos++
        if (questionPos < dataQuiz.size - 1) {
            loadQuestion()
        }
    }
}
