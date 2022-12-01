package com.example.ohurocchi

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
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


        val imageView2 = findViewById<ImageView>(R.id.imageView7)

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

        etName = findViewById(R.id.stv_name)
        btnUpdate = findViewById(R.id.sbtn_Update)

        btnUpdate.setOnClickListener {
            //ボタンを押されたらまず現在の好感度情報を取得
            var Fa: Int = 10
            var nowDress = 0
            var nowBack = ""
            var Fav = db.collection("NameChange")
                .document("NameChange")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.data != null) {
                            Log.d(ContentValues.TAG, "getData")
                            //Log.d(ContentValues.TAG, "DocumentSnapshot data: " + (document.data?.get("Favorability")?.javaClass?.kotlin))
                            Log.d(ContentValues.TAG, "name=${document.data?.get("Favorability")}")
                            Fa = Integer.parseInt((document.data?.get("Favorability")).toString())
                            nowDress = Integer.parseInt((document.data?.get("nowDress_num")).toString())
                            nowBack = (document.data?.get("nowBackground")).toString()
                            Log.d(ContentValues.TAG,"FA=$Fa")
                            val sName = etName.text.toString().trim()

                            //新しいユーザー名と取得した好感度情報をセット
                            val userMap = hashMapOf(
                                "name" to sName,
                                "Favorability" to Fa,
                                "nowDress_num" to nowDress,
                                "nowBackground" to nowBack
                            )

                            //セットした情報を基にデータを更新する
                            db.collection("NameChange").document("NameChange").set(userMap)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                                    etName.text.clear()
                                }
                                .addOnFailureListener{
                                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Log.d(ContentValues.TAG, "No such document")
                        }
                    } else {
                        Log.d(ContentValues.TAG, "get failed with " + task.exception)
                    }
                }
                .addOnFailureListener { e -> Log.d(ContentValues.TAG, "Error adding document" + e)}

        }
        val btnBack: Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()

        }
    }
}