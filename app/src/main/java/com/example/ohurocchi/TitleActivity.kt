package com.example.ohurocchi

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.media.MediaPlayer
import android.widget.ImageView

class TitleActivity : AppCompatActivity() {
    //５）mpを横断的に使えるようにここに書く
    private lateinit var mp:MediaPlayer
    private lateinit var mp1:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title)

        //１）Viewの取得
        val btnStart :Button =findViewById(R.id.btnStart)

        //２）ボタンを押したら次の画面へ
        btnStart.setOnClickListener {

            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) //フェードイン・フェードアウト
        }

        mp = MediaPlayer.create(this,R.raw.bath)
        mp1 = MediaPlayer.create(this,R.raw.title)
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