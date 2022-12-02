package com.example.ohurocchi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SoundActivity : AppCompatActivity() {

    private var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)

        val imageView2 = findViewById<ImageView>(R.id.imageView6)

        db.collection("NameChange").document("NameChange").get()
            .addOnCompleteListener { background ->
                if(background.isSuccessful){
                    val background_document = background.result
                    if (background_document != null && background_document.data != null){
                        //var rrr =dress_document.data?.get("Favorability")
                        imageView2.setImageResource(getResources().getIdentifier(background_document.data?.get("nowBackground") as String?,"drawable", getPackageName()))
                    }
                }

            }

        val btnBack: ImageButton = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()

        }

    }

}