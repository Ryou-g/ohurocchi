package com.example.ohurocchi

import android.os.Bundle
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

            val sName = etName.text.toString().trim()

            val userMap = hashMapOf(
                "name" to sName
            )


            db.collection("NameChange").document().set(userMap)
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