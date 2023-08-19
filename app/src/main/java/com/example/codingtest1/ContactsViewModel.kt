package com.example.codingtest1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactsViewModel : ViewModel() {

    /**
     * MutableLiveData to hold the list of contacts
     */
    private val _contacts = MutableLiveData<List<ContactsData>>()
    val contacts: LiveData<List<ContactsData>> = _contacts

    init {
        // Initialize hardcoded contact data
        _contacts.value = getHardcodedContacts()
    }

    fun addContact(contactsData: ContactsData){
        val updatedList = _contacts.value?.toMutableList() ?: mutableListOf()
        updatedList.add(contactsData)
        _contacts.value = updatedList

    }

    fun loadContacts(loadedContacts: List<ContactsData>) {
        _contacts.value = loadedContacts
    }

    private fun getHardcodedContacts(): List<ContactsData> {
        return listOf(
            ContactsData("John Doe", "123-456-7890", "Friend"),
            ContactsData( "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData( "Alice Johnson", "555-123-4567", "Family"),
            ContactsData( "John Doe", "123-456-7890", "Friend"),
            ContactsData( "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData( "Alice Johnson", "555-123-4567", "Family"),
            ContactsData( "John Doe", "123-456-7890", "Friend"),
            ContactsData( "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData( "Alice Johnson", "555-123-4567", "Family"),
            ContactsData( "John Doe", "123-456-7890", "Friend"),
            ContactsData( "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData( "Alice Johnson", "555-123-4567", "Family"),
            ContactsData( "John Doe", "123-456-7890", "Friend"),
            ContactsData( "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData( "Alice Johnson", "555-123-4567", "Family"),
            ContactsData( "John Doe", "123-456-7890", "Friend"),
            ContactsData( "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData( "Alice Johnson", "555-123-4567", "Family")
        )

    }

}