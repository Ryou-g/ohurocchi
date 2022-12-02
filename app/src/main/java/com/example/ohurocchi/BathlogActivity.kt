package com.example.ohurocchi

import TaskAdapter
import android.content.ContentValues.TAG
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.example.ohurocchi.databinding.ActivityBathlogBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class BathlogActivity : AppCompatActivity() {

    private lateinit var mp: MediaPlayer

    private lateinit var binding: ActivityBathlogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bathlog)
        binding = ActivityBathlogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendarView = findViewById<View>(R.id.calendarView) as CalendarView

        val db = Firebase.firestore

//        val taskAdapter = TaskAdapter()
//        binding.recyclerView.adapter = taskAdapter
//        binding.recyclerView.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //日付処理
        val cal1 =
            Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN)   //現在時刻を取得する
        //val date1 = cal1.time
        val cal2 =
            Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN)   //現在時刻を取得する

        //デバック用
        //cal1.add(Calendar.MONTH, -1)
        //cal2.add(Calendar.MONTH, -1)


        //現在の年月日日時秒を取得
        val Year = Calendar.getInstance().get(Calendar.YEAR)
        val Month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val min = Calendar.getInstance().get(Calendar.MINUTE)
        val sec = Calendar.getInstance().get(Calendar.SECOND)

        //当年の1日0:0:0に設定
        //        cal1.add(Calendar.YEAR, -Year)
//        cal1.add(Calendar.MONTH, -Month)
//        cal1.add(Calendar.MONTH, 1)
//        cal1.add(Calendar.DATE, -day)
//        cal1.add(Calendar.DATE, 1)




        //それをスタートに365回のループを繰り返す、この際starttimeとendtimeを一日ずらしながら進む

//        cal2.add(Calendar.MONTH, -Month)
//        cal2.add(Calendar.DAY_OF_MONTH, -day)
//        //cal1.add(Calendar.DAY_OF_MONTH, 1)
//        cal2.add(Calendar.HOUR, -hour)
//        cal2.add(Calendar.MINUTE, -min)
//        cal2.add(Calendar.SECOND, -sec)
//        //0:0:0から23:59:59を作る
//        cal2.add(Calendar.MONTH, 12)
//        cal2.add(Calendar.HOUR, 23)
//        cal2.add(Calendar.MINUTE, 59)
//        cal2.add(Calendar.SECOND, 59)


//        var endmonth = 0
//        Log.d(TAG, "Month=$Month")
//        if (Month == 3 || Month == 5 || Month == 8 || Month == 10) {
//            endmonth = 30
//        } else if (Month == 1) {
//            endmonth = 28
//        } else {
//            endmonth = 31
//        }
//        Log.d(TAG, "endmonth=$endmonth")


        //変換フォーマット作成
        val sd = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        //一年前の日付を計算
        cal1.add(Calendar.DATE, -365)
        cal2.add(Calendar.DATE, -365)
        //0:0:0に設定
        cal1.add(Calendar.HOUR, -hour)
        cal1.add(Calendar.MINUTE, -min)
        cal1.add(Calendar.SECOND, -sec)
        cal2.add(Calendar.HOUR, -hour)
        cal2.add(Calendar.MINUTE, -min)
        cal2.add(Calendar.SECOND, -sec)
        cal2.add(Calendar.DATE, 1)
        cal2.add(Calendar.SECOND, -1)


        //一年前の日をstarttime,その日の終わりをendtimeと定義



//        Log.d(TAG,"starttime=$starttime")
//        Log.d(TAG, "endtime=$endtime")

        val events: MutableList<EventDay> = ArrayList()
        //一日ごとの最初から最後を計算で求める
        for (i in 1..366) {
            var date3 = cal1.time
            val starttime = sd.format(date3)
            var date4 = cal2.time
            val endtime = sd.format(date4)
            Log.d(TAG, "startAt=$starttime")
            Log.d(TAG, "endAt=$endtime")
            val Bathlog =
                db.collection("BAthlog").orderBy("createdAt").startAt(starttime).endAt(endtime)
                    //スタートとエンドを月初めから月最後まで設定する-日付から日付を引いて1を足す(29日なら29-29+1で一日が指定できるはず)
                    //一日ごとに件数を取得、1件あればスタンプを押す
                    //0件ならおふろに入ってないので、スタンプを押す処理を飛ばす
                    .get()
                    .addOnSuccessListener { Bathlog ->
                        var bath_cnt = 0
                        for (today in Bathlog) {
                            bath_cnt += 1
                        }
//                        Log.d(TAG, "startAt2=$starttime")
//                        Log.d(TAG,"bathcnt=$bath_cnt")
                        //Log.d(TAG, "fa$Fa")
                        //cal.add(Calendar.DECEMBER, 0)
                        //Toast.makeText(this, "$cal", Toast.LENGTH_SHORT).show()
//                            val date4 = sd.format(cal3)
//                            Log.d(TAG, "cal=$date4")
                        if(bath_cnt >= 1){
                            var cal5 = Calendar.getInstance()
                            //cal5.add(Calendar.MONTH, -1)
                            cal5.add(Calendar.DAY_OF_MONTH, -365)
                            //cal1.add(Calendar.DAY_OF_MONTH, 1)
                            cal5.add(Calendar.HOUR, -hour)
                            cal5.add(Calendar.MINUTE, -min)
                            cal5.add(Calendar.SECOND, -sec)
                            cal5.add(Calendar.DATE,i-1)
                            Log.d(TAG, "i = =$i-1")
                            events.add(EventDay(cal5, R.drawable.ohurohome))
                            calendarView.setEvents(events)
                        }
                    }
            //処理が終わったら日付をインクリメントする
            cal1.add(Calendar.DAY_OF_MONTH, 1)  //日付を後ろにずらす
            cal2.add(Calendar.DAY_OF_MONTH, 1)  //日付を後ろにずらす
        }


        //Calender型からDate型へ変換
        //val fdate1 = sd.format(date1)   //書いている時刻


        Log.d(TAG, "hour=$Year")
        Log.d(TAG, "hour=$Month")
        Log.d(TAG, "hour=$day")
        Log.d(TAG, "hour=$hour")
        Log.d(TAG, "hour=$min")
        Log.d(TAG, "hour=$sec")


        //val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.month,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //spinner.adapter = adapter

        // OnItemSelectedListenerの実装
        //spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {


