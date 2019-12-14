package id.infiniteuny.mediapembelajaran.ui.quiz

/**
 * Created by wijaya on 14/12/19
 */
interface QuizView {

    fun isLoading(state: Boolean)
    fun isError(msg: String)
}