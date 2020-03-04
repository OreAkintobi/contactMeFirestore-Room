package com.ore.contactapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

/** CREATES A VIEWMODEL CLASS WHICH HOLDS AND MANAGES UI-RELATED DATA TO MAKE IT WITHSTAND APP LIFE CYCLE CHANGES **/
// Class extends AndroidViewModel and requires application as a parameter.
class ContactViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: ContactRepository
    // LiveData gives us updated contacts when they change.
    val allContacts: LiveData<List<Contact>>

    init {
        // Gets reference to ContactDao from ContactDatabase to construct
        // the correct ContactRepository.
        val contactsDao = ContactDatabase.getDatabase(application)?.contactDao()
        repository = ContactRepository(contactsDao!!)
        allContacts = repository.allContacts
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */

    fun insert(contact: Contact) {
        repository.insert(contact)

    }
}