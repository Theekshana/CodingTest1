package com.example.codingtest1

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
import com.example.codingtest1.model.ContactsData
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: ContactsAdapter
    private lateinit var binding: ActivityMainBinding

    private var list: List<ContactsData> = emptyList()

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
                list = contactsList
                adapter.contactList = list
                adapter.notifyDataSetChanged()

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
        //adapter = ContactsAdapter(userList as ArrayList<ContactsData>, viewModel)

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
        adapter = ContactsAdapter(list, viewModel)

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
                    val contact = ContactsData(0, name, number, description)
                    viewModel.addContact(contact)

                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, getString(R.string.adding_contact), Toast.LENGTH_LONG)
                        .show()

                } else {
                    Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_SHORT)
                        .show()

                }

            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Cancel", Toast.LENGTH_LONG).show()
            }
            .create()
            .show()
    }


}