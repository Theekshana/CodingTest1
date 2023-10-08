package com.example.codingtest1.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.codingtest1.localDB.ContactsListDB
import com.example.codingtest1.model.ContactsData
import com.example.codingtest1.repository.ContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel class to manage contacts data
 */
class ContactsViewModel(app: Application) : AndroidViewModel(app) {

    private val _contacts = MutableLiveData<ArrayList<ContactsData>>()
    val contacts: LiveData<List<ContactsData>>
    private val repository: ContactsRepository

    init {
        val contactDao = ContactsListDB.getDatabase(app).contactsDao()
        repository = ContactsRepository(contactDao)
        contacts = repository.fetchAllContacts

    }

    /**
     * Add a new contact to the list
     */
    fun addContact(contact: ContactsData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertContact(contact)
            val currentList = _contacts.value ?: ArrayList()
            val updatedList = ArrayList(currentList)
            updatedList.add(contact)
            _contacts.postValue(updatedList)
        }
    }

    /**
     * Delete a list of contacts from the ViewModel
     */
    fun deleteContact(contact: ContactsData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteContact(contact)
            val currentList = _contacts.value ?: ArrayList()
            val updatedList = ArrayList(currentList)
            updatedList.remove(contact)
            _contacts.postValue(updatedList)
        }
    }

    /**
     * Edit an existing contact in the list
     */
    fun editContact(contact: ContactsData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateContact(contact)
        }
    }


}