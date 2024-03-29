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
import androidx.core.app.ShareCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CharaActivity : AppCompatActivity(){

    private var db = Firebase.firestore

    // BGM
    private lateinit var mp: MediaPlayer

    // SE
    private lateinit var soundPool: SoundPool
    private var buttonse = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chara)

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
            .setMaxStreams(2)
            .build()

        // 効果音をロードしておく
        buttonse = soundPool.load(this, R.raw.button_se, 1)


        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener{ soundPool, sampleId, status ->
            Log.d("debug", "sampleId=$sampleId")
            Log.d("debug", "status=$status")
        }

        val circleprogressBar :ProgressBar = findViewById(R.id.circle_progressBar)
        circleprogressBar.visibility = View.INVISIBLE

        val text_koukando :TextView = findViewById(R.id.textView23)
        text_koukando.visibility = View.INVISIBLE

        val view :View = findViewById(R.id.view8)
        view.visibility = View.INVISIBLE

        val text_favarite :TextView = findViewById(R.id.textView16)
        text_favarite.visibility = View.INVISIBLE

        val textView8: TextView = findViewById(R.id.textView8)
        val progressBar: ProgressBar = findViewById(R.id.progressber)

        val textView22: TextView = findViewById(R.id.textView22)


        var status = Login_status.getInstance()

        val doc = status.now_Login


        db.collection("NameChange").document(doc)
            .get()
            .addOnSuccessListener { result ->
                if(result != null) {
                    textView22.text = result.data!!["Favorability"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        // 水平プログレスバーの最大値を設定します
        progressBar.setMax(200);

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
                        progressBar.setProgress(Faboravirity);
                    }
                }
            }


        val imageView = findViewById<ImageView>(R.id.imageView)
        val imageView2 = findViewById<ImageView>(R.id.imageView5)
        val share_button = findViewById<ImageButton>(R.id.share_button)


        db.collection("NameChange").document(doc)
            .get()
            .addOnSuccessListener { result ->
                if(result != null) {
                    textView8.text = result.data!!["name"].toString()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        db.collection("NameChange").document(doc).get()
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
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            //ここまで
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
        Button_4.setOnClickListener{
            //ここから遷移用のコード
            val intent = Intent(this,SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        mp = MediaPlayer.create(this,R.raw.chara)
        mp.setVolume(0.4F,0.4F)
        mp.isLooping = true
        mp.start()
        share_button.setOnClickListener() {
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)

            showShareChooser()
        }
    }

    //share compatの実装
    fun showShareChooser() {

        val chooserTitle = "おふろっち"
        val subject = "メールの件名"
        val text = "#おふろっち"


        val builder = ShareCompat.IntentBuilder.from(this)
        builder.setChooserTitle(chooserTitle) // シェアする時のタイトル
            .setSubject(subject) // 件名。使われ方はシェアされた側のアプリによる
            .setText(text) // 本文。使われ方はシェアされた側のアプリによる
            .setType("text/plain") // ストリームで指定したファイルのMIMEタイプ



        // 結果を受け取らなくても良い場合は、ビルダーからそのまま開始できる
        builder.startChooser()
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