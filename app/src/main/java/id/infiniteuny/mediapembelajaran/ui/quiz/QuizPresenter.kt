package id.infiniteuny.mediapembelajaran.ui.quiz

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import id.infiniteuny.mediapembelajaran.R
import id.infiniteuny.mediapembelajaran.data.Pref
import id.infiniteuny.mediapembelajaran.data.QuestionModel
import id.infiniteuny.mediapembelajaran.data.Questions
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import id.infiniteuny.mediapembelajaran.utils.logD
import id.infiniteuny.mediapembelajaran.utils.logE
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by wijaya on 14/12/19
 */
class QuizPresenter(private val db: FirebaseFirestore, private val view: QuizView) {

    fun getAllQuestions(key: String) {
        view.isLoading(true)
        val docRef = db.collection("quiz_questions").whereEqualTo("key_quiz", key)
        docRef.get()
            .addOnSuccessListener {
                if (it != null) {
                    val data = mutableListOf<QuestionModel>()
                    it.forEach { snap ->
                        data.add(snap.toObject(QuestionModel::class.java))
                    }
                    logD(data.size.toString())
                    view.showQuestions(data)
                } else {
                    view.isError("data kosong")
                }
                view.isLoading(false)
            }
            .addOnFailureListener {
                logE(it.localizedMessage)
                view.isError(it.localizedMessage!!)
                view.isLoading(false)
            }
    }

    fun getTrainQuestions() {
        val dataQuestions = mutableListOf<QuestionModel>()
        val inStream = view.context().resources.openRawResource(R.raw.soal)
        val bufferedReader = BufferedReader(InputStreamReader(inStream))
        val read = bufferedReader.use { it.readText() }
        val data = Gson().fromJson(read, Questions::class.java)
        bufferedReader.close()
        dataQuestions.addAll(data.data)
        view.showQuestions(dataQuestions)
    }

    fun uploadScore(score: Int, keyquiz: String, name: String,kelas:String) {
        val fAuth = FirebaseAuth.getInstance()
        val uid = fAuth.currentUser!!.uid
        view.resultLoad(true)
        db.collection("student_grade")
            .add(ScoreModel(uid, score.toString(), name, keyquiz,kelas))
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    view.resultUpload(true, score)
                } else {
                    view.resultUpload(false, score)
                }
                view.resultLoad(false)
            }
            .addOnFailureListener {
                view.resultUpload(false, score)
                view.resultLoad(false)
            }
    }
}