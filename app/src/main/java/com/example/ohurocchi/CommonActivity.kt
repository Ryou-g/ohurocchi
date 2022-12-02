package com.example.ohurocchi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.ohurocchi.databinding.ActivityBathlogBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommonActivity  : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        val db = Firebase.firestore

        val progressBar: ProgressBar = findViewById(R.id.progressber1)

        // 水平プログレスバーの最大値を設定します
        progressBar.setMax(200);

        //好感度を取得
        var Faboravirity = 0
        db.collection("NameChange").document("NameChange").get().addOnCompleteListener { Fav ->
                if (Fav.isSuccessful) {
                    val Fav_document = Fav.result
                    if (Fav_document != null && Fav_document.data != null) {
                        Faboravirity =
                            Integer.parseInt((Fav_document.data?.get("Favorability")).toString()) // progress
                        progressBar.setProgress(Faboravirity);
                    }
                }
            }
    }}