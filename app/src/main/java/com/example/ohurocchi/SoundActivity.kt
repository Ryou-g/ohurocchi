package com.example.ohurocchi

import TaskAdapter
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ohurocchi.databinding.ActivitySoundBinding
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

            val taskAdapter = TaskAdapter()
            binding.recyclerView.adapter = taskAdapter
            binding.recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


            // ボタンを押したときの処理
            binding.addButton.setOnClickListener {

                // Taskをインスタンス化
                val user = User(
                    title = binding.titleEditText.text.toString(),
                )

                db.collection("user")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }


            }

            db.collection("user")
                .get()
                .addOnSuccessListener { user ->
                    val userList = ArrayList<User>()
                    user.forEach { userList.add(it.toObject(User::class.java)) }
                    taskAdapter.submitList(userList)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }
        }
}








