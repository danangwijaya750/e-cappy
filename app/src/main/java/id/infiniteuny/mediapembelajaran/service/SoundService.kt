package id.infiniteuny.mediapembelajaran.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import id.infiniteuny.mediapembelajaran.R

/**
 * Created by wijaya on 24/01/20
 */
class SoundService : Service() {

    companion object{
        val SERVICE_TAG="sound.service"
    }

    lateinit var mp: MediaPlayer

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer.create(this, R.raw.backsound)
        mp.isLooping = true
    }

    override fun onDestroy() {
        mp.stop()
        mp.release()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mp.start()
        return super.onStartCommand(intent, flags, startId)
    }
}