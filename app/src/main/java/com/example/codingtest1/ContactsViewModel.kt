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

    private fun getHardcodedContacts(): List<ContactsData> {
        return listOf(
            ContactsData(1, "John Doe", "123-456-7890", "Friend"),
            ContactsData(2, "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData(3, "Alice Johnson", "555-123-4567", "Family"),
            ContactsData(1, "John Doe", "123-456-7890", "Friend"),
            ContactsData(2, "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData(3, "Alice Johnson", "555-123-4567", "Family"),
            ContactsData(1, "John Doe", "123-456-7890", "Friend"),
            ContactsData(2, "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData(3, "Alice Johnson", "555-123-4567", "Family"),
            ContactsData(1, "John Doe", "123-456-7890", "Friend"),
            ContactsData(2, "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData(3, "Alice Johnson", "555-123-4567", "Family"),
            ContactsData(1, "John Doe", "123-456-7890", "Friend"),
            ContactsData(2, "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData(3, "Alice Johnson", "555-123-4567", "Family"),
            ContactsData(1, "John Doe", "123-456-7890", "Friend"),
            ContactsData(2, "Jane Smith", "987-654-3210", "Colleague"),
            ContactsData(3, "Alice Johnson", "555-123-4567", "Family")
        )

    }

}