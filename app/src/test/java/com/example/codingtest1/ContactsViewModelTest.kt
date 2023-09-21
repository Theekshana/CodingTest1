package com.example.codingtest1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
internal class ContactsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ContactsViewModel

    @Mock
    lateinit var observer: Observer<ArrayList<ContactsData>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = ContactsViewModel()
    }

    @Test
    fun testAddContact() {
        val testContact = ContactsData("name", "123456789", "description")

        viewModel.contacts.observeForever(observer)

        viewModel.addContact(testContact)

        Mockito.verify(observer, Mockito.atLeastOnce()).onChanged(Mockito.any())

        val liveDataValue = viewModel.contacts.value
        assert(liveDataValue != null)
        assert(liveDataValue!!.contains(testContact))

        viewModel.contacts.removeObserver(observer)
    }

    @Test
    fun testLoadContacts() {

        val testContacts = arrayListOf(
            ContactsData("John", "12345", "Friend"),
            ContactsData("Alice", "67890", "Family")
        )

        viewModel.contacts.observeForever(observer)

        viewModel.loadContacts(testContacts)

        Mockito.verify(observer).onChanged(testContacts)

        val liveDataValue = viewModel.contacts.value

        assert(liveDataValue != null)

        assert(liveDataValue == testContacts)

    }

    @Test
    fun testEditContact() {

        val initialContacts = arrayListOf(
            ContactsData("John", "12345", "Friend"),
            ContactsData("Alice", "67890", "Family"),
            ContactsData("Bob", "54321", "Colleague")
        )

        viewModel.loadContacts(initialContacts)

        viewModel.contacts.observeForever(observer)

        val newContact = ContactsData("John Doe", "12345", "Best Friend")

        viewModel.editContact(newContact)

        Mockito.verify(observer, Mockito.atLeastOnce()).onChanged(Mockito.any())

        val updatedContacts = viewModel.contacts.value
        assert(updatedContacts != null)
        assert(updatedContacts!!.contains(newContact))
    }

}