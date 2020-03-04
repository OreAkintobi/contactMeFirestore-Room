package com.ore.contactapp

import androidx.lifecycle.LiveData

/** CREATES ACCESS POINT TO THE DAO, THEREBY ALLOWING US ACCESS TO THE ROOM DATABASE **/
// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class ContactRepository(private val contactDao: ContactDAO) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allContacts: LiveData<List<Contact>> = contactDao.getAlphabetizedContacts()

    fun insert(contact: Contact) {

        contactDao.insert(contact)
    }
}