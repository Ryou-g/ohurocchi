package com.example.ohurocchi

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DressbuyActivity : AppCompatActivity() {

    // BGM
    private lateinit var mp: MediaPlayer

    // SE
    private lateinit var soundPool: SoundPool
    private var buttonse = 0
    private var choice = 0
    private var cancel = 0
    private var decision = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressexchange)

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

        val xchange_button = findViewById<Button>(R.id.button)

        //ここからホーム画面遷移のコード
        val Button_1: Button = findViewById(R.id.Button_1)
        Button_1.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, HomeActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)
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
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)
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
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
        //ここから設定画面遷移のコード
        val Button_4: Button = findViewById(R.id.Button_4)
        Button_4.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        mp = MediaPlayer.create(this,R.raw.dress_buy)
        mp.setVolume(0.4F,0.4F)
        mp.isLooping = true
        mp.start()


        val imageView = findViewById<ImageView>(R.id.imageView18)
        val textView = findViewById<TextView>(R.id.textView4)
        val textView1 = findViewById<TextView>(R.id.textView5)


        val adult = findViewById<ImageButton>(R.id.adult)
        var fav_point = 0
        var select_costume = 0

        adult.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.coat_usually)
            textView.setText("コート");
            textView1.setText("50pt");
            fav_point = 50
            select_costume = 1
        }

        val sexy = findViewById<ImageButton>(R.id.sexy)

        sexy.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.dress_usually)
            textView.setText("ワンピース");
            textView1.setText("100pt");
            fav_point = 100
            select_costume = 2
        }

        val neautral = findViewById<ImageButton>(R.id.neautral)

        neautral.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.maid_usually)
            textView.setText("メイド");
            textView1.setText("150pt");
            fav_point = 150
            select_costume = 3
        }

        val sick = findViewById<ImageButton>(R.id.sick)

        sick.setOnClickListener {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(choice, 0.3f, 0.3f, 0, 0, 1.0f)

            imageView.setImageResource(R.drawable.uniform_usually)
            textView.setText("制服");
            textView1.setText("200pt");
            fav_point = 200
            select_costume = 4
        }
        var status = Login_status.getInstance()
        val doc = status.now_Login
        //現在の好感度を取得
        var Fav = 0
        db.collection("NameChange")
            .document(doc)
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
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(decision, 0.3f, 0.3f, 0, 0, 1.0f)
            }else{
                //ポイントが不足していることをトーストで表示
                Toast.makeText(this, "好感度が不足しています！", Toast.LENGTH_SHORT).show()
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(cancel, 0.3f, 0.3f, 0, 0, 1.0f)
            }
        }

        val progressBar1: ProgressBar = findViewById(R.id.circle_progressBar)

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