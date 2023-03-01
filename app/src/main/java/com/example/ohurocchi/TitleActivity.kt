package com.example.ohurocchi

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import android.widget.ImageView

class TitleActivity : AppCompatActivity() {
    // BGM
    private lateinit var mp:MediaPlayer

    // Title_call
    private lateinit var mp1:MediaPlayer

    // SE
    private lateinit var soundPool: SoundPool
    private var titlestart = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val status = Login_status.getInstance()
        val sharedPref = getSharedPreferences("user_login_id", Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        val audioAttributes = AudioAttributes.Builder()
            // USAGE_MEDIA
            // USAGE_GAME
            .setUsage(AudioAttributes.USAGE_GAME)
            // CONTENT_TYPE_MUSIC
            // CONTENT_TYPE_SPEECH, etc.
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            // ストリーム数に応じて
            .setMaxStreams(2)
            .build()

        // 効果音をロードしておく
        titlestart = soundPool.load(this, R.raw.title_start, 1)


        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener{ soundPool, sampleId, status ->
            Log.d("debug", "sampleId=$sampleId")
            Log.d("debug", "status=$status")
        }

        //１）Viewの取得
        val btnStart :Button =findViewById(R.id.btnStart)

        //２）ボタンを押したら次の画面へ
        btnStart.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(titlestart, 0.3f, 0.3f, 0, 0, 1.0f)
            //val intent = Intent(this,HomeActivity::class.java)
            var savedText = sharedPref.getString("user_id", "none")
            //savedText = "none"
            if (savedText == "none"){
                val intent = Intent(this,AuthActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) //フェードイン・フェードアウト
            }else{
                //ログイン情報があるとき
                if (savedText != null) {
                    status.now_Login = savedText
                }
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) //フェードイン・フェードアウト
            }

        }

        mp = MediaPlayer.create(this,R.raw.bath)
        mp.setVolume(0.4F,0.4F)
        mp1 = MediaPlayer.create(this,R.raw.title)
        mp1.setVolume(1F,1F)
        mp.isLooping = true
        mp.start()
        mp1.start()
    }


    private fun animateRotationY(img:ImageView) {                   //タイトルアニメーション
        val objectAnimator = ObjectAnimator.ofFloat(img, "rotationY", 2f)
        objectAnimator.duration = 2000
        objectAnimator.repeatCount = -1
        objectAnimator.start()
    }

    //６）再開
    override fun onResume() {
        super.onResume()
        mp.start()
        mp1.start()
    }
    //５）一時停止
    override fun onPause() {
        super.onPause()
        mp.pause()
        mp1.pause()
    }

    //７）終了・メモリの解放
    override fun onDestroy() {
        super.onDestroy()
        mp.stop() //終了・停止
        mp1.stop() //終了・停止
        mp.release() //解放
        mp1.release() //解放
    }

}