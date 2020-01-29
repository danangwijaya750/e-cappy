package id.infiniteuny.mediapembelajaran.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by wijaya on 10/01/20
 */
class Pref (context: Context) {
    val PREFS_FILENAME = "id.infiniteuny.mediapembelajaran"
    val NAME = "name"
    val SOUND="sound"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var user_name: String
        get() = prefs.getString(NAME,"halo").toString()
        set(value) = prefs.edit().putString(NAME,value).apply()

    var sound_setting:Boolean
    get()=prefs.getBoolean(SOUND,true)
    set(value) = prefs.edit().putBoolean(SOUND,value).apply()
}