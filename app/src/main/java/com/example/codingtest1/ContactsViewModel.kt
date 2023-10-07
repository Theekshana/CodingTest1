package com.example.codingtest1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codingtest1.model.ContactsData

/**
 * ViewModel class to manage contacts data
 */
class ContactsViewModel : ViewModel() {

    /**
     * MutableLiveData to hold the list of contacts
     */
    private val _contacts = MutableLiveData<ArrayList<ContactsData>>()
    val contacts: LiveData<ArrayList<ContactsData>> = _contacts

    init {
        // Initialize hardcoded contact data
        val hardcodedContacts = getHardcodedContacts()
        if (hardcodedContacts != null) {
            _contacts.value = hardcodedContacts
        }
    }

    /**
     * Add a new contact to the list
     */
    fun addContact(contactsData: ContactsData) {
        val updatedList = _contacts.value
        updatedList?.add(contactsData)
        _contacts.value = updatedList

    }

    /**
     * Load a list of contacts into the ViewModel
     */
    fun loadContacts(loadedContacts: ArrayList<ContactsData>) {
        _contacts.value = loadedContacts
    }

    /**
     * Delete a list of contacts from the ViewModel
     */
    fun deleteContacts(deletedContacts: ArrayList<ContactsData>) {
        _contacts.value = deletedContacts
    }

    /**
     * Edit an existing contact in the list
     */
    fun editContact(newContact: ContactsData) {
        val updatedContact = contacts.value.orEmpty().toMutableList()
        val index = updatedContact.indexOfFirst { it.phoneNumber == newContact.phoneNumber }
        if (index != -1) {
            updatedContact[index] = newContact
            _contacts.value = ArrayList(updatedContact)
        }


    }

    /**
     * Get a list of hardcoded contacts
     */
    private fun getHardcodedContacts(): ArrayList<ContactsData>? {
        return arrayListOf(
            ContactsData("John Doe", "123-456-7890", "Friend"),
            ContactsData("Jane Smith", "987-654-3210", "Colleague"),
            ContactsData("Alice Johnson", "555-123-4567", "Family"),
            ContactsData("John Doe", "123-456-7890", "Friend"),
            ContactsData("Jane Smith", "987-654-3210", "Colleague"),
            ContactsData("Alice Johnson", "555-123-4567", "Family"),
            ContactsData("John Doe", "123-456-7890", "Friend"),
            ContactsData("Jane Smith", "987-654-3210", "Colleague"),
            ContactsData("Alice Johnson", "555-123-4567", "Family"),
            ContactsData("John Doe", "123-456-7890", "Friend"),
            ContactsData("Jane Smith", "987-654-3210", "Colleague"),
            ContactsData("Alice Johnson", "555-123-4567", "Family"),
            ContactsData("John Doe", "123-456-7890", "Friend"),
            ContactsData("Jane Smith", "987-654-3210", "Colleague"),
            ContactsData("Alice Johnson", "555-123-4567", "Family"),
            ContactsData("John Doe", "123-456-7890", "Friend"),
            ContactsData("Jane Smith", "987-654-3210", "Colleague"),
            ContactsData("Alice Johnson", "555-123-4567", "Family")
        )

    }

}