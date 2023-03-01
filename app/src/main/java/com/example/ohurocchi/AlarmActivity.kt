package com.example.ohurocchi

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AlarmActivity : AppCompatActivity(){

    // BGM
    private lateinit var mp: MediaPlayer

    // SE
    private lateinit var soundPool: SoundPool
    private var buttonse = 0
    private var decision = 0
    private var cancel = 0

    lateinit var text_et: EditText

    private var am: AlarmManager? = null
    private var pending: PendingIntent? = null
    private val requestCode = 1

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

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

        val imageView30 = findViewById<ImageView>(R.id.imageView30)

        var status = Login_status.getInstance()
        val doc = status.now_Login

        db.collection("NameChange").document(doc).get()
            .addOnCompleteListener { background ->
                if(background.isSuccessful){
                    val background_document = background.result
                    if (background_document != null && background_document.data != null){
                        //var rrr =dress_document.data?.get("Favorability")
                        imageView30.setImageResource(getResources().getIdentifier(background_document.data?.get("nowBackground") as String?,"drawable", getPackageName()))
                    }
                }

            }

        text_et = findViewById<EditText>(R.id.text_et) //EditText（入力エリア）

        //EditTextのクリックイベントを設定
        text_et.setOnClickListener {
            showTimePickerDialog()
        }

        //アラームを設定するボタン
        val buttonAlarm: Button = findViewById(R.id.buttonAlarm)
        buttonAlarm.setOnClickListener {

            if (text_et.text.isEmpty()) {
                Toast.makeText(this, "入力してください", Toast.LENGTH_LONG).show();
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(cancel, 0.3f, 0.3f, 0, 0, 1.0f)
            } else {
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(decision, 0.3f, 0.3f, 0, 0, 1.0f)

                val calendar = java.util.Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()

                var number: String = text_et.text.toString()
                Log.d("TAG", number)
                val HH: String = number.toString().take(2)
                val hh: Int = HH.toInt()
                Log.d("TAG", HH)
                val MM: String = number.toString().takeLast(2)
                val mm: Int = MM.toInt()

                calendar.set(java.util.Calendar.HOUR_OF_DAY, hh)
                calendar.set(java.util.Calendar.MINUTE, mm)
                calendar.set(java.util.Calendar.SECOND, 0)

                val intent = Intent(applicationContext, AlarmNotification::class.java)
                intent.putExtra("RequestCode", requestCode)
                pending = PendingIntent.getBroadcast(
                    applicationContext, requestCode, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                // アラームをセットする
                am = getSystemService(ALARM_SERVICE) as AlarmManager
                if (am != null) {
                    am!!.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis, pending
                    )

                    // トーストで設定されたことをを表示
                    Toast.makeText(
                        applicationContext,
                        "入浴する時間を決定しました！", Toast.LENGTH_SHORT
                    ).show()
                    Log.d("debug", "start")
                }
            }
        }
        val btnBack: ImageButton = findViewById(R.id.btn_back30)

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

    /* 時間ダーダイアログを開くためのメソッド */
    fun showTimePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            //EditTextに選択された時間を設定
            text_et.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }

        //タイムピッカーダイアログを生成および設定
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
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