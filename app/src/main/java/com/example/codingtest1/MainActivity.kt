package com.example.codingtest1

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codingtest1.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: ContactsAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var contacts: ArrayList<ContactsData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupViewModel()
        setupUI()
        setupListeners()
        observeContacts()

    }

    private fun observeContacts() {
        // Observe the contacts LiveData for changes
        viewModel.contacts.observe(this) { contactsList ->
            if (contactsList.isEmpty()) {
                Toast.makeText(this, "Contact list is empty", Toast.LENGTH_LONG).show()
            } else {
                // Update the contact list in the adapter
                adapter.contactList = contactsList

            }
        }
    }

    private fun setupListeners() {
        // Handle adding a new contact
        binding.btnAddContact.setOnClickListener {
            addContact()
        }
    }

    private fun setupUI() {
        // Configure the RecyclerView
        binding.contactsRecyclerView.setHasFixedSize(true)
        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the RecyclerView Adapter with the initial contact list
        adapter = ContactsAdapter(contacts, sharedPreferences, viewModel)

        binding.contactsRecyclerView.adapter = adapter

        // Set up the search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun setupViewModel() {
        // Initialize the ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]

        // Initialize the list of contacts
        contacts = arrayListOf()


        // Load saved data from SharedPreferences and update ViewModel
        loadSavedDataFromSharedPreferences()
    }

    /**
     * Filters the contact list based on the given query
     * @param query The query to filter contacts
     */
    private fun filterList(query: String?) {
        if (query == null) {
            adapter.setFilteredContacts(viewModel.contacts.value.orEmpty() as ArrayList<ContactsData>)
            return
        }

        val filteredList = ArrayList<ContactsData>()
        for (i in viewModel.contacts.value.orEmpty()) {
            if (i.name.lowercase(Locale.ROOT).contains(query)) {
                filteredList.add(i)
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_LONG).show()
            // Clear the filtered list in the adapter to display nothing
            adapter.setFilteredContacts(ArrayList())
        } else {
            // Update the adapter with the filtered list
            adapter.setFilteredContacts(filteredList)
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