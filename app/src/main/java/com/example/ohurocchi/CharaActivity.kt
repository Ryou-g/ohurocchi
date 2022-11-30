package com.example.ohurocchi

import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CharaActivity : AppCompatActivity(){

    private var db = Firebase.firestore

    // ① 準備（コンポを部屋に置く・コピペOK）

    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chara)

        val textView8: TextView = findViewById(R.id.textView8)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val imageView2 = findViewById<ImageView>(R.id.imageView5)

        db.collection("NameChange")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    textView8.text = document.data!!["name"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        db.collection("NameChange").document("NameChange").get()
            .addOnCompleteListener { dress ->
                if(dress.isSuccessful){
                    val dress_document = dress.result
                    if (dress_document != null && dress_document.data != null){
                        //var rrr =dress_document.data?.get("Favorability")
                        //imageView.setImageResource(getResources().getIdentifier(dress_document.data?.get("nowDress") as String?,"drawable", getPackageName()))
                        var Fav = Integer.parseInt((dress_document.data?.get("Favorability")).toString())
                        val dress_num = Integer.parseInt((dress_document.data?.get("nowDress_num")).toString())
                        Log.d(TAG,"dress_num=$dress_num")
                        Log.d(TAG,"Fav=$Fav")
                        if(dress_num == 1){
                            if(Fav >= 150){
                                imageView.setImageResource(R.drawable.coat_highest)
                            }else if(Fav >= 100){
                                imageView.setImageResource(R.drawable.coat_usually)
                            }else if(Fav >= 50){
                                imageView.setImageResource(R.drawable.coat_bad)
                            }else{
                                imageView.setImageResource(R.drawable.coat_terrible)
                            }
                        }else if(dress_num == 2){
                            if(Fav >= 150){
                                imageView.setImageResource(R.drawable.dress_highest)
                            }else if(Fav >= 100){
                                imageView.setImageResource(R.drawable.dress_usually)
                            }else if(Fav >= 50){
                                imageView.setImageResource(R.drawable.dress_bad)
                            }else{
                                imageView.setImageResource(R.drawable.dress_terrible)
                            }
                        }else if(dress_num == 3){
                            if(Fav >= 150){
                                imageView.setImageResource(R.drawable.maid_highest)
                            }else if(Fav >= 100){
                                imageView.setImageResource(R.drawable.maid_usually)
                            }else if(Fav >= 50){
                                imageView.setImageResource(R.drawable.maid_bad)
                            }else{
                                imageView.setImageResource(R.drawable.maid_terrible)
                            }
                        }else if(dress_num == 4){
                            if(Fav >= 150){
                                imageView.setImageResource(R.drawable.uniform_highest)
                            }else if(Fav >= 100){
                                imageView.setImageResource(R.drawable.uniform_usually)
                            }else if(Fav >= 50){
                                imageView.setImageResource(R.drawable.uniform_bad)
                            }else{
                                imageView.setImageResource(R.drawable.uniform_terrible)
                                Log.d(TAG,"okdayo")
                            }
                        }

                    }
                }

            }

        db.collection("NameChange").document("NameChange").get()
            .addOnCompleteListener { background ->
                if(background.isSuccessful){
                    val background_document = background.result
                    if (background_document != null && background_document.data != null){
                        //var rrr =dress_document.data?.get("Favorability")
                        imageView2.setImageResource(getResources().getIdentifier(background_document.data?.get("nowBackground") as String?,"drawable", getPackageName()))
                    }
                }

            }

        //ここからホーム画面遷移のコード
        val imageButton3: ImageButton = findViewById(R.id.imageButton3)
        imageButton3.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, HomeActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            //ここまで
        }
        //ここから衣装配布画面遷移のコード
        val imageButton2: ImageButton = findViewById(R.id.imageButton2)
        imageButton2.setOnClickListener {
            //ここから遷移用のコード
            val intent = Intent(
                this,
                DressbuyActivity::class.java
            )    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        //ここからキャラ画面遷移のコード
        val imageButton: ImageButton = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            //ここから遷移用のコード
            val intent = Intent(
                this,
                CharaActivity::class.java
            )    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        //ここから設定画面遷移のコード
        val imageButton4: ImageButton = findViewById(R.id.imageButton4)
        imageButton4.setOnClickListener{
            //ここから遷移用のコード
            val intent = Intent(this,SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        mp = MediaPlayer.create(this,R.raw.chara)
        mp.isLooping = true
        mp.start()
    }     // ③ 読込処理(CDを入れる)

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