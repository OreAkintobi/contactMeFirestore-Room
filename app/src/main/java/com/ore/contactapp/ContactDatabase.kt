package com.ore.contactapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/** CREATES A DATABASE CLASS AND ANNOTATES IT AS A ROOM DATABASE WITH A TABLE/ENTITY **/
// Annotates class to be a Room Database with a table (entity) of the Contact class
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(
            context: Context
        ): ContactDatabase? {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact_database"
                )
//                   .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}
