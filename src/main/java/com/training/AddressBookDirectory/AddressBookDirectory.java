package com.training.AddressBookDirectory;


import java.util.*;

import com.training.AddressBook.AddressBook;

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
	case 5:if(addressBookDirectory.get(str).getContact().size()==0)
	{
		System.out.println("Address Book is Empty !");
		break;
	}
	else
	{
		System.out.println(addressBookDirectory.get(str).searchContactsByFirstName(sc));
		break;
	}
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
		Map<String,LinkedList<String>>cityPersonDir=new HashMap<>();
		for (Map.Entry<String,AddressBook> entry : addressBookDirectory.entrySet()) {
			entry.getValue().getCityPersonDir(cityPersonDir);
		}
		for (Map.Entry<String,LinkedList<String>> entry : cityPersonDir.entrySet()) {
			   System.out.println("City : "+entry.getKey()+" No of Contacts Found : "+entry.getValue().stream().count());
			   System.out.println("");
			   entry.getValue().forEach(s->{System.out.println(s);});
			   System.out.println("");
		}
	}
	public void dirStatePerson() {
		Map<String,LinkedList<String>>statePersonDir=new HashMap<>();
		for (Map.Entry<String,AddressBook> entry : addressBookDirectory.entrySet()) {
		    entry.getValue().getStatePersonDir(statePersonDir);
		}
		for (Map.Entry<String,LinkedList<String>> entry : statePersonDir.entrySet()) {
			   System.out.println(entry.getKey()+" No of Contacts Found : "+entry.getValue().stream().count());
			   System.out.println("");
			   entry.getValue().stream().forEach(s->{System.out.println(s);});
			   System.out.println("");
		}
	}
	public void printSortedContacts() {
		for(Map.Entry<String,AddressBook> entry:addressBookDirectory.entrySet()) {
			System.out.println(entry.getKey());
			entry.getValue().viewEntriesSortedByName();
		}
	}
}
