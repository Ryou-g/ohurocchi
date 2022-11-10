package com.example.ohurocchi

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class NameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_namechange)


        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
        }
            val btnBack: Button = findViewById(R.id.btnBack)

            btnBack.setOnClickListener {
                finish()

            }
        }
    }