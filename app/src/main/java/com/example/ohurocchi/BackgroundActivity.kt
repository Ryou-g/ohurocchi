package com.example.ohurocchi

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BackgroundActivity : AppCompatActivity() {

    // BGM
    private lateinit var mp: MediaPlayer

    // SE
    private lateinit var soundPool: SoundPool
    private var buttonse = 0
    private var choice = 0
    private var decision = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)

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
            .setMaxStreams(3)
            .build()

        // 効果音をロードしておく
        buttonse = soundPool.load(this, R.raw.button_se, 1)
        choice = soundPool.load(this, R.raw.choice, 1)
        decision = soundPool.load(this, R.raw.decision, 1)

        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener{ soundPool, sampleId, status ->
            Log.d("debug", "sampleId=$sampleId")
            Log.d("debug", "status=$status")
        }

        val btnBack :ImageButton = findViewById(R.id.btnBack2)
        val acceptButton: Button = findViewById(R.id.button)
        var acceptflag = ""

        val db = Firebase.firestore

        btnBack.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)
            //ここから遷移用のコード
            val intent = Intent(this,SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }



        val imageView = findViewById<ImageView>(R.id.imageView4)
        val imageButton14 = findViewById<ImageButton>(R.id.imageButton14)
        val textView = findViewById<TextView>(R.id.textView4)

        imageButton14.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.wafu)
            textView.text = "浴室"
            acceptflag = "wafu"

        }

        val imageButton15 = findViewById<ImageButton>(R.id.imageButton15)

        imageButton15.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.roten)
            textView.text = "露天風呂"
            acceptflag = "roten"
        }

        val imageButton16 = findViewById<ImageButton>(R.id.imageButton16)

        imageButton16.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.huro)
            textView.text = "バスルーム"
            acceptflag = "huro"
        }

        val imageButton17 = findViewById<ImageButton>(R.id.imageButton17)

        imageButton17.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.daiyokujo)
            textView.text = "洋式風呂"
            acceptflag = "daiyokujo"
        }
        var status = Login_status.getInstance()

        val doc = status.now_Login

        //適用ボタン押下時処理
        acceptButton.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(decision, 0.3f, 0.3f, 0, 0, 1.0f)

            db.collection("NameChange").document(doc).update("nowBackground",acceptflag)
            Toast.makeText(this, "切り替え成功！", Toast.LENGTH_SHORT).show()

        }

        // ③ 読込処理(CDを入れる)
        mp = MediaPlayer.create(this,R.raw.dress_buy)
        mp.setVolume(0.5F,0.5F)
        mp.isLooping = true
        mp.start()
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