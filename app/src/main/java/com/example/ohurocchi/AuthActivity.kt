package com.example.ohurocchi

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthActivity : AppCompatActivity() {
    val status = Login_status.getInstance()
    private var mAuth: FirebaseAuth? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        mAuth = FirebaseAuth.getInstance()

        // ユーザー登録ボタン
        var btnCreate: Button = findViewById<Button>(R.id.signup) as Button
        btnCreate.setOnClickListener{
            var email = (this.findViewById<EditText>(R.id.editTextTextEmailAddress) as EditText).text.toString()
            var password = (this.findViewById<EditText>(R.id.editTextTextPassword) as EditText).text.toString()
            Log.i(ContentValues.TAG, String.format("create email=%s, password%s", email, password))
            createUserWithEmailAndPassword(email, password )
        }

        // サインインボタン
        var btnSignIn: Button = findViewById<Button>(R.id.login) as Button
        btnSignIn.setOnClickListener{
            var email = (this.findViewById<EditText>(R.id.editTextTextEmailAddress) as EditText).text.toString()
            var password = (this.findViewById<EditText>(R.id.editTextTextPassword) as EditText).text.toString()
            Log.i(ContentValues.TAG, String.format("signin email=%s, password%s", email, password))
            signInWithEmailAndPassword(email, password)
        }
    }

    override fun onStart() {
        super.onStart()

        var currentUser = mAuth?.currentUser
        updateUI(currentUser)
    }

    // 画面表示更新
    private fun updateUI(currentUser: FirebaseUser?) {
        Log.i(ContentValues.TAG, String.format("update ui user=%s", currentUser?.email.toString()))
        Log.i(ContentValues.TAG, String.format("update ui user=%s", currentUser?.uid.toString()))
        //val mes = findViewById<TextView>(R.id.message)
        //mes.text = status.now_Login
        Log.d("nowLogin",status.now_Login)
        //(findViewById<TextView>(R.id.lblUser) as TextView).text = currentUser?.email.toString()
    }

    // ユーザー登録処理
    private fun createUserWithEmailAndPassword(email: String, password: String) {
        mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener{
                task: Task<AuthResult> ->
            if (task.isSuccessful) {
                Log.d(ContentValues.TAG, "createUserWithEmail:success")
                var user = mAuth?.currentUser
                Toast.makeText(this, "登録成功", Toast.LENGTH_SHORT).show()
                updateUI(user)
            } else {
                Log.w(ContentValues.TAG, "createUserWithEmail:failure")
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    // サインイン処理
    private fun signInWithEmailAndPassword(email: String, password: String) {
        val sharedPref = getSharedPreferences("user_login_id", Context.MODE_PRIVATE)
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener{
                task: Task<AuthResult> ->
            if(task.isSuccessful) {
                Log.d(ContentValues.TAG, "signInWithEmail:success")
                var user = mAuth?.currentUser
                status.now_Login = user?.uid.toString()
                //updateUI(user)
                val intent = Intent(this,SplashActivity::class.java)
                startActivity(intent)
                sharedPref.edit().putString("user_id", user?.uid.toString()).commit()
            } else {
                Log.w(ContentValues.TAG, "signInWithEmail:failure")
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }
}