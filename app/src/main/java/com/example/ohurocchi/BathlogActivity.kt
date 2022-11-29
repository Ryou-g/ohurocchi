package com.example.ohurocchi

import TaskAdapter
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.example.ohurocchi.databinding.ActivityBathlogBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import com.applandeo.materialcalendarview.CalendarView
import java.time.Month
import java.time.Year


class BathlogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBathlogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bathlog)
        binding = ActivityBathlogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Firebase.firestore

        val taskAdapter = TaskAdapter()
        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //日付処理
        val cal1 =
            Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN)   //現在時刻を取得する

        val date1 = cal1.time

        //変換フォーマット
        val sd = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

        //Calender型からDate型へ変換
        val fdate1 = sd.format(date1)   //書いている時刻

        //0:0:0にする処理
        val Year = Calendar.getInstance().get(Calendar.YEAR)
        val Month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val min = Calendar.getInstance().get(Calendar.MINUTE)
        val sec = Calendar.getInstance().get(Calendar.SECOND)

        cal1.add(Calendar.YEAR, -Year)
        cal1.add(Calendar.YEAR, -Month)
        cal1.add(Calendar.DAY_OF_MONTH, -day)
        cal1.add(Calendar.DAY_OF_MONTH, -day)
        cal1.add(Calendar.HOUR, -hour)
        cal1.add(Calendar.MINUTE, -min)
        cal1.add(Calendar.SECOND, -sec)

        val date2 = cal1.time
        val starttime = sd.format(date2)

        Log.d(TAG, "hour=$Year")
        Log.d(TAG, "hour=$Month")
        Log.d(TAG, "hour=$day")
        Log.d(TAG, "hour=$hour")
        Log.d(TAG, "hour=$min")
        Log.d(TAG, "hour=$sec")

        Log.d(TAG, "starttime = $starttime")
        Log.d(TAG, "time=$fdate1")

        val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.month,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // OnItemSelectedListenerの実装
        //spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            // 項目が選択された時に呼ばれる
            fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val text = parent?.selectedItem as String
                Toast.makeText(this@BathlogActivity, text, Toast.LENGTH_SHORT).show()
                //日付範囲設定
                val cal1 = Calendar.getInstance()
                val cal2 = Calendar.getInstance()

                val month_str = text.dropLast(1)
                val month_int = month_str.toIntOrNull()
                Log.d(TAG, "$month_int")


                //変数で指定出来ないため全パターン記述
                when (month_int) {
                    1 -> {
                        cal1[2023, 0, 1, 0, 0] = 0
                        cal2[2023, 0, 31, 23, 59] = 59
                    }
                    2 -> {
                        cal1[2023, 1, 1, 0, 0] = 0
                        cal2[2023, 1, 28, 23, 59] = 59
                    }
                    3 -> {
                        cal1[2023, 2, 1, 0, 0] = 0
                        cal2[2023, 2, 31, 23, 59] = 59
                    }
                    4 -> {
                        cal1[2022, 3, 1, 0, 0] = 0
                        cal2[2022, 3, 30, 23, 59] = 59
                    }
                    5 -> {
                        cal1[2022, 4, 1, 0, 0] = 0
                        cal2[2022, 4, 31, 23, 59] = 59
                    }
                    6 -> {
                        cal1[2022, 5, 1, 0, 0] = 0
                        cal2[2022, 5, 30, 23, 59] = 59
                    }
                    7 -> {
                        cal1[2022, 6, 1, 0, 0] = 0
                        cal2[2022, 6, 31, 23, 59] = 59
                    }
                    8 -> {
                        cal1[2022, 7, 1, 0, 0] = 0
                        cal2[2022, 7, 31, 23, 59] = 59
                    }
                    9 -> {
                        cal1[2022, 8, 1, 0, 0] = 0
                        cal2[2022, 8, 30, 23, 59] = 59
                    }
                    10 -> {
                        cal1[2022, 9, 1, 0, 0] = 0
                        cal2[2022, 9, 31, 23, 59] = 59
                    }
                    11 -> {
                        cal1[2022, 10, 1, 0, 0] = 0
                        cal2[2022, 10, 30, 23, 59] = 59
                    }
                    12 -> {
                        cal1[2022, 11, 1, 0, 0] = 0
                        cal2[2022, 11, 31, 23, 59] = 59
                    }
                }


                Log.d(TAG, "start=$starttime")

                val calendarView = findViewById<View>(R.id.calendarView) as CalendarView

                val Bathlog = db.collection("BAthlog").orderBy("createdAt").startAt(starttime)
                    .get()
                    .addOnCompleteListener { Bathlog ->
                        val events: MutableList<EventDay> = ArrayList()
                        val cal = Calendar.getInstance()
                        //Log.d(TAG, "fa$Fa")
                        //cal.add(Calendar.Fa, 0)
                        events.add(EventDay(cal, R.drawable.ohurohome))
                        calendarView.setEvents(events)


                        //val Bathlog: MutableList<Bathlog> = ArrayList()

                        //val calendar = Calendar.getInstance()
                        //val BathlogList = ArrayList<User>()
                        //Bathlog.forEach { BathlogList.add(it.toObject(User::class.java)) }
                        //Bathlog.forEach{ Bathlog.add(Bathlog(calendar, R.drawable.ohurohome))}
                        //Bathlog.forEach{ Bathlog.add(Bathlog(calendar, R.drawable.ohurohome))}
                        //val events: BathlogList<Bathlog> = ArrayList()
                        //val events = Bathlog()

                        // events.add(Bathlog(calendar, R.drawable.ohurohome))
                        //Bathlog.add(Bathlog(calendar, R.drawable.ohurohome))
                        //val BathlogList = ArrayList<User>()
                        //Bathlog.forEach { BathlogList.add(it.toObject(User::class.java)) }
                        //taskAdapter.submitList(EventDay)
                        //val events: MutableList<User> = ArrayList()
                        //val events = ArrayList<User>()

                        //val cal = Calendar.getInstance()
                        //cal.add(Calendar.DAY_OF_MONTH, 7)

                        //events.add(User(cal, R.drawable.ohurohome))
                        //val events: MutableList<EventDay> = ArrayList()

                        //val BathlogList = ArrayList<User>()
                        //Bathlog.forEach { BathlogList.add(it.toObject(User::class.java)) }
                        //taskAdapter.submitList(BathlogList)

                        //val events: MutableList<EventDay> = ArrayList()


                        //Fa.toString()
                        //val cal = Calendar.getInstance()
                        //Log.d(TAG, "fa$Fa")
                        //cal.add(Calendar.Fa, 0)
                        events.add(EventDay(cal, R.drawable.ohurohome))
                        calendarView.setEvents(events)

                    }


                // 基本的には呼ばれないが、何らかの理由で選択されることなく項目が閉じられたら呼ばれる
                //override fun onNothingSelected(parent: AdapterView<*>?) {

                //}
                //}

                //val btnBack: Button = findViewById(R.id.btnBack)

                //btnBack.setOnClickListener
                //{
                //finish()

                //}

            }
        }
    }






