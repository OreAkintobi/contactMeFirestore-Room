package com.ore.contactapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ore.loginsignupui.R
import kotlinx.android.synthetic.main.activity_main_open_app.*
import kotlin.system.exitProcess

/** LAUNCHES THE APP AND PROVIDES THE USER WITH INTENT CHOICES **/
class MainOpenAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_open_app)

        Toast.makeText(
            this,
            R.string.toast_open_app,
            Toast.LENGTH_SHORT
        ).show()

        openAppButton.setOnClickListener {
            val intent = Intent(this, MainBabyBlissLoginUIActivity::class.java)
            startActivity(intent)
        }

        killAppButton.setOnClickListener {
            exitApp()
        }

        logoutButton.setOnClickListener {
            val prefs: SharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
            prefs.edit().clear().apply()
            finish()
        }

        viewAllContactsButton.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        exitApp()
    }

    fun exitApp() {
        //Display Message to User with Alert Dialog
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(R.string.sure)
            // if the dialog is cancelable
            .setCancelable(true)
            // positive button text and action
            .setPositiveButton(R.string.yes) { _, _ ->
                Toast.makeText(
                    this,
                    R.string.toast_exiting_app,
                    Toast.LENGTH_SHORT
                ).show()
                finishAffinity()
                exitProcess(0)
            }
            // negative button text and action
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(R.string.dialog_box_quit)
        // show alert dialog
        alert.show()
    }
}


