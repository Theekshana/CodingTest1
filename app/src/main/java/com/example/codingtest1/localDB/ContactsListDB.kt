package com.example.codingtest1.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.codingtest1.model.ContactsData

@Database(entities = [ContactsData::class], version = 1, exportSchema = false)
abstract class ContactsListDB: RoomDatabase(){
    abstract fun contactsDao(): ContactsDao

    companion object {
        @Volatile
        private var INSTANCE: ContactsListDB? = null

        fun getDatabase(context: Context): ContactsListDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsListDB::class.java,
                    "contacts"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}