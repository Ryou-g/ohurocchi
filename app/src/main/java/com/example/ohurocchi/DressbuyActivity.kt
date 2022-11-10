package com.example.ohurocchi

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class DressbuyActivity : AppCompatActivity() {

    // ① 準備（コンポを部屋に置く・コピペOK）

    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dressexchange)

        //ここからホーム画面遷移のコード
        val imageButton3: ImageButton = findViewById(R.id.imageButton3)
        imageButton3.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, HomeActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
        }
        //ここから衣装配布画面遷移のコード
        val imageButton2: ImageButton = findViewById(R.id.imageButton2)
        imageButton2.setOnClickListener {
            //ここから遷移用のコード
            val intent = Intent(
                this,
                DressbuyActivity::class.java
            )    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
        }
        //ここからキャラ画面遷移のコード
        val imageButton: ImageButton = findViewById(R.id.imageButton)
        imageButton.setOnClickListener {
            //ここから遷移用のコード
            val intent = Intent(
                this,
                CharaActivity::class.java
            )    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
        }
        //ここから設定画面遷移のコード
        val imageButton4: ImageButton = findViewById(R.id.imageButton4)
        imageButton4.setOnClickListener {
            //ここから遷移用のコード
            val intent =
                Intent(this, SettingActivity::class.java)    //intentインスタンスの生成(第二引数は遷移先のktファイル名)
            startActivity(intent)
            //ここまで
        }

        mp = MediaPlayer.create(this,R.raw.dress_buy)
        mp.isLooping = true
        mp.start()


        val imageView = findViewById<ImageView>(R.id.imageView18)
        val textView = findViewById<TextView>(R.id.textView4)
        val textView1 = findViewById<TextView>(R.id.textView5)


        val adult = findViewById<ImageButton>(R.id.adult)

        adult.setOnClickListener {

            imageView.setImageResource(R.drawable.adult)
            textView.setText("風呂メイド1");
            textView1.setText("25pt");
        }

        val sexy = findViewById<ImageButton>(R.id.sexy)

        sexy.setOnClickListener {

            imageView.setImageResource(R.drawable.sexy)
            textView.setText("風呂メイド2");
            textView1.setText("30pt");
        }

        val neautral = findViewById<ImageButton>(R.id.neautral)

        neautral.setOnClickListener {

            imageView.setImageResource(R.drawable.neautral)
            textView.setText("風呂メイド3");
            textView1.setText("45pt");
        }

        val sick = findViewById<ImageButton>(R.id.sick)

        sick.setOnClickListener {

            imageView.setImageResource(R.drawable.sick)
            textView.setText("風呂メイド4");
            textView1.setText("300pt");
        }

        //APIのURL
        val mainURL = "http://10.0.2.2/huro_API/DB.php" //IPアドレスには現在の自分に割り振られているアドレスを指定する
        //ボタンの取得
        //val button1: Button = findViewById(R.id.button1)
        val tvUserName:TextView = findViewById(R.id.tvUserName)
        //ぼたんが押されたら
//        button1.setOnClickListener{
//            userNameTask(mainURL)
//        }
        userNameTask(mainURL)
    }

    private fun userNameTask(mainURL:String){
        //コルーチンスコープの用意
        lifecycleScope.launch{
            val result = userNameBackgroundTask(mainURL)

            userNameJsonTask(result)
        }
    }

    private suspend fun userNameBackgroundTask(mainURL:String):String{
        val response = withContext(Dispatchers.IO){
            // 天気情報サービスから取得した結果情報（JSON文字列）を後で入れるための変数（いったん空っぽ）を用意。
            var httpResult = ""

            //  try{エラーがあるかもしれない処理を実行}catch{実際エラーがあった場合}
            try{
                //ただのURL文字列をURLオブジェクトに変換（文字列にリンクを付けるイメージ）
                val urlObj = URL(mainURL)

                // アクセスしたAPIから情報を取得
                //テキストファイルを読み込むクラス(文字コードを読めるようにする準備(URLオブジェクト))
                val br = BufferedReader(InputStreamReader(urlObj.openStream()))

                //読み込んだデータを文字列に変換して代入
                //httpResult =br.toString()
                httpResult = br.readText()
            }catch (e: IOException){//IOExceptionとは例外管理するクラス
                e.printStackTrace() //エラーが発生したよって言う
            }catch (e: JSONException){ //JSONデータ構造に問題が発生した場合の例外
                e.printStackTrace()
            }
            //HTTP接続の結果、取得したJSON文字列httpResultを戻り値とする
            return@withContext httpResult
        }
        return response
    }

    private fun userNameJsonTask(result: String){
        //val button1:Button = findViewById(R.id.button1)
        val tvUserName:TextView = findViewById(R.id.tvUserName)
        val JsonObj = JSONObject(result)
        Log.d("MyApp","aaa $JsonObj")
        //val array = JsonObj.getJSONArray("test0")
        //Log.d("MyApp","bbb $array")
        val nameObj = JsonObj.getJSONObject("test0")
        val text = nameObj.getString("user_name")
        tvUserName.text = text
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