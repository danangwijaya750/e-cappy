package id.infiniteuny.mediapembelajaran.utils

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast

/**
 * Created by wijaya on 11/12/19
 */
inline fun <reified T>T.logD(msg:String?) = msg.let{
    Log.d(T::class.java.simpleName,it)
}
inline fun <reified T>T.logE(msg:String?) = msg.let{
    Log.e(T::class.java.simpleName,it)
}
fun Context.toastCnt(msg: String?){
    val tst= Toast.makeText(this,msg,Toast.LENGTH_SHORT)
    tst.setGravity(Gravity.CENTER,0,0)
    tst.show()
}