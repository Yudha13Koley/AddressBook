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
	while(true) {
	System.out.println("Enter 1 to add contact in this directory : ");
	System.out.println("Enter 2 to edit contact in this directory : ");
	System.out.println("Enter 3 to delete contact in this directory : ");
	System.out.println("Enter 4 to print contact in this directory : ");
	System.out.println("Enter 5 to search contact in this directory : ");
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
	default: System.out.println("Select From The Menu !");
	}
}
}
}
