package com.example.ohurocchi

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class NameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_namechange)

        val newName:EditText =findViewById(R.id.editTextTextPersonName)



        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {

            val setName = newName.text.toString()

            changeuserNameTask(setName)
            val btnBack: Button = findViewById(R.id.btnBack)

            btnBack.setOnClickListener {
                finish()

            }
        }
    }

    private fun changeuserNameTask(mainURL:String){
        //コルーチンスコープの用意
        lifecycleScope.launch{
            //val result = changeuserNameBackgroundTask(mainURL)
            changeuserNameBackgroundTask(mainURL)
            //changeuserNameJsonTask(result)
        }
    }

    private suspend fun changeuserNameBackgroundTask(mainURL:String):String{
        val URL = "http://172.22.113.55/huro_API/DBupdate.php?name="
        val response = withContext(Dispatchers.IO){
            // 天気情報サービスから取得した結果情報（JSON文字列）を後で入れるための変数（いったん空っぽ）を用意。
            var httpResult = ""

            //  try{エラーがあるかもしれない処理を実行}catch{実際エラーがあった場合}
            try{
                //ただのURL文字列をURLオブジェクトに変換（文字列にリンクを付けるイメージ）
                val newURL = "$URL$mainURL"
                Log.d("MyApp","URL $newURL")
                val urlObj = URL(newURL)

                // アクセスしたAPIから情報を取得
                //テキストファイルを読み込むクラス(文字コードを読めるようにする準備(URLオブジェクト))
                val br = BufferedReader(InputStreamReader(urlObj.openStream()))

                //読み込んだデータを文字列に変換して代入
                //httpResult =br.toString()
                httpResult = br.readText()
            }catch (e: IOException){//IOExceptionとは例外管理するクラス
                Log.d("MyApp","例外発生")
                e.printStackTrace() //エラーが発生したよって言う
            }catch (e: JSONException){ //JSONデータ構造に問題が発生した場合の例外
                Log.d("MyApp","Json error")
                e.printStackTrace()
            }
            //HTTP接続の結果、取得したJSON文字列httpResultを戻り値とする
            return@withContext httpResult
        }
        return response
    }

    private fun changeuserNameJsonTask(result: String){
        val button1:Button = findViewById(R.id.button1)

//        val JsonObj = JSONObject(result)
//        Log.d("MyApp","aaa $JsonObj")
//        //val array = JsonObj.getJSONArray("test0")
//        //Log.d("MyApp","bbb $array")
//        val nameObj = JsonObj.getJSONObject("test0")
//        val text = nameObj.getString("user_name")
//        button1.text = text
    }
}