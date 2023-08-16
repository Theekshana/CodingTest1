package com.example.codingtest1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codingtest1.databinding.ItemListBinding

class ContactsAdapter(var contactList: List<ContactsData>) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

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

        val isExpandable: Boolean = contacts.isExpandable
        holder.binding.number.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.binding.description.visibility = if (isExpandable) View.VISIBLE else View.GONE

        holder.binding.expandedLayout.setOnClickListener {

            contacts.isExpandable = !contacts.isExpandable
            notifyItemChanged(position , Unit)
        }









    }
}