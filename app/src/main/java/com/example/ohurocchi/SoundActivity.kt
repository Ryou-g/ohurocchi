package com.example.ohurocchi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

    val btnBack :Button = findViewById(R.id.btnBack)

    btnBack.setOnClickListener {
        finish()

    }
    }

}