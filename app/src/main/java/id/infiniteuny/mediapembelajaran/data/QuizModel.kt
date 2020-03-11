package id.infiniteuny.mediapembelajaran.data

import id.infiniteuny.mediapembelajaran.ui.pembahasan.ActivityPembahasan

data class QuizModel(
    var id:Int,
    var question:String,
    var img:String,
    var key:String,
    var pembahas:String
)