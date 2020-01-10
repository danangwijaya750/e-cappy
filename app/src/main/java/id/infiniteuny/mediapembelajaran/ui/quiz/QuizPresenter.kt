package id.infiniteuny.mediapembelajaran.ui.quiz

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.data.QuestionModel
import id.infiniteuny.mediapembelajaran.data.ScoreModel
import id.infiniteuny.mediapembelajaran.utils.logD
import id.infiniteuny.mediapembelajaran.utils.logE

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
                    it.forEach {snap->
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
        for (i in 1..5) {
            val question = QuestionModel(
                "Question $i",
                "option1 $i",
                "option2 $i",
                "option3 $i",
                "option4 $i",
                "option5 $i",
                i,
                ""
            )
            dataQuestions.add(question)
        }
        view.showQuestions(dataQuestions)
    }
    fun uploadScore(score:Int,keyquiz:String,name:String){
        val fAuth=FirebaseAuth.getInstance()
        val uid= fAuth.currentUser!!.uid
        view.resultLoad(true)
        db.collection("student_grade")
            .add(ScoreModel(uid,score.toString(),name,keyquiz))
            .addOnCompleteListener {
                if(it.isSuccessful){
                    view.resultUpload(true,score)
                }else{
                    view.resultUpload(false,score)
                }
                view.resultLoad(false)
            }
            .addOnFailureListener {
                view.resultUpload(false,score)
                view.resultLoad(false)
            }
    }
}