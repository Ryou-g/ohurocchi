package com.example.ohurocchi

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreditActivity : AppCompatActivity() {

    // ① 準備（コンポを部屋に置く・コピペOK）
    private lateinit var mp: MediaPlayer

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit)

        val imageView5 = findViewById<ImageView>(R.id.imageView5)

        var status = Login_status.getInstance()
        val doc = status.now_Login

        db.collection("NameChange").document(doc).get()
            .addOnCompleteListener { background ->
                if(background.isSuccessful){
                    val background_document = background.result
                    if (background_document != null && background_document.data != null){
                        //var rrr =dress_document.data?.get("Favorability")
                        imageView5.setImageResource(getResources().getIdentifier(background_document.data?.get("nowBackground") as String?,"drawable", getPackageName()))
                    }
                }

            }

    val btnBack: ImageButton = findViewById(R.id.btnBack5)

    btnBack.setOnClickListener {
        //ここから遷移用のコード
        val intent = Intent(this,SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
        startActivity(intent)
        //ここまで
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

    }
    // ③ 読込処理(CDを入れる)
    mp = MediaPlayer.create(this,R.raw.setting)
    mp.setVolume(0.4F,0.4F)
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