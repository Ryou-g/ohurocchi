package com.example.ohurocchi

import android.content.ContentValues
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


class NameActivity : AppCompatActivity() {

    // BGM
    private lateinit var mp: MediaPlayer

    // SE
    private lateinit var soundPool: SoundPool
    private var buttonse = 0
    private var cancel = 0
    private var decision = 0

    private lateinit var etName: EditText
    private lateinit var btnUpdate: Button

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_namechange)

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
            .setMaxStreams(3)
            .build()

        // 効果音をロードしておく
        buttonse = soundPool.load(this, R.raw.button_se, 1)
        decision = soundPool.load(this, R.raw.decision, 1)
        cancel = soundPool.load(this, R.raw.cancel, 1)

        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener{ soundPool, sampleId, status ->
            Log.d("debug", "sampleId=$sampleId")
            Log.d("debug", "status=$status")
        }

        val imageView2 = findViewById<ImageView>(R.id.imageView7)

        var status = Login_status.getInstance()
        val doc = status.now_Login

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

        etName = findViewById(R.id.stv_name)
        btnUpdate = findViewById(R.id.sbtn_Update)

        btnUpdate.setOnClickListener {

            if (etName.text.isEmpty()) {
                Toast.makeText(this, "入力してください", Toast.LENGTH_LONG).show();
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(cancel, 0.3f, 0.3f, 0, 0, 1.0f)
            } else {
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(decision, 0.3f, 0.3f, 0, 0, 1.0f)
                //ボタンを押されたらまず現在の好感度情報を取得
                var Fa: Int = 10
                var nowDress = 0
                var nowBack = ""
                var Fav = db.collection("NameChange")
                    .document(doc)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val document = task.result
                            if (document != null && document.data != null) {
                                Log.d(ContentValues.TAG, "getData")
                                //Log.d(ContentValues.TAG, "DocumentSnapshot data: " + (document.data?.get("Favorability")?.javaClass?.kotlin))
                                Log.d(
                                    ContentValues.TAG,
                                    "name=${document.data?.get("Favorability")}"
                                )
                                Log.d(ContentValues.TAG, "name=${document.data?.get("nowDress")}")
                                Log.d(
                                    ContentValues.TAG,
                                    "name=${document.data?.get("nowBackground")}"
                                )
                                Log.d(
                                    ContentValues.TAG,
                                    "name=${document.data?.get("nowDress_num")}"
                                )
                                Fa =
                                    Integer.parseInt((document.data?.get("Favorability")).toString())
                                nowDress =
                                    Integer.parseInt((document.data?.get("nowDress_num")).toString())
                                nowBack = (document.data?.get("nowBackground")).toString()
                                Log.d(ContentValues.TAG, "FA=$Fa")
                                val sName = etName.text.toString().trim()

                                //新しいユーザー名と取得した好感度情報をセット
                                val userMap = hashMapOf(
                                    "name" to sName,
                                    "Favorability" to Fa,
                                    "nowDress_num" to nowDress,
                                    "nowBackground" to nowBack
                                )

                                //セットした情報を基にデータを更新する
                                db.collection("NameChange").document(doc).update("name", sName)
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "名前の変更が完了しました！", Toast.LENGTH_SHORT)
                                            .show()
                                        etName.text.clear()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(this, "エラーが発生しました", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                            } else {
                                Log.d(ContentValues.TAG, "No such document")
                            }
                        } else {
                            Log.d(ContentValues.TAG, "get failed with " + task.exception)
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.d(
                            ContentValues.TAG,
                            "Error adding document" + e
                        )
                    }

            }
        }


        val btnBack: ImageButton = findViewById(R.id.btnBack4)

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