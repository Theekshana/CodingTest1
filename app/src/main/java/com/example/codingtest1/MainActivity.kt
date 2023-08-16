package com.example.codingtest1

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

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: ContactsAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Initialize the ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]

        binding.contactsRecyclerView.setHasFixedSize(true)
        binding.contactsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the RecyclerView Adapter with the initial contact list
        adapter = ContactsAdapter(viewModel.contacts.value ?: emptyList())
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
                adapter.contactList = contactsList
                adapter.notifyDataSetChanged()

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

}