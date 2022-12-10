package com.example.ohurocchi

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DressbuyActivity : AppCompatActivity() {

    // ① 準備（コンポを部屋に置く・コピペOK）

    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressexchange)
        val db = Firebase.firestore

        val xchange_button = findViewById<Button>(R.id.button)

        //ここからホーム画面遷移のコード
        val imageButton3: ImageButton = findViewById(R.id.imageButton3)
        imageButton3.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, HomeActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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
        imageButton4.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        mp = MediaPlayer.create(this,R.raw.dress_buy)
        mp.isLooping = true
        mp.start()


        val imageView = findViewById<ImageView>(R.id.imageView18)
        val textView = findViewById<TextView>(R.id.textView4)
        val textView1 = findViewById<TextView>(R.id.textView5)


        val adult = findViewById<ImageButton>(R.id.adult)
        var fav_point = 0
        var select_costume = 0

        adult.setOnClickListener {

            imageView.setImageResource(R.drawable.coat_usually)
            textView.setText("コート");
            textView1.setText("50pt");
            fav_point = 50
            select_costume = 1
        }

        val sexy = findViewById<ImageButton>(R.id.sexy)

        sexy.setOnClickListener {

            imageView.setImageResource(R.drawable.dress_usually)
            textView.setText("ワンピース");
            textView1.setText("100pt");
            fav_point = 100
            select_costume = 2
        }

        val neautral = findViewById<ImageButton>(R.id.neautral)

        neautral.setOnClickListener {

            imageView.setImageResource(R.drawable.maid_usually)
            textView.setText("メイド");
            textView1.setText("150pt");
            fav_point = 150
            select_costume = 3
        }

        val sick = findViewById<ImageButton>(R.id.sick)

        sick.setOnClickListener {

            imageView.setImageResource(R.drawable._34554)
            textView.setText("準備中");
            textView1.setText("???pt");
            fav_point = 200
            select_costume = 4
        }

        //現在の好感度を取得
        var Fav = 0
        db.collection("NameChange")
            .document("NameChange")
            .get()
            .addOnCompleteListener{ namechange ->
                if(namechange.isSuccessful){
                    val namechange_doc = namechange.result
                    if (namechange_doc != null && namechange_doc.data != null){
                        Fav = Integer.parseInt((namechange_doc.data?.get("Favorability")).toString())
                    }
                }
            }
        //衣装交換ボタンを押した時に好感度とポイントを比較
        xchange_button.setOnClickListener {
            if(Fav >= fav_point){
                //交換可能であればxxx_unlickの値をFalseからTrueに変更する
                if (select_costume == 1){
                    db.collection("NameChange").document("NameChange").update("coat_unlock","True")
                }else if(select_costume == 2){
                    db.collection("NameChange").document("NameChange").update("dress_unlock","True")
                }else if(select_costume == 3){
                    db.collection("NameChange").document("NameChange").update("maid_unlock","True")
                }else if(select_costume == 4){
                    db.collection("NameChange").document("NameChange").update("uniform_unlock","True")
                }
                Toast.makeText(this, "交換成功！", Toast.LENGTH_SHORT).show()

            }else{
                //ポイントが不足していることをトーストで表示
                Toast.makeText(this, "好感度が不足しています！", Toast.LENGTH_SHORT).show()
            }
        }

        val textView16: TextView = findViewById(R.id.textView16)


        db.collection("NameChange")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    textView16.text = document.data!!["Favorability"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }


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