//                val Bathlog = db.collection("BAthlog").orderBy("createdAt").startAt(starttime).endAt(fdate1)
//                        //スタートとエンドを月初めから月最後まで設定する-日付から日付を引いて1を足す(29日なら29-29+1で一日が指定できるはず)
//                        //一日ごとに件数を取得、1件あればスタンプを押す
//                        //0件ならおふろに入ってないので、スタンプを押す処理を飛ばす
//                    .get()
//                    .addOnSuccessListener { Bathlog ->
//                        var bath_cnt = 0
//                        for(today in Bathlog){
//                            bath_cnt += 1
//                        }
//                        if (bath_cnt >= 1){
//
//                        }
//
//                        val events: MutableList<EventDay> = ArrayList()
//                        val cal = Calendar.getInstance()
//                        //Log.d(TAG, "fa$Fa")
//                        //cal.add(Calendar.DECEMBER, 0)
//                        //Toast.makeText(this, "$cal", Toast.LENGTH_SHORT).show()
//                        Log.d(TAG, "cal=$cal")
//                        events.add(EventDay(cal, R.drawable.ohurohome))
//                        calendarView.setEvents(events)
//
//
//                        //val Bathlog: MutableList<Bathlog> = ArrayList()
//
//                        //val calendar = Calendar.getInstance()
//                        //val BathlogList = ArrayList<User>()
//                        //Bathlog.forEach { BathlogList.add(it.toObject(User::class.java)) }
//                        //Bathlog.forEach{ Bathlog.add(Bathlog(calendar, R.drawable.ohurohome))}
//                        //Bathlog.forEach{ Bathlog.add(Bathlog(calendar, R.drawable.ohurohome))}
//                        //val events: BathlogList<Bathlog> = ArrayList()
//                        //val events = Bathlog()
//
//                        // events.add(Bathlog(calendar, R.drawable.ohurohome))
//                        //Bathlog.add(Bathlog(calendar, R.drawable.ohurohome))
//                        //val BathlogList = ArrayList<User>()
//                        //Bathlog.forEach { BathlogList.add(it.toObject(User::class.java)) }
//                        //taskAdapter.submitList(EventDay)
//                        //val events: MutableList<User> = ArrayList()
//                        //val events = ArrayList<User>()
//
//                        //val cal = Calendar.getInstance()
//                        //cal.add(Calendar.DAY_OF_MONTH, 7)
//
//                        //events.add(User(cal, R.drawable.ohurohome))
//                        //val events: MutableList<EventDay> = ArrayList()
//
//                        //val BathlogList = ArrayList<User>()
//                        //Bathlog.forEach { BathlogList.add(it.toObject(User::class.java)) }
//                        //taskAdapter.submitList(BathlogList)
//
//                        //val events: MutableList<EventDay> = ArrayList()
//
//
//                        //Fa.toString()
//                        //val cal = Calendar.getInstance()
//                        //Log.d(TAG, "fa$Fa")
//                        //cal.add(Calendar.Fa, 0)
//                        //events.add(EventDay(cal, R.drawable.ohurohome))
//                        //calendarView.setEvents(events)
//
//                    }
//

        // 基本的には呼ばれないが、何らかの理由で選択されることなく項目が閉じられたら呼ばれる
        //override fun onNothingSelected(parent: AdapterView<*>?) {

        //}
        //}

        val btnBack: Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()

        }
        // ③ 読込処理(CDを入れる)
        mp = MediaPlayer.create(this,R.raw.setting)
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





