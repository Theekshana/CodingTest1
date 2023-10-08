package com.example.codingtest1.repository

import androidx.lifecycle.LiveData
import com.example.codingtest1.localDB.ContactsDao
import com.example.codingtest1.model.ContactsData

class ContactsRepository(private val contactsDao: ContactsDao) {

    /**
     * Retrieves a LiveData list of all contacts from the database.
     *
     * @return A LiveData object containing a list of contacts.
     */
    val fetchAllContacts: LiveData<List<ContactsData>> = contactsDao.fetchAllContacts()

    /**
     * Inserts a new contact into the database.
     *
     * @param contact The contact to be inserted.
     */
    suspend fun insertContact(contact: ContactsData) {
        contactsDao.insertContact(contact)
    }

    /**
     * Deletes a contact from the database.
     *
     * @param contact The contact to be deleted.
     */
    suspend fun deleteContact(contact: ContactsData) {
        contactsDao.deleteContact(contact)
    }

    /**
     * Updates an existing contact in the database.
     *
     * @param contact The contact to be updated.
     */
    suspend fun updateContact(contact: ContactsData) {
        contactsDao.updateContact(contact)
    }


}