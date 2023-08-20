package com.example.codingtest1

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codingtest1.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: ContactsAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var contacts: ArrayList<ContactsData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Initialize the ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]
        contacts = arrayListOf()
        // Load saved data from SharedPreferences and update ViewModel
        loadSavedDataFromSharedPreferences()

        binding.contactsRecyclerView.setHasFixedSize(true)
        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the RecyclerView Adapter with the initial contact list
        adapter = ContactsAdapter(contacts, sharedPreferences, viewModel)

        binding.contactsRecyclerView.adapter = adapter

        binding.btnAddContact.setOnClickListener {
            addContact()
        }

        // Observe the contacts LiveData for changes
        viewModel.contacts.observe(this) { contactsList ->
            if (contactsList.isEmpty()) {
                Toast.makeText(this, "Contact list is empty", Toast.LENGTH_LONG).show()
            } else {
                // Update the contact list in the adapter
                adapter.contactList = contactsList as ArrayList<ContactsData>

            }

        }

    }

    /**
     * Displays a dialog for adding a new contact with name, number, and description
     */
    private fun addContact() {
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_contact, null)
        val strName = v.findViewById<EditText>(R.id.etName)
        val strNumber = v.findViewById<EditText>(R.id.etNumber)
        val strDescription = v.findViewById<EditText>(R.id.etDescription)

        // Create an AlertDialog Builder to build the dialog
        AlertDialog.Builder(this)
            .setView(v)
            .setPositiveButton("OK") { _, _ ->
                val name = strName.text.toString()
                val number = strNumber.text.toString()
                val description = strDescription.text.toString()
                if (name.isNotEmpty() && number.isNotEmpty() && description.isNotEmpty()) {
                    val contact = ContactsData(name, number, description)
                    viewModel.addContact(contact)
                    // Save the updated list to SharedPreferences
                    saveContactsToSharedPreferences()
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Adding contact", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()

                }

            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show()
            }
            .create()
            .show()
    }

    /**
     * Save the list of contacts to SharedPreferences
     */
    private fun saveContactsToSharedPreferences() {
        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(viewModel.contacts.value)
        editor.putString("contacts", json)
        editor.apply()
    }

    /**
     * Load saved data from SharedPreferences and updates the ViewModel
     */
    private fun loadSavedDataFromSharedPreferences() {
        // Load saved data from SharedPreferences and update ViewModel
        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString("contacts", null)
        val savedContacts: ArrayList<ContactsData> =
            gson.fromJson(json, object : TypeToken<List<ContactsData>>() {}.type)
        viewModel.loadContacts(savedContacts)
    }



}