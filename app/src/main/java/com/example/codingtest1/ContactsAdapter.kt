package com.example.codingtest1

import android.app.AlertDialog
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.codingtest1.databinding.ItemListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ContactsAdapter(
    var contactList: ArrayList<ContactsData>,
    private val sharedPreferences: SharedPreferences,
    private val viewModel: ContactsViewModel,

    ) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private var expandedPosition: Int = RecyclerView.NO_POSITION

    inner class ContactViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private fun deleteContact(position: Int) {
        if (position in contactList.indices) {
            val deletedContact = contactList.removeAt(position)
            notifyDataSetChanged()

            // Update SharedPreferences to remove the deleted contact
            updateSharedPreferences(deletedContact)
        }
    }

    fun setFilteredContacts(contactList: ArrayList<ContactsData>) {

        this.contactList = contactList
        notifyDataSetChanged()


    }

    private fun updateSharedPreferences(deletedContact: ContactsData) {
        val gson = Gson()
        val json: String? = sharedPreferences.getString("contacts", null)
        val savedContacts: ArrayList<ContactsData> =
            gson.fromJson(json, object : TypeToken<ArrayList<ContactsData>>() {}.type)

        savedContacts.remove(deletedContact)

        val updatedJson: String = gson.toJson(savedContacts)

        val editor = sharedPreferences.edit()
        editor.putString("contacts", updatedJson)
        editor.apply()
    }

    private fun popUpMenu(view: View, position: Int) {
        val popUpMenus = PopupMenu(view.context, view)
        popUpMenus.inflate(R.menu.show_menu)
        popUpMenus.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.editText -> {
                    val inflater = LayoutInflater.from(view.context)
                    val editView = inflater.inflate(R.layout.add_contact, null)
                    val editName = editView.findViewById<EditText>(R.id.etName)
                    val editNumber = editView.findViewById<EditText>(R.id.etNumber)
                    val editDescription = editView.findViewById<EditText>(R.id.etDescription)

                    val selectedContact = contactList[position]
                    editName.setText(selectedContact.name)
                    editNumber.setText(selectedContact.phoneNumber)
                    editDescription.setText(selectedContact.description)

                    AlertDialog.Builder(view.context)
                        .setView(editView)
                        .setPositiveButton("OK") { _, _ ->
                            val newName = editName.text.toString()
                            val newNumber = editNumber.text.toString()
                            val newDescription = editDescription.text.toString()
                            if (newName.isNotEmpty() && newNumber.isNotEmpty() && newDescription.isNotEmpty()) {
                                val editedContact = ContactsData(newName, newNumber, newDescription)
                                viewModel.editContact(editedContact)
                                contactList[position] = editedContact
                                notifyDataSetChanged()
                                saveContactsToSharedPreferences()
                                Toast.makeText(view.context, "Contact edited", Toast.LENGTH_LONG)
                                    .show()

                            } else {
                                Toast.makeText(
                                    view.context,
                                    "Please fill in all fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            Toast.makeText(view.context, "Cancel", Toast.LENGTH_LONG).show()
                        }
                        .create()
                        .show()

                    true
                }

                R.id.delete -> {
                    deleteContact(position)
                    notifyDataSetChanged()

                    true
                }

                else -> true

            }

        }
        popUpMenus.show()
        val popupMenu = PopupMenu::class.java.getDeclaredField("mPopup")
        popupMenu.isAccessible = true
        val menu = popupMenu.get(popUpMenus)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
    }

    private fun saveContactsToSharedPreferences() {
        val gson = Gson()
        val updatedJson: String = gson.toJson(viewModel.contacts.value)
        val editor = sharedPreferences.edit()
        editor.putString("contacts", updatedJson)
        editor.apply()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        val itemView =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(itemView)

    }

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        val contacts = contactList[position]
        holder.binding.name.text = contacts.name
        holder.binding.number.text = contacts.phoneNumber
        holder.binding.description.text = contacts.description


        holder.binding.menus.setOnClickListener {
            popUpMenu(it, position)
        }
        // Check if this item is the currently expanded one
        val isExpanded = position == expandedPosition

        // Update the visibility of phone number and description based on the expanded state
        holder.binding.number.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.binding.description.visibility = if (isExpanded) View.VISIBLE else View.GONE

        // Set a click listener on the expanded layout
        holder.binding.expandedLayout.setOnClickListener {
            val prevExpandedPosition = expandedPosition
            expandedPosition = if (isExpanded) RecyclerView.NO_POSITION else position

            // Update the expandable state for the previously expanded item
            if (prevExpandedPosition != RecyclerView.NO_POSITION) {
                contactList[prevExpandedPosition].isExpandable = false
                notifyItemChanged(prevExpandedPosition)

            }

            // Update the expandable state for the clicked item
            contacts.isExpandable = !isExpanded
            notifyItemChanged(position)
        }
    }
}