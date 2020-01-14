package id.infiniteuny.mediapembelajaran.ui.quiz

import android.content.Context
import id.infiniteuny.mediapembelajaran.data.QuestionModel

/**
 * Created by wijaya on 14/12/19
 */
interface QuizView {

    fun isLoading(state: Boolean)
    fun isError(msg: String)
    fun showQuestions(data:List<QuestionModel>)
    fun resultUpload(state: Boolean,score:Int)
    fun resultLoad(state: Boolean)
    fun context():Context
}