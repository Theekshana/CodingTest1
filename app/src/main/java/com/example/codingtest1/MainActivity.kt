package com.example.codingtest1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: ContactsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.contactsRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the RecyclerView Adapter with the initial contact list
        adapter = ContactsAdapter(viewModel.contacts.value ?: emptyList())
        recyclerView.adapter = adapter

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

}