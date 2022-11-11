package com.example.ohurocchi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.ohurocchi.AppDatabase.Companion.getDatabase
import com.example.ohurocchi.SoundActivity.Companion.buttonUpdate
import com.example.ohurocchi.SoundActivity.Companion.editTextName
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SoundActivity : AppCompatActivity() {
    companion object {
        lateinit var editTextName: EditText
        lateinit var buttonUpdate: Button
        lateinit var listViewNames: ListView
        lateinit var db : AppDatabase
        lateinit var dao : UserDao
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound)
        //コンポーネント取得
        editTextName = findViewById(R.id.editTextName)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        //listViewNames = findViewById(R.id.ListViewNames)
        //DB関連のインスタンス取得
        db = getDatabase(this)
        dao = db.userDao()

        //リストの初期表示
        getAllUser(this)

        //ボタンのリスナーを設定
        buttonUpdate.setOnClickListener {
            //入力内容の登録
            insUser(editTextName.text.toString())
            editTextName.text.clear()
            //リストの再取得
            getAllUser(this)
        }
    }

    //ListViewにデータを表示する
    private fun getAllUser(context : Context) {
        //ioスレッド：DBからデータ取得
        //mainスレッド：取得結果をUIに表示
        dao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //データ取得完了時の処理
                    val data = ArrayList<User>()
                    it.forEach { user -> data.add(user) }
                    //リスト項目とListViewを対応付けるArrayAdapterを用意する
                    //リストで使用するlayout（simple_list_item_1）を指定する
                    val adapter = ArrayAdapter(
                        context, android.R.layout.simple_list_item_1, data)
                    listViewNames.adapter = adapter
                }
                , {
                    //エラー処理
                }
            )
    }

    //入力内容をDBに登録する
    private fun insUser(name : String) {
        Completable.fromAction { dao.insert(User(0, name, money =0, apply_Face =1, apply_Background =1,
            apply_Costume = 1, clean = 1, favorability = 100)) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}






