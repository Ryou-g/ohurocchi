package com.example.ohurocchi

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity() {
    private lateinit var mp: MediaPlayer

    // ① 準備（コンポを部屋に置く・コピペOK）
    private var soundPool // 効果音を鳴らす本体（コンポ）
            : SoundPool? = null
    private var mp3a // 効果音データ（mp3）
            = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // ② 初期化（電源を入れる・コピペOK）
        soundPool = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        } else {
            val attr = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            SoundPool.Builder()
                .setAudioAttributes(attr)
                .setMaxStreams(5)
                .build()
        }

        // ③ 読込処理(CDを入れる)
        mp3a = soundPool!!.load(this, R.raw.voice1, 1)
        mp = MediaPlayer.create(this,R.raw.bath)
        mp.isLooping = true
        mp.start()
    }

    fun onA(v: View?) {
        // ④ 再生処理(再生ボタン)
        soundPool!!.play(mp3a, 1f, 1f, 0, 0, 1f)
    }
    //６）再開
    override fun onResume() {
        super.onResume()
        mp.start()
    }
    //５）一時停止
    override fun onPause() {
        super.onPause()
        mp.pause()
    }

    //７）終了・メモリの解放
    override fun onDestroy() {
        super.onDestroy()
        mp.stop() //終了・停止
        mp.release() //解放
    }
}