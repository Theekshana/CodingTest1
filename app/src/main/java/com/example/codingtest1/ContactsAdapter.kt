package com.example.codingtest1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codingtest1.databinding.ItemListBinding

class ContactsAdapter(var contactList: List<ContactsData>) :
    RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        val itemView =
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(itemView)

    }

    override fun getItemCount(): Int = contactList.size


    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        holder.binding.name.text = contactList[position].name
        holder.binding.number.text = contactList[position].phoneNumber
        holder.binding.description.text = contactList[position].description

    }
}