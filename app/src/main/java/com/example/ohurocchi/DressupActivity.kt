package com.example.ohurocchi

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DressupActivity : AppCompatActivity() {

    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressup)

        val db = Firebase.firestore

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val acceptButton: Button = findViewById(R.id.button)
        var acceptflag = ""
        var nowDress_num = 0

        btnBack.setOnClickListener {
            //ここから遷移用のコード
            val intent = Intent(this,SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }

        val imageView = findViewById<ImageView>(R.id.imageView1)
        val adult = findViewById<ImageButton>(R.id.adult)
        val textview=findViewById<TextView>(R.id.textView4)

        adult.setOnClickListener {

            imageView.setImageResource(R.drawable.coat_usually)
            textview.setText("風呂メイド1");
            acceptflag = "coat_usually"
            nowDress_num = 1
        }

        val sexy = findViewById<ImageButton>(R.id.sexy)

        sexy.setOnClickListener {

            imageView.setImageResource(R.drawable.dress_usually)
            textview.setText("風呂メイド2");
            acceptflag = "dress_usually"
            nowDress_num = 2
        }

        val neautral = findViewById<ImageButton>(R.id.neautral)

        neautral.setOnClickListener {

            imageView.setImageResource(R.drawable.maid_usually)
            textview.setText("風呂メイド3");
            acceptflag = "maid_usually"
            nowDress_num = 3
        }

        val sick = findViewById<ImageButton>(R.id.sick)

        sick.setOnClickListener {

            imageView.setImageResource(R.drawable.uniform_usually)
            textview.setText("風呂メイド4");
            acceptflag = "uniform_usually"
            nowDress_num = 4
        }

        //適用ボタン押下時処理
        acceptButton.setOnClickListener {
            var dress = ""
            db.collection("NameChange")
                .document("NameChange")
                .get()
                .addOnCompleteListener{changedress ->
                    if(changedress.isSuccessful){
                        val changedress_doc = changedress.result
                        if (changedress_doc != null && changedress_doc.data != null){
                            if(nowDress_num == 1){
                                dress = changedress_doc.data?.get("coat_unlock").toString()
                            }
                            else if(nowDress_num == 2){
                                dress = (changedress_doc.data?.get("dress_unlock")).toString()
                            }else if(nowDress_num == 3){
                                dress = (changedress_doc.data?.get("maid_unlock")).toString()
                            }else if(nowDress_num == 4){
                                dress = (changedress_doc.data?.get("uniform_unlock")).toString()
                            }
                            Log.d(TAG,"dress=$dress")
                            if(dress == "True"){
                                db.collection("NameChange").document("NameChange").update("nowDress",acceptflag,"nowDress_num",nowDress_num)
                                Toast.makeText(this, "着せ替え成功！", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this, "衣装をアンロックしてください！", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

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