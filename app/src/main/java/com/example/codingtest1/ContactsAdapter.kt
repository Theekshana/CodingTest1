package com.example.codingtest1

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.codingtest1.databinding.ItemListBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ContactsAdapter(var contactList: ArrayList<ContactsData>,  private val sharedPreferences: SharedPreferences) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

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
                    Toast.makeText(view.context, "Edit text button is clicked", Toast.LENGTH_LONG)
                        .show()
                    true
                }

                R.id.delete -> {
                    deleteContact(position)
                    notifyDataSetChanged()
                    Toast.makeText(view.context, "Delete button is clicked", Toast.LENGTH_LONG)
                        .show()

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
            popUpMenu(it,position)
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