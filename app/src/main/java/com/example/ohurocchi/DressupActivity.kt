package com.example.ohurocchi

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DressupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressup)

        val btnBack: Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()

        }

        val imageView = findViewById<ImageView>(R.id.imageView1)
        val adult = findViewById<ImageButton>(R.id.adult)
        val textview=findViewById<TextView>(R.id.textView4)

        adult.setOnClickListener {

            imageView.setImageResource(R.drawable.adult)
            textview.setText("風呂メイド1");
        }

        val sexy = findViewById<ImageButton>(R.id.sexy)

        sexy.setOnClickListener {

            imageView.setImageResource(R.drawable.sexy)
            textview.setText("風呂メイド2");
        }

        val neautral = findViewById<ImageButton>(R.id.neautral)

        neautral.setOnClickListener {

            imageView.setImageResource(R.drawable.neautral)
            textview.setText("風呂メイド3");
        }

        val sick = findViewById<ImageButton>(R.id.sick)

        sick.setOnClickListener {

            imageView.setImageResource(R.drawable.sick)
            textview.setText("風呂メイド4");
        }

    }



}