package com.example.codingtest1

/**
 * Represents contact information for a person.
 */
data class ContactsData(
    val name: String,
    val phoneNumber: String,
    val description: String,
    var isExpandable: Boolean = false
)
