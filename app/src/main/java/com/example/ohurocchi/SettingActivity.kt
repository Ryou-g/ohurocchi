package com.example.ohurocchi

import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SettingActivity : AppCompatActivity() {

    // ① 準備（コンポを部屋に置く・コピペOK）
    private lateinit var mp: MediaPlayer

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val namechange : Button = findViewById(R.id.namechange)
        val dressup : Button = findViewById(R.id.dressup)
        val background1 : Button = findViewById(R.id.background)

        val imageView2 = findViewById<ImageView>(R.id.imageView3)

        val progressBar1: ProgressBar = findViewById(R.id.circle_progressBar)

        var status = Login_status.getInstance()

        val doc = status.now_Login

        // 水平プログレスバーの最大値を設定します
        progressBar1.setMax(200);

        //好感度を取得
        var Faboravirity = 0
        db.collection("NameChange").document(doc)
            .get()
            .addOnCompleteListener{ Fav ->
                if(Fav.isSuccessful){
                    val Fav_document = Fav.result
                    if(Fav_document != null && Fav_document.data != null){
                        Faboravirity = Integer.parseInt((Fav_document.data?.get("Favorability")).toString())
                        // progress
                        progressBar1.setProgress(Faboravirity);
                    }
                }
            }


        db.collection("NameChange").document(doc).get()
            .addOnCompleteListener { background ->
                if(background.isSuccessful){
                    val background_document = background.result
                    if (background_document != null && background_document.data != null){
                        //var rrr =dress_document.data?.get("Favorability")
                        imageView2.setImageResource(getResources().getIdentifier(background_document.data?.get("nowBackground") as String?,"drawable", getPackageName()))
                    }
                }

            }

        val textView16: TextView = findViewById(R.id.textView16)


        db.collection("NameChange").document(doc)
            .get()
            .addOnSuccessListener { result ->
                if(result != null) {
                    textView16.text = result.data!!["Favorability"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        namechange.setOnClickListener {
            val intent = Intent(applicationContext,NameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        dressup.setOnClickListener {
            val intent = Intent(applicationContext,DressupActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        background1.setOnClickListener {
            val intent = Intent(applicationContext,BackgroundActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        //ここからホーム画面遷移のコード
        val Button_1: Button = findViewById(R.id.Button_1)
        Button_1.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, HomeActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        //ここから衣装配布画面遷移のコード
        val Button_2: Button = findViewById(R.id.Button_2)
        Button_2.setOnClickListener {
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
        val Button_3: Button = findViewById(R.id.Button_3)
        Button_3.setOnClickListener {
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
        val Button_4: Button = findViewById(R.id.Button_4)
        Button_4.setOnClickListener{
            //ここから遷移用のコード
            val intent = Intent(this,SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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
