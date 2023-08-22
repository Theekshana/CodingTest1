package com.example.codingtest1

/**
 * Represents contact information for a person.
 */
data class ContactsData(
    var name: String,
    var phoneNumber: String,
    var description: String,
    var isExpandable: Boolean = false,
)
