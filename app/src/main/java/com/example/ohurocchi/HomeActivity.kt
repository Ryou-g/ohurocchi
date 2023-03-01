package com.example.ohurocchi

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ohurocchi.databinding.ActivityHomeBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    // BGM
    private lateinit var mp: MediaPlayer

    // SE
    private lateinit var soundPool: SoundPool
    private var buttonse = 0

    // Voiceデータ（mp3）
    private lateinit var mp3a: MediaPlayer
    private lateinit var mp3b: MediaPlayer
    private lateinit var mp3c: MediaPlayer
    private lateinit var mp3d: MediaPlayer
    private lateinit var mp3e: MediaPlayer
    private lateinit var mp3f: MediaPlayer
    private lateinit var mp3g: MediaPlayer
    private lateinit var mp3h: MediaPlayer
    private lateinit var mp3i: MediaPlayer
    private lateinit var mp3j: MediaPlayer
    private lateinit var mp3k: MediaPlayer
    private lateinit var mp3l: MediaPlayer
    private lateinit var mp3m: MediaPlayer
    private lateinit var mp3n: MediaPlayer
    private lateinit var mp3o: MediaPlayer
    private lateinit var mp3p: MediaPlayer
    private lateinit var mp3q: MediaPlayer
    private lateinit var mp3r: MediaPlayer
    private lateinit var mp3s: MediaPlayer
    private lateinit var mp3t: MediaPlayer
    private lateinit var mp3u: MediaPlayer
    private lateinit var mp3v: MediaPlayer
    private lateinit var mp3w: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        //imageViewの取得
        val imageView = findViewById<ImageView>(R.id.imageView)
        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val textView23 = findViewById<TextView>(R.id.textView25)

        val bathlog : Button = findViewById(R.id.bathlog)

        val db = Firebase.firestore


        //日付処理
        val cal1 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN)   //現在時刻を取得する

        val date1 = cal1.time

        //変換フォーマット
        val sd = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        //Calender型からDate型へ変換
        val fdate1 = sd.format(date1)   //書いている時刻

        //0:0:0にする処理
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val min = Calendar.getInstance().get(Calendar.MINUTE)
        val sec = Calendar.getInstance().get(Calendar.SECOND)

        cal1.add(Calendar.HOUR, -hour)
        cal1.add(Calendar.MINUTE, -min)
        cal1.add(Calendar.SECOND,-sec)

        val date2 = cal1.time
        val starttime = sd.format(date2)

        //ユーザー分別処理
        var status = Login_status.getInstance()
        val doc = status.now_Login

        Log.d(TAG,"hour=$hour")
        Log.d(TAG,"hour=$min")
        Log.d(TAG,"hour=$sec")

        Log.d(TAG,"starttime = $starttime")
        Log.d(TAG,"time=$fdate1")


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


        db.collection("NameChange").document(doc).get()
            .addOnCompleteListener { dress ->
                if(dress.isSuccessful){
                    val dress_document = dress.result
                    if (dress_document != null && dress_document.data != null) {
                        //var rrr =dress_document.data?.get("Favorability")
                        //imageView.setImageResource(getResources().getIdentifier(dress_document.data?.get("nowDress") as String?,"drawable", getPackageName()))
                        var Fav =
                            Integer.parseInt((dress_document.data?.get("Favorability")).toString())
                        val dress_num =
                            Integer.parseInt((dress_document.data?.get("nowDress_num")).toString())
                        Log.d(TAG, "dress_num=$dress_num")
                        Log.d(TAG, "Fav=$Fav")
                        if (dress_num == 1) {
                            if (Fav >= 150) {
                                val random = (1..3).random()
                                //日付処理
                                //val cal7 = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN)   //現在時刻を取得する
                                val cal7 = LocalTime.now()
                                val date7 = cal7

                                //変換フォーマット
                                //val sd = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")



                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)


                                val fdate6 = date7
                                val fdate7 = fdate6

                                val fdate8 = date8
                                val fdate9 = fdate8

                                val fdate10 = date9
                                val fdate11 = fdate10

                                val fdate12 = date10
                                val fdate13 = fdate12

                                val fdate14 = date11
                                val fdate15 = fdate14

                                if(fdate7 > fdate9 && fdate7 < fdate11) {
                                    //val morning = 1
                                    imageView.setImageResource(R.drawable.coat_highest)
                                    if( random == 1){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }

                                }else if(fdate7 >= fdate11 && fdate7 < fdate13) {
                                    imageView.setImageResource(R.drawable.coat_highest)
                                    if( random == 1){
                                        textView23.setText("ね、また今度イルミネーション見に行こうよ！")
                                        mp3f.start()
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }

                                }else if(fdate7 >= fdate13) {
                                    imageView.setImageResource(R.drawable.coat_highest)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 2) {
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }else if(fdate7 in fdate15..fdate9){
                                    imageView.setImageResource(R.drawable.coat_highest)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 2) {
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }
                            }else if(Fav >= 100){
                                //日付処理
                                val cal7 = LocalTime.now()
                                val date7 = cal7


                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)


                                val fdate6 = date7
                                val fdate7 = fdate6

                                val fdate8 = date8
                                val fdate9 = fdate8

                                val fdate10 = date9
                                val fdate11 = fdate10

                                val fdate12 = date10
                                val fdate13 = fdate12

                                val fdate14 = date11
                                val fdate15 = fdate14

                                val random = (1..3).random()
                                if(fdate7 > fdate9 && fdate7 < fdate11) {
                                    //val morning = 1
                                    imageView.setImageResource(R.drawable.coat_usually)
                                    if (random == 1) {
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if (random == 2) {
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }else if(fdate7 >= fdate11 && fdate7 < fdate13) {
                                    imageView.setImageResource(R.drawable.coat_usually)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }else if(fdate7 >= fdate13){
                                    imageView.setImageResource(R.drawable.coat_usually)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }

                                }else if(fdate7 in fdate15..fdate9){
                                    imageView.setImageResource(R.drawable.coat_usually)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }


                            }else if(Fav >= 50){
                                //日付処理
                                val cal7 = LocalTime.now()
                                val date7 = cal7


                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)


                                val fdate6 = date7
                                val fdate7 = fdate6

                                val fdate8 = date8
                                val fdate9 = fdate8

                                val fdate10 = date9
                                val fdate11 = fdate10

                                val fdate12 = date10
                                val fdate13 = fdate12

                                val fdate14 = date11
                                val fdate15 = fdate14

                                val random = (1..3).random()

                                if(fdate7 > fdate9 && fdate7 < fdate11) {
                                    imageView.setImageResource(R.drawable.coat_bad)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }else if(fdate7 >= fdate11 && fdate7 < fdate13){
                                    imageView.setImageResource(R.drawable.coat_bad)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }else if(fdate7 >= fdate13){
                                    imageView.setImageResource(R.drawable.coat_bad)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }

                                }else if(fdate7 in fdate15..fdate9){
                                    imageView.setImageResource(R.drawable.coat_bad)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }


                            }else{

                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)

                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.coat_terrible)
                                    if( random == 1){
                                        textView23.setText("おはよー")
                                        mp3i.start()
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }

                                }else if(date7 >= date9 && date7 < date10 ){
                                    imageView.setImageResource(R.drawable.coat_terrible)
                                    if( random == 1){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.coat_terrible)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }else if(date7 in date11..date8) {
                                    imageView.setImageResource(R.drawable.coat_terrible)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                        mp3e.start()
                                    }
                                }

                            }
                        }else if(dress_num == 2){
                            if(Fav >= 150){
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)

                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.dress_highest)
                                    if( random == 1){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if(random == 3) {
                                        textView23.setText("今度の休みピクニック行かない？")
                                        mp3l.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.dress_highest)
                                    if( random == 1){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if(random == 2){
                                        textView23.setText("今度の休みピクニック行かない？")
                                        mp3l.start()
                                    } else if(random == 3) {
                                        textView23.setText("ね、今年の夏海行こうよ！")
                                        mp3m.start()
                                    }
                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.dress_highest)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 2) {
                                        textView23.setText("今から花火買って一緒にやろうよ！")
                                        mp3n.start()
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }else if(date7 in date11..date8) {
                                    imageView.setImageResource(R.drawable.dress_highest)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }

                            }else if(Fav >= 100){
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)

                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.dress_usually)
                                    if( random == 1){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    }

                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.dress_usually)
                                    if( random == 1){
                                        textView23.setText("風が気持ちいいねー、眠たくなっちゃう")
                                        mp3o.start()
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 3) {
                                        textView23.setText("風が気持ちいいねー、眠たくなっちゃう")
                                        mp3o.start()
                                    }
                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.dress_usually)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 2) {
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.dress_usually)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }
                            }else if(Fav >= 50){
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)
                                val random = (1..3).random()

                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.dress_bad)
                                    if( random == 1){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 3) {
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.dress_bad)
                                    if( random == 1){
                                        textView23.setText("気温がちょうどいいからお出かけしたいねー")
                                        mp3p.start()
                                    } else if(random == 2){
                                        textView23.setText("気温がちょうどいいからお出かけしたいねー")
                                        mp3p.start()
                                    } else if(random == 3) {
                                        textView23.setText("気温がちょうどいいからお出かけしたいねー")
                                        mp3p.start()
                                    }

                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.dress_bad)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.dress_bad)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }

                            }else{
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)

                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.dress_terrible)
                                    if( random == 1){
                                        textView23.setText("おはよー")
                                        mp3i.start()
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.dress_terrible)
                                    if( random == 1){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    }
                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.dress_terrible)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    }
                                }else if(date7 in date11..date8) {
                                    imageView.setImageResource(R.drawable.dress_terrible)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    }
                                }
                            }
                        }else if(dress_num == 3){
                            val random = (1..3).random()
                            if(Fav >= 150){
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.maid_highest)
                                    if( random == 1){
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                        mp3q.start()
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 3) {
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.maid_highest)
                                    if( random == 1){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if(random == 3) {
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                        mp3q.start()
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.maid_highest)
                                    if( random == 1){
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                        mp3q.start()
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if(random == 3) {
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                        mp3q.start()
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.maid_highest)
                                    if( random == 1){
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                        mp3q.start()
                                    } else if(random == 2){
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                        mp3q.start()
                                    } else if(random == 3) {
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                        mp3q.start()
                                    }
                                }
                                //imageView10.setImageResource(R.drawable.e1300_1)
                            }else if(Fav >= 100){
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)
                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.maid_usually)
                                    if( random == 1){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.maid_usually)
                                    if( random == 1){
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 3) {
                                        textView23.setText("メイドの真似はちょっと...")
                                        mp3r.start()
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.maid_usually)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.maid_usually)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }

                            }else if(Fav >= 50){
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)
                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.maid_bad)
                                    if( random == 1){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 3) {
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.maid_bad)
                                    if( random == 1){
                                        textView23.setText("メイド服なんてめったに着ないから新鮮だね")
                                        mp3s.start()
                                    } else if(random == 2){
                                        textView23.setText("メイド服なんてめったに着ないから新鮮だね")
                                        mp3s.start()
                                    } else if(random == 3) {
                                        textView23.setText("メイド服なんてめったに着ないから新鮮だね")
                                        mp3s.start()
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.maid_bad)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.maid_bad)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }
                            }else{
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)
                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.maid_terrible)
                                    if( random == 1){
                                        textView23.setText("おはよー")
                                        mp3i.start()
                                    } else if(random == 2){
                                        textView23.setText("おはよー")
                                        mp3i.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.maid_terrible)
                                    if( random == 1){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.maid_terrible)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.maid_terrible)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    }
                                }
                            }
                        }else if(dress_num == 4){
                            if(Fav >= 150){
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)

                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.uniform_highest)
                                    if( random == 1){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if(random == 3) {
                                        textView23.setText("今日お弁当作ってきたから一緒に食べよ！")
                                        mp3t.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.uniform_highest)
                                    if( random == 1){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                        mp3d.start()
                                    } else if(random == 2){
                                        textView23.setText("今日お弁当作ってきたから一緒に食べよ！")
                                        mp3t.start()
                                    } else if(random == 3) {
                                        textView23.setText("放課後遊ばない？")
                                        mp3u.start()
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.uniform_highest)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }

                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.uniform_highest)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }
                            }else if(Fav >= 100){
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)
                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.uniform_usually)
                                    if( random == 1){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.uniform_usually)
                                    if( random == 1){
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近いい感じじゃない？")
                                        mp3h.start()
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.uniform_usually)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.uniform_usually)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }

                            }else if(Fav >= 50){
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)
                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.uniform_bad)
                                    if( random == 1){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                        mp3c.start()
                                    } else if(random == 3) {
                                        textView23.setText("ちゃんと朝ごはん食べた？")
                                        mp3v.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.uniform_bad)
                                    if( random == 1){
                                        textView23.setText("眠いけど授業中に寝ちゃダメだよ？")
                                        mp3w.start()
                                    } else if(random == 2){
                                        textView23.setText("眠いけど授業中に寝ちゃダメだよ？")
                                        mp3w.start()
                                    } else if(random == 3) {
                                        textView23.setText("眠いけど授業中に寝ちゃダメだよ？")
                                        mp3w.start()
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.uniform_bad)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.uniform_bad)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                        mp3k.start()
                                    }
                                }

                            }else{
                                val date7 = LocalTime.now()

                                val date8 = LocalTime.of(6, 0, 0)
                                val date9 = LocalTime.of(12, 0, 0)
                                val date10 = LocalTime.of(18, 0, 0)
                                val date11 = LocalTime.of(0, 0, 0)
                                val random = (1..3).random()
                                if(date7 > date8 && date7 < date9){
                                    imageView.setImageResource(R.drawable.uniform_terrible)
                                    if( random == 1){
                                        textView23.setText("おはよー")
                                        mp3i.start()
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.uniform_terrible)
                                    if( random == 1){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                        mp3j.start()
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.uniform_terrible)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.uniform_terrible)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                        mp3g.start()
                                    }
                                }

                                //imageView10.setImageResource(R.drawable.e1300_1)
                                //Log.d(TAG,"okdayo")
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

        binding.button1.setOnClickListener {
            // Bathlogをインスタンス化
            val bathlog = Bathlog(
                //title = binding.button1.text.toString(),
                title = message,
                createdAt = fdate1,
                uid = status.now_Login
            )
            Log.d(TAG,"インスタンス化")
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)


            // ④ 再生処理(再生ボタン)
            //soundPool!!.play(mp3a, 1f, 1f, 0, 0, 1f)

            //soundPool!!.play(mp3b, 1f, 1f, 0, 0, 1f)

            //好感度を取得する
            var Fa: Int = 10
            var Fav = db.collection("NameChange")
                .document(doc)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.data != null) {
                            Log.d(TAG, "getData")
                            Log.d(TAG, "DocumentSnapshot data: " + (document.data?.get("Favorability")?.javaClass?.kotlin))
                            Fa = Integer.parseInt((document.data?.get("Favorability")).toString())
                            Log.d(TAG,"FA=$Fa")

                            //一日ごとの入浴状況を確認
                            val user_id = status.now_Login
                            val hoge = db.collection("BAthlog").whereEqualTo("uid",user_id).orderBy("createdAt").startAt(starttime).endAt(fdate1)
                                .get()
                                .addOnSuccessListener { result ->
                                    var cnt = 0
                                    for(doc in result){
                                        Log.d(TAG,"resultt=$doc")
                                        cnt += 1
                                    }
                                    if(cnt == 0){
                                        Fa += 5
                                        db.collection("NameChange").document(doc).update("Favorability",Fa)
                                        Log.d(TAG,"FA2=$Fa")
                                        db.collection("BAthlog")
                                            .add(bathlog)
                                            .addOnSuccessListener { documentReference ->
                                                Log.d(
                                                    ContentValues.TAG,
                                                    "DocumentSnapshot added with ID: ${documentReference.id}"
                                                )
                                                Toast.makeText(this, "お風呂に入りました！", Toast.LENGTH_SHORT).show()
                                                mp3a.start()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(this, "エラーが出ました", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                    else {
                                        Toast.makeText(this, "本日は入浴済みです！", Toast.LENGTH_SHORT).show()
                                        mp3b.start()
                                    }
                                }
                                .addOnFailureListener{
                                    Log.d(TAG,"Error Error Error")
                                }
                            Log.d(TAG,"hoge=$hoge")


                        } else {
                            Log.d(TAG, "No such document")
                        }
                    } else {
                        Log.d(TAG, "get failed with " + task.exception)
                    }
                }
                .addOnFailureListener { e -> Log.d(TAG, "Error adding document" + e)}

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


        bathlog.setOnClickListener {
            val intent = Intent(applicationContext,BathlogActivity::class.java)
            startActivity(intent)
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(buttonse, 0.3f, 0.3f, 0, 0, 1.0f)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)//アニメーション
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
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out) //アニメーション
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
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)//アニメーション
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
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)//アニメーション
        }

        // ③ 読込処理(CDを入れる)
        mp3a = MediaPlayer.create(this, R.raw.sugoi)
        mp3b = MediaPlayer.create(this, R.raw.are)
        mp3c = MediaPlayer.create(this, R.raw.hiten_ohayo)
        mp3d = MediaPlayer.create(this, R.raw.hunniki)
        mp3e = MediaPlayer.create(this, R.raw.samui_kaze)
        mp3f = MediaPlayer.create(this, R.raw.irumi)
        mp3g = MediaPlayer.create(this, R.raw.itinitiotukare)
        mp3h = MediaPlayer.create(this, R.raw.iikanji)
        mp3i = MediaPlayer.create(this, R.raw.ohayo)
        mp3j = MediaPlayer.create(this, R.raw.hurohaiiteru)
        mp3k = MediaPlayer.create(this, R.raw.otukare_cyanto)
        mp3l = MediaPlayer.create(this, R.raw.picnic)
        mp3m = MediaPlayer.create(this, R.raw.sea)
        mp3n = MediaPlayer.create(this, R.raw.hanabi)
        mp3o = MediaPlayer.create(this, R.raw.kazekimoti)
        mp3p = MediaPlayer.create(this, R.raw.odekake)
        mp3q = MediaPlayer.create(this, R.raw.gosyujinnsama)
        mp3r = MediaPlayer.create(this, R.raw.meidonomane)
        mp3s = MediaPlayer.create(this, R.raw.sinnsenn)
        mp3t = MediaPlayer.create(this, R.raw.obennto)
        mp3u = MediaPlayer.create(this, R.raw.houkago)
        mp3v = MediaPlayer.create(this, R.raw.asagohann)
        mp3w = MediaPlayer.create(this, R.raw.netyadame)

        mp3a.setVolume(1F,1F)
        mp3b.setVolume(1F,1F)
        mp3c.setVolume(1F,1F)
        mp3d.setVolume(1F,1F)
        mp3e.setVolume(1F,1F)
        mp3f.setVolume(1F,1F)
        mp3g.setVolume(1F,1F)
        mp3h.setVolume(1F,1F)
        mp3i.setVolume(1F,1F)
        mp3j.setVolume(1F,1F)
        mp3k.setVolume(1F,1F)
        mp3l.setVolume(1F,1F)
        mp3m.setVolume(1F,1F)
        mp3n.setVolume(1F,1F)
        mp3o.setVolume(1F,1F)
        mp3p.setVolume(1F,1F)
        mp3q.setVolume(1F,1F)
        mp3r.setVolume(1F,1F)
        mp3s.setVolume(1F,1F)
        mp3t.setVolume(1F,1F)
        mp3u.setVolume(1F,1F)
        mp3v.setVolume(1F,1F)
        mp3w.setVolume(1F,1F)
        mp = MediaPlayer.create(this,R.raw.bath)
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
        mp3a.pause()
        mp3b.pause()
        mp3c.pause()
        mp3d.pause()
        mp3e.pause()
        mp3f.pause()
        mp3g.pause()
        mp3h.pause()
        mp3i.pause()
        mp3j.pause()
        mp3k.pause()
        mp3l.pause()
        mp3m.pause()
        mp3n.pause()
        mp3o.pause()
        mp3p.pause()
        mp3q.pause()
        mp3r.pause()
        mp3s.pause()
        mp3t.pause()
        mp3u.pause()
        mp3v.pause()
        mp3w.pause()
    }

    //７）終了・メモリの解放
    override fun onDestroy() {
        super.onDestroy()
        mp.stop() //終了・停止
        mp.release() //解放

        mp3a.stop() //終了・停止
        mp3b.stop() //終了・停止
        mp3c.stop() //終了・停止
        mp3d.stop() //終了・停止
        mp3e.stop() //終了・停止
        mp3f.stop() //終了・停止
        mp3g.stop() //終了・停止
        mp3h.stop() //終了・停止
        mp3i.stop() //終了・停止
        mp3j.stop() //終了・停止
        mp3k.stop() //終了・停止
        mp3l.stop() //終了・停止
        mp3m.stop() //終了・停止
        mp3n.stop() //終了・停止
        mp3o.stop() //終了・停止
        mp3p.stop() //終了・停止
        mp3q.stop() //終了・停止
        mp3r.stop() //終了・停止
        mp3s.stop() //終了・停止
        mp3t.stop() //終了・停止
        mp3u.stop() //終了・停止
        mp3v.stop() //終了・停止
        mp3w.stop() //終了・停止

        mp3a.release() //解放
        mp3b.release() //解放
        mp3c.release() //解放
        mp3d.release() //解放
        mp3e.release() //解放
        mp3f.release() //解放
        mp3g.release() //解放
        mp3h.release() //解放
        mp3i.release() //解放
        mp3j.release() //解放
        mp3k.release() //解放
        mp3l.release() //解放
        mp3m.release() //解放
        mp3n.release() //解放
        mp3o.release() //解放
        mp3p.release() //解放
        mp3q.release() //解放
        mp3r.release() //解放
        mp3s.release() //解放
        mp3t.release() //解放
        mp3u.release() //解放
        mp3v.release() //解放
        mp3w.release() //解放

    }
}
