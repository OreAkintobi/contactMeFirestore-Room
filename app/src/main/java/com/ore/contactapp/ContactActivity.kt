package com.ore.contactapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.ore.loginsignupui.R
import kotlinx.android.synthetic.main.contact_scrolling.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/** SHOWS RECYCLERVIEW OF ALL CONTACTS STORED **/

const val TAG = "FirestoreFirebaseDB"

class ContactActivity : AppCompatActivity() {

    private lateinit var firestoreDB: FirebaseFirestore
    private lateinit var database: ContactDatabase
    private lateinit var contactViewModel: ContactViewModel
    lateinit var adapter: ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        val contacts = ArrayList<Contact>()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_scrolling)
        setSupportActionBar(toolbarAll)
        toolbarAll.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        toolbarAll.setNavigationOnClickListener {
            super.onBackPressed()
        }

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.allContacts.observe(this, Observer {
            // Update the cached copy of the words in the adapter.
            adapter = ContactsAdapter(it, this)
            recyclerView.adapter = adapter
        })

        firestoreDB = FirebaseFirestore.getInstance()
        firestoreDB.collection("contacts")
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {
                        for (contact in document) {
                            val item = contact.data
                            contacts.add(
                                Contact(
                                    item["name"] as String?,
                                    item["email"] as String?,
                                    item["phone"] as String?
                                )
                            )
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.exception)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu.findItem(R.id.menu1)
        item.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.preferences) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            database = ContactDatabase.getDatabase(applicationContext)!!
            val bundle = data?.extras
            val result = bundle?.getParcelable<Contact>("CONTACT")
            if (result != null) {
                firestoreDB.collection("contacts").add(result).addOnCompleteListener {
                    GlobalScope.async(Dispatchers.IO) {
                        contactViewModel.insert(result)
                        adapter.notifyDataSetChanged()
                        result
                    }
                }.addOnFailureListener {
                    it.stackTrace
                }
            }
        }
    }

    fun addNewContactClick(v: View) {
        val intent = Intent(this, MainBabyBlissLoginUIActivity::class.java)
        startActivityForResult(intent, 1)
    }
}

