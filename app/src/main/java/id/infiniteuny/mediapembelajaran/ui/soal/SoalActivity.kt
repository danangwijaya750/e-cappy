package id.infiniteuny.mediapembelajaran.ui.soal

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizActivity
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizOnlineActivity
import id.infiniteuny.mediapembelajaran.ui.quiz.QuizTrueFalseActivity
import kotlinx.android.synthetic.main.activity_soal.*

class SoalActivity : AppCompatActivity() {

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        setContentView(R.layout.activity_soal)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        supportActionBar?.hide()

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_latihan.setOnClickListener {
            val builder =AlertDialog.Builder(this)
            val v = layoutInflater.inflate(R.layout.layout_instruction, null)
            val b = v.findViewById<LinearLayout>(R.id.root_lay)
            b.setBackgroundColor(resources.getColor(R.color.softYellow))
            val tv = v.findViewById<TextView>(R.id.tv_help)
            tv.text = "- Berdoalah sebelum mengerjakan soal dan pastikan anda sudah belajar\n" +
                    "- Siapkan Kalkulator\n" +
                    "- Soal berbentuk pilihan ganda, sehingga pilih salah satu jawaban yang benar\n" +
                    "- Skor untuk jawaban benar adalah 5\n" +
                    "- Skor untuk jawaban salah adalah 0\n" +
                    "- Jumlah soal 30 dengan waktu 45 menit\n" +
                    "- Kerjakan mandiri untuk mengukur kemampuan anda"

            builder.apply {
                setTitle("Petunjuk Pengerjaan Soal : ")
                setView(v)
                setPositiveButton("Ok") { _, _ ->
                    startQuiz("train")
                }
                setNegativeButton("Batal") { _, _ ->

                }
            }
            builder.create().show()

        }
        btn_tf.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val v = layoutInflater.inflate(R.layout.layout_instruction, null)
            val b = v.findViewById<LinearLayout>(R.id.root_lay)
            b.setBackgroundColor(resources.getColor(R.color.tentang))
            val tv = v.findViewById<TextView>(R.id.tv_help)
            tv.text = "- Berdoalah sebelum mengerjakan soal dan pastikan anda sudah belajar\n" +
                    "- Siapkan Kalkulator\n" +
                    "- Soal berbentuk pilihan ganda, sehingga pilih salah satu jawaban yang benar\n" +
                    "- Skor untuk jawaban benar adalah 5\n" +
                    "- Skor untuk jawaban salah adalah 0\n" +
                    "- Jumlah soal 20 dengan waktu 15 menit\n" +
                    "- Kerjakan mandiri untuk mengukur kemampuan anda"
            builder.apply {
                setTitle("Petunjuk Pengerjaan Soal : ")
                setView(v)
                setPositiveButton("Ok") { _, _ ->
                    val intent = Intent(this@SoalActivity, QuizTrueFalseActivity::class.java)
                    startActivity(intent)
                }
                setNegativeButton("Batal") { _, _ ->

                }
            }
            builder.create().show()

        }

        btn_uji.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val v = layoutInflater.inflate(R.layout.layout_instruction, null)
            val b = v.findViewById<LinearLayout>(R.id.root_lay)
            b.setBackgroundColor(resources.getColor(R.color.lightBlue))
            val tv = v.findViewById<TextView>(R.id.tv_help)
            tv.text = "- Berdoalah sebelum mengerjakan soal dan pastikan anda sudah belajar\n" +
                    "- Siapkan Kalkulator\n" +
                    "- Soal berbentuk pilihan ganda, sehingga pilih salah satu jawaban yang benar\n" +
                    "- Skor untuk jawaban benar adalah 5\n" +
                    "- Skor untuk jawaban salah adalah 0\n" +
                    "- Jumlah soal 20 dengan waktu 15 menit\n" +
                    "- Kerjakan mandiri untuk mengukur kemampuan anda"

            builder.apply {
                setTitle("Petunjuk Pengerjaan Soal : ")
                setView(v)
                setPositiveButton("Ok") { _, _ ->
                    val intent = Intent(this@SoalActivity, QuizOnlineActivity::class.java)
                    intent.putExtra("key", "Key1-Generated")
                    startActivity(intent)
                }
                setNegativeButton("Batal") { _, _ ->

                }
            }
            builder.create().show()

//            val input=EditText(this)
//            val builder=AlertDialog.Builder(this)
//            builder.apply {
//                setTitle("Masukan Key Quiz Anda : ")
//                setView(input)
//                setPositiveButton("Ok"){ _, _ ->
//                    val intent =Intent(this@SoalActivity,QuizOnlineActivity::class.java)
//                    intent.putExtra("key",input.text.toString())
//                    startActivity(intent)
//                }
//                setNegativeButton("Batal"){ _,_->
//
//                }
//            }
//            builder.create().show()

        }
    }

    private fun startQuiz(type: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
