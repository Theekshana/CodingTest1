package com.example.codingtest1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codingtest1.databinding.ItemListBinding

class ContactsAdapter(var contactList: List<ContactsData>) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private var expandedPosition: Int = RecyclerView.NO_POSITION

    inner class ContactViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

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