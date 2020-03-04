package com.ore.contactapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ore.loginsignupui.R
import kotlinx.android.synthetic.main.activity_login.*

/** MANAGES LOGIN AND BARE-BONES AUTHENTICATION FOR A USER TO ENTER THE APP AND START USING THE APP **/
const val EMAIL = "email"
const val PASSWORD = "password"

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val prefs: SharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        if (prefs.contains(EMAIL)) {
            login()
        }

        loginButton.setOnClickListener {
            editor.putString(EMAIL, email.text.toString())
            editor.putString(PASSWORD, password.text.toString())
            editor.apply()
            login()
        }
    }

    fun login() {
        val intent = Intent(this, MainOpenAppActivity::class.java)
        startActivity(intent)
    }
}
