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

}