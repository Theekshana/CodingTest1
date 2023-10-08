package com.example.codingtest1.repository

import androidx.lifecycle.LiveData
import com.example.codingtest1.localDB.ContactsDao
import com.example.codingtest1.model.ContactsData

class ContactsRepository(private val contactsDao: ContactsDao) {

    val fetchAllContacts: LiveData<List<ContactsData>> = contactsDao.fetchAllContacts()

    suspend fun insertContact(contact: ContactsData) {
        contactsDao.insertContact(contact)
    }
}