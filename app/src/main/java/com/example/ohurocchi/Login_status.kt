package com.example.ohurocchi

import androidx.appcompat.app.AppCompatActivity

class Login_status: AppCompatActivity() {
    var now_Login = ""

    companion object {
        private var instance: Login_status? = null

        fun getInstance(): Login_status {
            if (instance == null)
                instance = Login_status()

            return instance!!
        }
    }

}