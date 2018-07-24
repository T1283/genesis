package com.prgm.communications.mail;

import java.util.List;

import sendinblue.ApiClient;
import sendinblue.ApiException;
import sibApi.ContactsApi;
import sibApi.ListsApi;
import sibModel.AddContactToList;
import sibModel.CreateContact;
import sibModel.CreateList;
import sibModel.CreateModel;
import sibModel.PostContactInfo;

public class Contacts
{
	public ContactsApi contactsApi = null;
	public ApiClient client = null;
	Long contactListId = 0L;
	
	public Contacts(ApiClient client)
	{
		contactsApi = new ContactsApi();
		contactsApi.setApiClient(client);
		this.client = client;
	}
	
	public CreateModel createContact(String email) throws Exception
	{
		try
		{
			CreateContact contact = new CreateContact();
			contact.setEmail(email);
			
			return contactsApi.createContact(contact);
		}
		catch(ApiException apiEx)
		{
			System.err.println("Error while creating contact : " + email + "\n" + apiEx.getResponseBody());
			throw new Exception(apiEx.getResponseBody());
		}
	}
	
	public AddContactToList createContacts(List<String> emails) throws Exception
	{
		AddContactToList contacts = new AddContactToList();
		contacts.emails(emails);
		return contacts;
	}
	
	public CreateModel createList(String listName, Long folderId) throws Exception
	{
		CreateModel contactList = null;
		
		try
		{
			ListsApi listsApi = new ListsApi();
			listsApi.setApiClient(client);
			
			CreateList list = new CreateList();
			list.setName(listName);
			list.setFolderId(folderId);
			
			contactList = listsApi.createList(list);
			this.contactListId = contactList.getId();
			System.out.println("Created list " + listName + " with id as " + contactList.getId());
		}
		catch(ApiException apiEx)
		{
			System.err.println("Error while creating list with name " + listName + "\n" + apiEx.getResponseBody());
			throw new Exception(apiEx.getResponseBody());
		}
		return contactList;
	}
	
	public void addContactsToList(Long listId, AddContactToList contactList) throws Exception
	{
		try
		{
			PostContactInfo addContactsToListResult = contactsApi.addContactToList(listId, contactList);
			List<String> successContacts = addContactsToListResult.getContacts().getSuccess();
			
			System.out.println("Following contacts where added successfully to the list\n" + successContacts.toString());
		}
		catch(ApiException apiEx)
		{
			System.err.println("Error while adding contacts to list : " + "\n" + apiEx.getResponseBody());
			throw new Exception(apiEx.getResponseBody());
		}
	}
	
	public void createAndAddContactsToList(List<String> emails, String listName, Long folderId) throws Exception
	{
		addContactsToList(createList(listName, folderId).getId(), createContacts(emails));
	}
	
	public Long getContactListId()
	{
		return this.contactListId;
	}
	
}
