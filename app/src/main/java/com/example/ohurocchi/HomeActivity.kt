package com.example.ohurocchi

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ohurocchi.databinding.ActivityHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*



class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mp: MediaPlayer
    
    // ① 準備（コンポを部屋に置く・コピペOK）
    private var soundPool // 効果音を鳴らす本体（コンポ）
            : SoundPool? = null
    private var mp3a // 効果音データ（mp3）
            = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore


        //日付処理
        val cal1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN)   //現在時刻を取得する


        val date1 = cal1.time

        //変換フォーマット
        val sd = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        //Calender型からDate型へ変換
        val fdate1 = sd.format(date1)   //書いている時刻

        var message = ""
        var rand_num = (0..2).random()
        Log.d(TAG,"rand=$rand_num")
        if( rand_num == 0){
            message = "おふろに入った"
        }
        else if(rand_num == 1){
            message = "にゅうよく"
        }
        else{
            message = "ふろりだ"
        }

        binding.button1.setOnClickListener {

            // Bathlogをインスタンス化
            val bathlog = Bathlog(
                //title = binding.button1.text.toString(),
                title = message,
                createdAt = fdate1
            )
            Log.d(TAG,"インスタンス化")

            db.collection("BAthlog")
                .add(bathlog)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: ${documentReference.id}"
                    )
                    Toast.makeText(this, "お風呂に入りました！", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "エラーが出ました", Toast.LENGTH_SHORT).show()
                }
            // ④ 再生処理(再生ボタン)
            soundPool!!.play(mp3a, 1f, 1f, 0, 0, 1f)

            //好感度を取得する
            var Fa: Int = 10
            var Fav = db.collection("NameChange")
                .document("NameChange")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.data != null) {
                            Log.d(TAG, "getData")
                            Log.d(TAG, "DocumentSnapshot data: " + (document.data?.get("Favorability")?.javaClass?.kotlin))
                            Fa = Integer.parseInt((document.data?.get("Favorability")).toString())
                            Log.d(TAG,"FA=$Fa")
                            Fa += 5
                            db.collection("NameChange").document("NameChange").update("Favorability",Fa)
                        } else {
                            Log.d(TAG, "No such document")
                        }
                    } else {
                        Log.d(TAG, "get failed with " + task.exception)
                    }
                }
                .addOnFailureListener { e -> Log.d(TAG, "Error adding document" + e)}

        }




        //ここからホーム画面遷移のコード
        val imageButton3: ImageButton = findViewById(R.id.imageButton3)
        imageButton3.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, HomeActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)//アニメーション
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
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) //アニメーション
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
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)//アニメーション
        }
        //ここから設定画面遷移のコード
        val imageButton4: ImageButton = findViewById(R.id.imageButton4)
        imageButton4.setOnClickListener{
            //ここから遷移用のコード
            val intent = Intent(this,SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)//アニメーション
        }

        // ② 初期化（電源を入れる・コピペOK）
        soundPool = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        } else {
            val attr = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            SoundPool.Builder()
                .setAudioAttributes(attr)
                .setMaxStreams(5)
                .build()
        }
        // ③ 読込処理(CDを入れる)
        mp3a = soundPool!!.load(this, R.raw.voice1, 1)
        mp = MediaPlayer.create(this,R.raw.bath)
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

