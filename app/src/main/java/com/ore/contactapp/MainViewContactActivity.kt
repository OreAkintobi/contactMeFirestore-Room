package com.ore.contactapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.ore.loginsignupui.R
import com.ore.loginsignupui.databinding.ActivityMainViewContactBinding

//import kotlinx.android.synthetic.main.activity_main_view_contact.*

/** DISPLAYS AN INDIVIDUAL CONTACT'S INFORMATION ON A SEPARATE PAGE AFTER REDIRECTING FROM RECYCLERVIEW/CONTACT LIST **/
class MainViewContactActivity : AppCompatActivity() {
    private val REQUESTCODE = 1
    private lateinit var binding: ActivityMainViewContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main_view_contact)


        // sets up Tool Bar for Activity
        setSupportActionBar(binding.toolbar)
        // sets up Back Navigation Icon
        binding.toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        // sets up event listener for Back Navigation Icon
        binding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        val bundle = intent.extras
        val contact = bundle?.getParcelable<Contact>("CONTACT")
        binding.contact = contact

        Glide.with(this).asBitmap()
            .load("https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
            .centerCrop().fallback(
            R.drawable.ic_contact_logo_main
        ).error(R.drawable.ic_add_contact_logo).placeholder(R.drawable.ic_contact_logo_main)
            .into(binding.contactImageTwo)
    }

    fun callContact(v: View) {
        val number = binding.placeholderViewContactPhone.text.toString().trim()
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + Uri.encode(number)))
        startActivity(intent)
    }

    fun emailContact(v: View) {
        val email = binding.placeholderViewContactEmail.text.toString().trim()
        // Initialize intent
        val intent = Intent(Intent.ACTION_SEND)
        // use ACTION_SEND to launch email client, with URI setData() and setType() methods
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        // place recipient mail in intent (Send comma-seperated array if sending multiple emails)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        try {
            startActivity(Intent.createChooser(intent, "Choose Email Client"))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun changeProfilePicture(V: View) {
        val img = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            //If no permission, request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUESTCODE
            )
        } else {
            img.type = "image/*"
            img.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(img, "select a picture"), REQUESTCODE)
        }
    }
}
