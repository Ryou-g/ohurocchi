package com.example.ohurocchi

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DressupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressup)

        val db = Firebase.firestore

        val btnBack: Button = findViewById(R.id.btnBack)
        val acceptButton: Button = findViewById(R.id.button)
        var acceptflag = ""
        var nowDress_num = 0

        btnBack.setOnClickListener {
            finish()

        }

        val imageView = findViewById<ImageView>(R.id.imageView1)
        val adult = findViewById<ImageButton>(R.id.adult)
        val textview=findViewById<TextView>(R.id.textView4)

        adult.setOnClickListener {

            imageView.setImageResource(R.drawable.coat_usually)
            textview.setText("風呂メイド1");
            acceptflag = "coat_usually"
            nowDress_num = 1
        }

        val sexy = findViewById<ImageButton>(R.id.sexy)

        sexy.setOnClickListener {

            imageView.setImageResource(R.drawable.dress_usually)
            textview.setText("風呂メイド2");
            acceptflag = "dress_usually"
            nowDress_num = 2
        }

        val neautral = findViewById<ImageButton>(R.id.neautral)

        neautral.setOnClickListener {

            imageView.setImageResource(R.drawable.maid_usually)
            textview.setText("風呂メイド3");
            acceptflag = "maid_usually"
            nowDress_num = 3
        }

        val sick = findViewById<ImageButton>(R.id.sick)

        sick.setOnClickListener {

            imageView.setImageResource(R.drawable.uniform_usually)
            textview.setText("風呂メイド4");
            acceptflag = "uniform_usually"
            nowDress_num = 4
        }

        //適用ボタン押下時処理
        acceptButton.setOnClickListener {
            db.collection("NameChange").document("NameChange").update("nowDress",acceptflag,"nowDress_num",nowDress_num)
            Toast.makeText(this, "着せ替え成功！", Toast.LENGTH_SHORT).show()

        }


    }



}