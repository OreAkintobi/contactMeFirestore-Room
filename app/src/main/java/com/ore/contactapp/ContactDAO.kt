package com.ore.contactapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/** SHOWS FUNCTIONS/METHODS THAT ARE IMPLEMENTABLE ON ROOM DATABASE OF CONTACT APP **/
@Dao
interface ContactDAO {
    @Query("SELECT * FROM contacts_table")
    fun getAllContacts(): List<Contact>

    @Query("SELECT * FROM contacts_table WHERE id IN (:id)")
    fun loadAllByIds(id: IntArray): List<Contact>

    @Query("SELECT * FROM contacts_table WHERE name LIKE :name")
    fun findByName(name: String): Contact

    @Insert
    fun insert(vararg contacts: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("SELECT * from contacts_table ORDER BY name ASC")
    fun getAlphabetizedContacts(): LiveData<List<Contact>>

    @Query("DELETE FROM contacts_table")
    suspend fun deleteAll()
}