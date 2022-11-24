package com.example.ohurocchi

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NameActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var btnUpdate: Button

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_namechange)

        etName = findViewById(R.id.stv_name)
        btnUpdate = findViewById(R.id.sbtn_Update)

        btnUpdate.setOnClickListener {
            var Fa: Int = 10
            var Fav = db.collection("NameChange")
                .document("NameChange")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.data != null) {
                            Log.d(ContentValues.TAG, "getData")
                            Log.d(ContentValues.TAG, "DocumentSnapshot data: " + (document.data?.get("Favorability")?.javaClass?.kotlin))
                            Fa = Integer.parseInt((document.data?.get("Favorability")).toString())
                            Log.d(ContentValues.TAG,"FA=$Fa")
                        } else {
                            Log.d(ContentValues.TAG, "No such document")
                        }
                    } else {
                        Log.d(ContentValues.TAG, "get failed with " + task.exception)
                    }
                }
                .addOnFailureListener { e -> Log.d(ContentValues.TAG, "Error adding document" + e)}

            val sName = etName.text.toString().trim()

            val userMap = hashMapOf(
                "name" to sName,
                "Favorability" to Fa
            )


            db.collection("NameChange").document("NameChange").set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    etName.text.clear()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }

        }
        val btnBack: Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()

        }
    }
}