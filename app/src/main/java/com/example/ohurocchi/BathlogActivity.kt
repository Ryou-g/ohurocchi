package com.example.ohurocchi

import TaskAdapter
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ohurocchi.databinding.ActivityBathlogBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Array.get
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

        val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // OnItemSelectedListenerの実装
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            // 項目が選択された時に呼ばれる
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val text = parent?.selectedItem as String
                Toast.makeText(this@BathlogActivity, text, Toast.LENGTH_SHORT).show()
                //日付範囲設定
                val cal1 = Calendar.getInstance()
                val cal2 = Calendar.getInstance()

                val month_str = text.dropLast(1)
                val month_int = month_str.toIntOrNull()
                Log.d(TAG,"$month_int")


                //変数で指定出来ないため全パターン記述
                when(month_int){
                    1 -> { cal1[2023, 0, 1, 0, 0] = 0
                        cal2[2023, 0, 31, 23, 59] = 59
                    }
                    2 -> { cal1[2023, 1, 1, 0, 0] = 0
                        cal2[2023, 1, 28, 23, 59] = 59
                    }
                    3 -> { cal1[2023, 2, 1, 0, 0] = 0
                        cal2[2023, 2, 31, 23, 59] = 59
                    }
                    4 -> { cal1[2022, 3, 1, 0, 0] = 0
                        cal2[2022, 3, 30, 23, 59] = 59
                    }
                    5 -> { cal1[2022, 4, 1, 0, 0] = 0
                        cal2[2022, 4, 31, 23, 59] = 59
                    }
                    6 -> { cal1[2022, 5, 1, 0, 0] = 0
                        cal2[2022, 5, 30, 23, 59] = 59
                    }
                    7 -> { cal1[2022, 6, 1, 0, 0] = 0
                        cal2[2022, 6, 31, 23, 59] = 59
                    }
                    8 -> { cal1[2022, 7, 1, 0, 0] = 0
                        cal2[2022, 7, 31, 23, 59] = 59
                    }
                    9 -> { cal1[2022, 8, 1, 0, 0] = 0
                        cal2[2022, 8, 30, 23, 59] = 59
                    }
                    10 -> { cal1[2022, 9, 1, 0, 0] = 0
                        cal2[2022, 9, 31, 23, 59] = 59
                    }
                    11 -> { cal1[2022, 10, 1, 0, 0] = 0
                        cal2[2022, 10, 30, 23, 59] = 59
                    }
                    12 -> { cal1[2022, 11, 1, 0, 0] = 0
                        cal2[2022, 11, 31, 23, 59] = 59
                    }
                }

                val date1 = cal1.time
                val date2 = cal2.time

                //変換フォーマット
                val sdformat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

                //calender型からdate型へ変換
                val starttime = sdformat.format(date1)    //月初め
                val endtime = sdformat.format(date2)  //月の終わり

                Log.d(TAG,"start=$starttime end=$endtime")



                val Bathlog = db.collection("BAthlog").orderBy("createdAt").startAt(starttime).endAt(endtime)
                    .get()
                    .addOnSuccessListener { Bathlog ->
                        val BathlogList = ArrayList<User>()
                        Bathlog.forEach { BathlogList.add(it.toObject(User::class.java)) }
                        taskAdapter.submitList(BathlogList)
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
                    }
            }

            // 基本的には呼ばれないが、何らかの理由で選択されることなく項目が閉じられたら呼ばれる
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val btnBack : Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()

        }
    }
}




