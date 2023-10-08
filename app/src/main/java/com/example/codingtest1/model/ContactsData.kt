package com.example.codingtest1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents contact information for a person.
 */
@Entity(tableName = "contacts")
data class ContactsData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var phoneNumber: String,
    var description: String,
    var isExpandable: Boolean = false,
)
