package id.infiniteuny.mediapembelajaran.ui.quiz

import com.google.firebase.firestore.FirebaseFirestore
import id.infiniteuny.mediapembelajaran.data.QuestionModel

/**
 * Created by wijaya on 14/12/19
 */
class QuizPresenter(private val db:FirebaseFirestore,private val view:QuizView) {

    fun getAllQuestions(){

    }

    fun getTrainQuestions(){
        val dataQuestions= mutableListOf<QuestionModel>()
        for(i in 0..5){
            val question=QuestionModel("Question $i","option1 $i","option2 $i","option3 $i","option4 $i","option5 $i",i)
            dataQuestions.add(question)
        }
        view.showQuestions(dataQuestions)
    }
}