package com.example.ohurocchi

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast

class BathlogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bathlog)


        val btnBack : Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()

        }
    }
}