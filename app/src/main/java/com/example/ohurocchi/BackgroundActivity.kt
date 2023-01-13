package com.example.ohurocchi

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BackgroundActivity : AppCompatActivity() {

    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)

        val btnBack :ImageButton = findViewById(R.id.btnBack2)
        val acceptButton: Button = findViewById(R.id.button)
        var acceptflag = ""

        val db = Firebase.firestore

        btnBack.setOnClickListener {

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

            imageView.setImageResource(R.drawable.wafu)
            textView.text = "バスルーム"
            acceptflag = "wafu"

        }

        val imageButton15 = findViewById<ImageButton>(R.id.imageButton15)

        imageButton15.setOnClickListener {

            imageView.setImageResource(R.drawable.roten)
            textView.text = "露天風呂"
            acceptflag = "roten"
        }

        val imageButton16 = findViewById<ImageButton>(R.id.imageButton16)

        imageButton16.setOnClickListener {

            imageView.setImageResource(R.drawable.huro)
            textView.text = "浴室"
            acceptflag = "huro"
        }

        val imageButton17 = findViewById<ImageButton>(R.id.imageButton17)

        imageButton17.setOnClickListener {

            imageView.setImageResource(R.drawable.daiyokujo)
            textView.text = "洋式風呂"
            acceptflag = "daiyokujo"
        }
        var status = Login_status.getInstance()

        val doc = status.now_Login

        //適用ボタン押下時処理
        acceptButton.setOnClickListener {
            db.collection("NameChange").document(doc).update("nowBackground",acceptflag)
            Toast.makeText(this, "着せ替え成功！", Toast.LENGTH_SHORT).show()

        }

        // ③ 読込処理(CDを入れる)
        mp = MediaPlayer.create(this,R.raw.dress_buy)
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