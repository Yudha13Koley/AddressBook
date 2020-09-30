package com.training.AddressBookDirectory;


import java.util.*;
import java.util.Map.Entry;

import com.training.AddressBook.AddressBook;
import com.training.Contact.Contact;

public class AddressBookDirectory {
private Map<String,AddressBook>addressBookDirectory=new HashMap<>();
public void addBooksInDirectory(Scanner sc) {
	System.out.println("Enter the Name of the Directory : ");
	String str=sc.next();
	addressBookDirectory.put(str,new AddressBook());
}
public void printDirectory(){
	for (Map.Entry<String,AddressBook> entry : addressBookDirectory.entrySet()) {
	    System.out.println(entry.getKey());
	    entry.getValue().printContacts();
	}	
}
public void accessDirectory(String str,Scanner sc) {
	loop : while(true) {
	System.out.println("Enter 1 to add contact in this Book : ");
	System.out.println("Enter 2 to edit contact in this Book : ");
	System.out.println("Enter 3 to delete contact in this Book : ");
	System.out.println("Enter 4 to print contact in this Book : ");
	System.out.println("Enter 5 to search contact in this Book : ");
	System.out.println("Enter 6 to exit : ");
	String choice=sc.next();
	switch(Integer.parseInt(choice)) {
	case 1:addressBookDirectory.get(str).addContact(sc);
	break;
	case 2:addressBookDirectory.get(str).editContact(sc);
	break;
	case 3:addressBookDirectory.get(str).deleteContact(sc);
	break;
	case 5:System.out.println(addressBookDirectory.get(str).searchContactsByFirstName(sc));
	break;
	case 4:addressBookDirectory.get(str).printContacts();
	break;
	case 6:addressBookDirectory.get(str).printContacts();
	break loop;
	default: System.out.println("Select From The Menu !");
	}
}
}
public void searchByCity(Scanner sc) {
	System.out.println("Enter the name of the City :");
	String str=sc.next();
	for (Map.Entry<String,AddressBook> entry : addressBookDirectory.entrySet()) {
	    System.out.println(entry.getKey());
	    entry.getValue().getContactsForCity(str);
	}
	}
	public void searchByState(Scanner sc) {
		System.out.println("Enter the name of the State :");
		String str=sc.next();
		for (Map.Entry<String,AddressBook> entry : addressBookDirectory.entrySet()) {
		    System.out.println(entry.getKey());
		    entry.getValue().getContactsForState(str);
		}
}
	public void dirCityPerson() {
		Map<String,LinkedList>cityPersonDir=new HashMap<>();
		for (Map.Entry<String,AddressBook> entry : addressBookDirectory.entrySet()) {
		   
		    entry.getValue().getCityPersonDir(cityPersonDir);
		}
		for (Map.Entry<String,LinkedList> entry : cityPersonDir.entrySet()) {
			   System.out.println(entry.getKey());
			   System.out.println("");
			   int counter=0;
			   Iterator<String> itr=entry.getValue().iterator();
			   while(itr.hasNext())
			   {
				   System.out.println(itr.next());
				   counter++;
			   }
			   System.out.println("No of Contacts Found : "+counter);
		}
	}
	public void dirStatePerson() {
		Map<String,LinkedList>statePersonDir=new HashMap<>();
		for (Map.Entry<String,AddressBook> entry : addressBookDirectory.entrySet()) {
		   
		    entry.getValue().getStatePersonDir(statePersonDir);
		}
		for (Map.Entry<String,LinkedList> entry : statePersonDir.entrySet()) {
			   System.out.println(entry.getKey());
			   System.out.println("");
			   int counter=0;
			   Iterator<String> itr=entry.getValue().iterator();
			   while(itr.hasNext())
			   {
				   System.out.println(itr.next());
				   counter++;
			   }
			   System.out.println("No of Contacts Found : "+counter);
		}
	}
}
