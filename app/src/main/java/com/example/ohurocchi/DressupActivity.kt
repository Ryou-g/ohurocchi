package com.example.ohurocchi

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DressupActivity : AppCompatActivity() {

    // BGM
    private lateinit var mp: MediaPlayer

    // SE
    private lateinit var soundPool: SoundPool
    private var buttonse = 0
    private var choice = 0
    private var decision = 0
    private var cancel = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressup)

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
            .setMaxStreams(4)
            .build()

        // 効果音をロードしておく
        buttonse = soundPool.load(this, R.raw.button_se, 1)
        choice = soundPool.load(this, R.raw.choice, 1)
        decision = soundPool.load(this, R.raw.decision, 1)
        cancel = soundPool.load(this, R.raw.cancel, 1)

        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener{ soundPool, sampleId, status ->
            Log.d("debug", "sampleId=$sampleId")
            Log.d("debug", "status=$status")
        }

        val db = Firebase.firestore

        val btnBack: ImageButton = findViewById(R.id.btnBack3)
        val acceptButton: Button = findViewById(R.id.button)
        var acceptflag = ""
        var nowDress_num = 0

        btnBack.setOnClickListener {
            //ここから遷移用のコード
            val intent = Intent(this,SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        }

        val imageView = findViewById<ImageView>(R.id.imageView1)
        val adult = findViewById<ImageButton>(R.id.adult)
        val textview=findViewById<TextView>(R.id.textView4)

        adult.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.coat_usually)
            textview.setText("コート");
            acceptflag = "coat_usually"
            nowDress_num = 1
        }

        val sexy = findViewById<ImageButton>(R.id.sexy)

        sexy.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.dress_usually)
            textview.setText("ワンピース");
            acceptflag = "dress_usually"
            nowDress_num = 2
        }

        val neautral = findViewById<ImageButton>(R.id.neautral)

        neautral.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.maid_usually)
            textview.setText("メイド");
            acceptflag = "maid_usually"
            nowDress_num = 3
        }

        val sick = findViewById<ImageButton>(R.id.sick)

        sick.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.uniform_usually)
            textview.setText("制服");
            acceptflag = "uniform_usually"
            nowDress_num = 4
        }


        //適用ボタン押下時処理
        var status = Login_status.getInstance()

        val doc = status.now_Login

        acceptButton.setOnClickListener {
            var dress = ""
            db.collection("NameChange")
                .document(doc)
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
                                //val doc = status.now_Login
                                db.collection("NameChange").document(doc).update("nowDress",acceptflag,"nowDress_num",nowDress_num)
                                Toast.makeText(this, "着せ替え成功！", Toast.LENGTH_SHORT).show()
                                // 効果音 の再生
                                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                                soundPool.play(decision, 0.3f, 0.3f, 0, 0, 1.0f)
                            }else{
                                Toast.makeText(this, "衣装をアンロックしてください！", Toast.LENGTH_SHORT).show()
                                // 効果音 の再生
                                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                                soundPool.play(cancel, 0.3f, 0.3f, 0, 0, 1.0f)
                            }
                        }
                    }

                }


        }



        // ③ 読込処理(CDを入れる)
        mp = MediaPlayer.create(this,R.raw.dress_buy)
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