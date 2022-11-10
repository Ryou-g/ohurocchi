package com.example.ohurocchi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.ohurocchi.databinding.ActivityBackgroundBinding

class BackgroundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)

        val btnBack :Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()

        }

        val imageView = findViewById<ImageView>(R.id.imageView4)
        val imageButton14 = findViewById<ImageButton>(R.id.imageButton14)
        val textView = findViewById<TextView>(R.id.textView4)

        imageButton14.setOnClickListener {

            imageView.setImageResource(R.drawable.japanese_1)
            textView.setText("露天風呂");

        }

        val imageButton15 = findViewById<ImageButton>(R.id.imageButton15)

        imageButton15.setOnClickListener {

            imageView.setImageResource(R.drawable.background_1)
            textView.setText("バスルーム");
        }

        val imageButton16 = findViewById<ImageButton>(R.id.imageButton16)

        imageButton16.setOnClickListener {

            imageView.setImageResource(R.drawable.apart_1)
            textView.setText("浴室");

        }

        val imageButton17 = findViewById<ImageButton>(R.id.imageButton17)

        imageButton17.setOnClickListener {

            imageView.setImageResource(R.drawable.telmare_1)
            textView.setText("洋式風呂");
        }

    }
}