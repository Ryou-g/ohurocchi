package com.example.ohurocchi

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.ohurocchi.databinding.ActivityBackgroundBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BackgroundActivity : AppCompatActivity() {

    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)

        val btnBack :Button = findViewById(R.id.btnBack)
        val acceptButton: Button = findViewById(R.id.button)
        var acceptflag = ""

        val db = Firebase.firestore

        btnBack.setOnClickListener {

            finish()

        }

        val imageView = findViewById<ImageView>(R.id.imageView4)
        val imageButton14 = findViewById<ImageButton>(R.id.imageButton14)
        val textView = findViewById<TextView>(R.id.textView4)

        imageButton14.setOnClickListener {

            imageView.setImageResource(R.drawable.japanese_1)
            textView.setText("バスルーム");
            acceptflag = "japanese_1"

        }

        val imageButton15 = findViewById<ImageButton>(R.id.imageButton15)

        imageButton15.setOnClickListener {

            imageView.setImageResource(R.drawable.background_1)
            textView.setText("露天風呂");
            acceptflag = "background_1"
        }

        val imageButton16 = findViewById<ImageButton>(R.id.imageButton16)

        imageButton16.setOnClickListener {

            imageView.setImageResource(R.drawable.apart_1)
            textView.setText("浴室");
            acceptflag = "apart_1"
        }

        val imageButton17 = findViewById<ImageButton>(R.id.imageButton17)

        imageButton17.setOnClickListener {

            imageView.setImageResource(R.drawable.telmare_1)
            textView.setText("洋式風呂");
            acceptflag = "telmare_1"
        }

        //適用ボタン押下時処理
        acceptButton.setOnClickListener {
            db.collection("NameChange").document("NameChange").update("nowBackground",acceptflag)
            Toast.makeText(this, "着せ替え成功！", Toast.LENGTH_SHORT).show()

        }

        // ③ 読込処理(CDを入れる)
        mp = MediaPlayer.create(this,R.raw.setting)
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