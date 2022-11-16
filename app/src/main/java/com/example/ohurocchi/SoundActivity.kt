package com.example.ohurocchi

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.ohurocchi.databinding.ActivityMainBinding
import com.example.ohurocchi.databinding.ActivitySoundBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SoundActivity : AppCompatActivity() {
        // バインディングクラスの変数
        private lateinit var binding: ActivitySoundBinding

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sound)
            binding = ActivitySoundBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Firestoreをインスタンス化
            val db = Firebase.firestore

            // ボタンを押したときの処理
            binding.addButton.setOnClickListener {

                // Taskをインスタンス化
                val user = User(
                    title = binding.titleEditText.text.toString(),
                )

                db.collection("tasks")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        }


    }





