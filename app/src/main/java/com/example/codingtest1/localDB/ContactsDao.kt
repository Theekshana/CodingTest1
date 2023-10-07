package com.example.codingtest1.localDB

import androidx.room.Insert
import com.example.codingtest1.model.ContactsData

interface ContactsDao {

    @Insert
    suspend fun insertContact(contact: ContactsData)


}