package id.infiniteuny.mediapembelajaran.data

/**
 * Created by wijaya on 10/01/20
 */
data class ScoreModel(
    var uid: String,
    var grade: String,
    var name: String,
    var key_quiz: String,
    var kelas:String

) {

    constructor() : this("", "", "", "","")
}
