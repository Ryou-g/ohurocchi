package com.example.ohurocchi

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity() {
    val status = Login_status.getInstance()
    private var mAuth: FirebaseAuth? = null
    @SuppressLint("MissingInflatedId")

    // BGM
    private lateinit var mp: MediaPlayer

    // SE
    private lateinit var soundPool: SoundPool
    private var decision = 0
    private var cancel = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val audioAttributes = AudioAttributes.Builder()
            // USAGE_MEDIA
            // USAGE_GAME
            .setUsage(AudioAttributes.USAGE_GAME)
            // CONTENT_TYPE_MUSIC
            // CONTENT_TYPE_SPEECH, etc.
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            // ストリーム数に応じて
            .setMaxStreams(2)
            .build()

        // 効果音をロードしておく
        decision = soundPool.load(this, R.raw.decision, 1)
        cancel = soundPool.load(this, R.raw.cancel, 1)

        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener{ soundPool, sampleId, status ->
            Log.d("debug", "sampleId=$sampleId")
            Log.d("debug", "status=$status")
        }

        mAuth = FirebaseAuth.getInstance()

        // ユーザー登録ボタン
        var btnCreate: Button = findViewById<Button>(R.id.signup) as Button
        btnCreate.setOnClickListener{

            var email = (this.findViewById<EditText>(R.id.editTextTextEmailAddress) as EditText).text.toString()
            var password = (this.findViewById<EditText>(R.id.editTextTextPassword) as EditText).text.toString()
            Log.i(ContentValues.TAG, String.format("create email=%s, password%s", email, password))
            if((email != "") && (password != "")){
                createUserWithEmailAndPassword(email, password )

            }else{
                Toast.makeText(this, "未入力の項目があります", Toast.LENGTH_SHORT).show()
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(cancel, 0.3f, 0.3f, 0, 0, 1.0f)
            }

        }

        // サインインボタン
        var btnSignIn: Button = findViewById<Button>(R.id.login) as Button
        btnSignIn.setOnClickListener{

            var email = (this.findViewById<EditText>(R.id.editTextTextEmailAddress) as EditText).text.toString()
            var password = (this.findViewById<EditText>(R.id.editTextTextPassword) as EditText).text.toString()
            Log.i(ContentValues.TAG, String.format("signin email=%s, password%s", email, password))
            if((email != "") && (password != "")){
                signInWithEmailAndPassword(email, password)

            }else{
                Toast.makeText(this, "未入力の項目があります", Toast.LENGTH_SHORT).show()
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(cancel, 0.3f, 0.3f, 0, 0, 1.0f)
            }

        }
        mp = MediaPlayer.create(this,R.raw.bath)
        mp.setVolume(0.4F,0.4F)
        mp.isLooping = true
        mp.start()

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
        val db = Firebase.firestore
        try {
            mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        var user = mAuth?.currentUser
                        Toast.makeText(this, "登録成功", Toast.LENGTH_SHORT).show()
                        // 効果音 の再生
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                        soundPool.play(decision, 0.3f, 0.3f, 0, 0, 1.0f)
                        updateUI(user)
                        db.collection("NameChange").document(user?.uid.toString()).get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val document = task.result
                                    if (document != null && document.data != null) {
                                        var Fa = -1
                                        Fa =
                                            Integer.parseInt((document.data?.get("Favorability")).toString())
                                        Log.d("Faイコール", "Fa取得成功=$Fa")

                                    } else {
                                        //存在しないユーザーの新規登録処理を行う
                                        val field_data = hashMapOf(
                                            "Favorability" to "50",
                                            "coat_unlock" to "True",
                                            "dress_unlock" to "False",
                                            "maid_unlock" to "False",
                                            "name" to "セバスちゃん",
                                            "nowBackground" to "huro",
                                            "nowDress" to "uniform_usually",
                                            "nowDress_num" to "1",
                                            "uniform_unlock" to "False"
                                        )

                                        db.collection("NameChange").document(user?.uid.toString())
                                            .set(field_data)
                                            .addOnSuccessListener { documentReference ->
                                                Log.d("succ", "成功")
                                            }
                                        Log.d("mamamama", "新規失敗")
                                    }

                                    Log.d("addOnCompleteListener", "Succsess")
                                } else {
                                    Log.d("addOnCompleteListener", "error")
                                }
                            }
                    } else {
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure")
                        Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                        // 効果音 の再生
                        // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                        soundPool.play(cancel, 0.3f, 0.3f, 0, 0, 1.0f)
                        updateUI(null)
                    }
                }
        }catch(e: Exception) {
            Toast.makeText(this, "このユーザーは既に登録されています", Toast.LENGTH_SHORT).show()
            // 効果音 の再生
            // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
            soundPool.play(cancel, 0.3f, 0.3f, 0, 0, 1.0f)
        }
    }

    // サインイン処理
    private fun signInWithEmailAndPassword(email: String, password: String) {
        val db = Firebase.firestore
        val sharedPref = getSharedPreferences("user_login_id", Context.MODE_PRIVATE)
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener{
                task: Task<AuthResult> ->
            if(task.isSuccessful) {
                Log.d(ContentValues.TAG, "signInWithEmail:success")
                var user = mAuth?.currentUser
                status.now_Login = user?.uid.toString()
                //updateUI(user)
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
                sharedPref.edit().putString("user_id", user?.uid.toString()).commit()
                //過去のログイン状態確認
                Log.d("userID",user?.uid.toString())
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(decision, 0.3f, 0.3f, 0, 0, 1.0f)
            } else {
                Log.w(ContentValues.TAG, "signInWithEmail:failure")
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                // 効果音 の再生
                // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
                soundPool.play(cancel, 0.3f, 0.3f, 0, 0, 1.0f)
                updateUI(null)
            }
        }
    }

    //６）再開
    override fun onResume() {
        super.onResume()
        mp.start()
    }
    //５）一時停止
    override fun onPause() {
        super.onPause()
        mp.pause()
    }

    //７）終了・メモリの解放
    override fun onDestroy() {
        super.onDestroy()
        mp.stop() //終了・停止
        mp.release() //解放
    }
}