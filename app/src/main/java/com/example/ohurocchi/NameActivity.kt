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
            changeuserNameBackgroundTask(mainURL)
        }
    }

    private suspend fun changeuserNameBackgroundTask(mainURL:String):String{
        val URL = "http://172.22.113.55/huro_API/DBupdate.php?name="
        val response = withContext(Dispatchers.IO){
            var httpResult = ""
            try{
                val newURL = "$URL$mainURL"
                Log.d("MyApp","URL $newURL")
                val urlObj = URL(newURL)

                val br = BufferedReader(InputStreamReader(urlObj.openStream()))

                httpResult = br.readText()
            }catch (e: IOException){//IOExceptionとは例外管理するクラス
                Log.d("MyApp","例外発生")
                e.printStackTrace() //エラーが発生したよって言う
            }catch (e: JSONException){ //JSONデータ構造に問題が発生した場合の例外
                Log.d("MyApp","Json error")
                e.printStackTrace()
            }
            return@withContext httpResult
        }
        return response
    }
}