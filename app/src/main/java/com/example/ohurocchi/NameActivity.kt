package com.example.ohurocchi

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_namechange)

        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("user")
        val buttonWrite = findViewById<Button>(R.id.writeButton)
        val buttonRead = findViewById<Button>(R.id.readButton)

        buttonWrite.setOnClickListener {
            ref.setValue("aaaaaaa")
        }

        buttonRead.setOnClickListener {
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.getValue(String::class.java)
                    Toast.makeText(baseContext, value,
                        Toast.LENGTH_LONG).show()
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(baseContext, "読み込み失敗",
                        Toast.LENGTH_LONG).show()
                }
            })
        }


        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {

            val btnBack: Button = findViewById(R.id.btnBack)

            btnBack.setOnClickListener {
                finish()

            }
        }
    }
}