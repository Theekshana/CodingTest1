package com.example.codingtest1.localDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.codingtest1.model.ContactsData

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts ORDER BY id ASC")
    fun fetchAllContacts(): LiveData<List<ContactsData>>

    @Insert
    suspend fun insertContact(contact: ContactsData)


}