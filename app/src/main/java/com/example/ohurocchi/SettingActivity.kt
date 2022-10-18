package com.example.ohurocchi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val mypage : Button = findViewById(R.id.mypage)
        val sound : Button = findViewById(R.id.sound)
        val bathlog : Button = findViewById(R.id.bathlog)
        val dressup : Button = findViewById(R.id.dressup)
        val background : Button = findViewById(R.id.background)

        mypage.setOnClickListener {
            val intent = Intent(applicationContext,MypageActivity::class.java)
            startActivity(intent)
        }

        sound.setOnClickListener {
            val intent = Intent(applicationContext,SoundActivity::class.java)
            startActivity(intent)
        }
        bathlog.setOnClickListener {
            val intent = Intent(applicationContext,BathlogActivity::class.java)
            startActivity(intent)
        }
        dressup.setOnClickListener {
            val intent = Intent(applicationContext,DressupActivity::class.java)
            startActivity(intent)
        }
        background.setOnClickListener {
            val intent = Intent(applicationContext,BackgroundActivity::class.java)
            startActivity(intent)
        }
    }
}
