package com.example.ohurocchi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
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

        //ここからホーム画面遷移のコード
        val imageButton3: ImageButton = findViewById(R.id.imageButton3)
        imageButton3.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, HomeActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
        }
        //ここから衣装配布画面遷移のコード
        val imageButton2: ImageButton = findViewById(R.id.imageButton2)
        imageButton2.setOnClickListener {
            //ここから遷移用のコード
            val intent = Intent(
                this,
                DressbuyActivity::class.java
            )    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
        }
        //ここからキャラ画面遷移のコード
        val imageButton: ImageButton = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            //ここから遷移用のコード
            val intent = Intent(
                this,
                CharaActivity::class.java
            )    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
        }
        //ここから設定画面遷移のコード
        val imageButton4: ImageButton = findViewById(R.id.imageButton4)
        imageButton4.setOnClickListener{
            //ここから遷移用のコード
            val intent = Intent(this,SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
        }
    }
}
