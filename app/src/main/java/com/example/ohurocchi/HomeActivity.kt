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
    private lateinit var mp: MediaPlayer

    // ① 準備（コンポを部屋に置く・コピペOK）
    private var soundPool // 効果音を鳴らす本体（コンポ）
            : SoundPool? = null
    private var mp3a // 効果音データ（mp3）
            = 0

    private var mp3b // 効果音データ（mp3）
            = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if(random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }

                                }else if(fdate7 >= fdate11 && fdate7 < fdate13) {
                                    imageView.setImageResource(R.drawable.coat_highest)
                                    if( random == 1){
                                        textView23.setText("ね、また今度イルミネーション見に行こうよ！")
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if(random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }

                                }else if(fdate7 >= fdate13) {
                                    imageView.setImageResource(R.drawable.coat_highest)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 2) {
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }
                                }else if(fdate7 in fdate15..fdate9){
                                    imageView.setImageResource(R.drawable.coat_highest)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 2) {
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
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
                                    } else if (random == 2) {
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }
                                }else if(fdate7 >= fdate11 && fdate7 < fdate13) {
                                    imageView.setImageResource(R.drawable.coat_usually)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }
                                }else if(fdate7 >= fdate13){
                                    imageView.setImageResource(R.drawable.coat_usually)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }

                                }else if(fdate7 in fdate15..fdate9){
                                    imageView.setImageResource(R.drawable.coat_usually)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
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
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }
                                }else if(fdate7 >= fdate11 && fdate7 < fdate13){
                                    imageView.setImageResource(R.drawable.coat_bad)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }
                                }else if(fdate7 >= fdate13){
                                    imageView.setImageResource(R.drawable.coat_bad)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }

                                }else if(fdate7 in fdate15..fdate9){
                                    imageView.setImageResource(R.drawable.coat_bad)
                                    if( random == 1){
                                        Log.d(TAG, "skin1=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        Log.d(TAG, "skin2=$random")
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        Log.d(TAG, "skin3=$random")
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
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
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }

                                }else if(date7 >= date9 && date7 < date10 ){
                                    imageView.setImageResource(R.drawable.coat_terrible)
                                    if( random == 1){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }
                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.coat_terrible)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
                                    }
                                }else if(date7 in date11..date8) {
                                    imageView.setImageResource(R.drawable.coat_terrible)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if (random == 3) {
                                        textView23.setText("最近寒いねー、風邪引かないように気をつけないと")
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
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if(random == 3) {
                                        textView23.setText("今度の休みピクニック行かない？")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.dress_highest)
                                    if( random == 1){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if(random == 2){
                                        textView23.setText("今度の休みピクニック行かない？")
                                    } else if(random == 3) {
                                        textView23.setText("ね、今年の夏海行こうよ！")
                                    }
                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.dress_highest)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 2) {
                                        textView23.setText("今から花火買って一緒にやろうよ！")
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    }
                                }else if(date7 in date11..date8) {
                                    imageView.setImageResource(R.drawable.dress_highest)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
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
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 3) {
                                        textView23.setText("最近いい感じじゃない？")
                                    }

                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.dress_usually)
                                    if( random == 1){
                                        textView23.setText("風が気持ちいいねー、眠たくなっちゃう")
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 3) {
                                        textView23.setText("風が気持ちいいねー、眠たくなっちゃう")
                                    }
                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.dress_usually)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 2) {
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.dress_usually)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
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
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                    } else if(random == 3) {
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.dress_bad)
                                    if( random == 1){
                                        textView23.setText("気温がちょうどいいからお出かけしたいねー")
                                    } else if(random == 2){
                                        textView23.setText("気温がちょうどいいからお出かけしたいねー")
                                    } else if(random == 3) {
                                        textView23.setText("気温がちょうどいいからお出かけしたいねー")
                                    }

                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.dress_bad)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.dress_bad)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
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
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.dress_terrible)
                                    if( random == 1){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    }
                                }else if(date7 >= date10) {
                                    imageView.setImageResource(R.drawable.dress_terrible)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    }
                                }else if(date7 in date11..date8) {
                                    imageView.setImageResource(R.drawable.dress_terrible)
                                    if (random == 1) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if (random == 2) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if (random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
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
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                    } else if(random == 3) {
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.maid_highest)
                                    if( random == 1){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if(random == 3) {
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.maid_highest)
                                    if( random == 1){
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if(random == 3) {
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.maid_highest)
                                    if( random == 1){
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                    } else if(random == 2){
                                        textView23.setText("お帰りなさいませ、ご主人様")
                                    } else if(random == 3) {
                                        textView23.setText("お帰りなさいませ、ご主人様")
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
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                    } else if(random == 3) {
                                        textView23.setText("最近いい感じじゃない？")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.maid_usually)
                                    if( random == 1){
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 3) {
                                        textView23.setText("メイドの真似はちょっと...")
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.maid_usually)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.maid_usually)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
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
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                    } else if(random == 3) {
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.maid_bad)
                                    if( random == 1){
                                        textView23.setText("メイド服なんてめったに着ないから新鮮だね")
                                    } else if(random == 2){
                                        textView23.setText("メイド服なんてめったに着ないから新鮮だね")
                                    } else if(random == 3) {
                                        textView23.setText("メイド服なんてめったに着ないから新鮮だね")
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.maid_bad)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.maid_bad)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
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
                                    } else if(random == 2){
                                        textView23.setText("おはよー")
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.maid_terrible)
                                    if( random == 1){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.maid_terrible)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.maid_terrible)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
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
                                    } else if(random == 2){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if(random == 3) {
                                        textView23.setText("今日お弁当作ってきたから一緒に食べよ！")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.uniform_highest)
                                    if( random == 1){
                                        textView23.setText("前より雰囲気良くなったかも！")
                                    } else if(random == 2){
                                        textView23.setText("今日お弁当作ってきたから一緒に食べよ！")
                                    } else if(random == 3) {
                                        textView23.setText("放課後遊ばない？")
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.uniform_highest)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    }

                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.uniform_highest)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
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
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 3) {
                                        textView23.setText("最近いい感じじゃない？")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.uniform_usually)
                                    if( random == 1){
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 2){
                                        textView23.setText("最近いい感じじゃない？")
                                    } else if(random == 3) {
                                        textView23.setText("最近いい感じじゃない？")
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.uniform_usually)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.uniform_usually)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
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
                                    } else if(random == 2){
                                        textView23.setText("おはよう！　今日もがんばろー！")
                                    } else if(random == 3) {
                                        textView23.setText("ちゃんと朝ごはん食べた？")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.uniform_bad)
                                    if( random == 1){
                                        textView23.setText("眠いけど授業中に寝ちゃダメだよ？")
                                    } else if(random == 2){
                                        textView23.setText("眠いけど授業中に寝ちゃダメだよ？")
                                    } else if(random == 3) {
                                        textView23.setText("眠いけど授業中に寝ちゃダメだよ？")
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.uniform_bad)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.uniform_bad)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、お風呂に入ってゆっくり休んでね")
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
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    }
                                }else if(date7 >= date9 && date7 < date10){
                                    imageView.setImageResource(R.drawable.uniform_terrible)
                                    if( random == 1){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 2){
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    } else if(random == 3) {
                                        textView23.setText("最近ちゃんとお風呂入ってる？")
                                    }
                                }else if(date7 >= date10){
                                    imageView.setImageResource(R.drawable.uniform_terrible)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    }
                                }else if(date7 in date11..date8){
                                    imageView.setImageResource(R.drawable.uniform_terrible)
                                    if( random == 1){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if(random == 2){
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
                                    } else if(random == 3) {
                                        textView23.setText("１日お疲れさま～、ちゃんとお風呂に入りなよ？")
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
                                                soundPool!!.play(mp3a, 1f, 1f, 0, 0, 1f)
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(this, "エラーが出ました", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                    else {
                                        Toast.makeText(this, "本日は入浴済みです！", Toast.LENGTH_SHORT).show()
                                        soundPool!!.play(mp3b, 1f, 1f, 0, 0, 1f)
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
            //ここまで
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)//アニメーション
        }
        //ここから設定画面遷移のコード
        val Button_4: Button = findViewById(R.id.Button_4)
        Button_4.setOnClickListener{
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
        mp3b = soundPool!!.load(this, R.raw.voice2, 1)
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
