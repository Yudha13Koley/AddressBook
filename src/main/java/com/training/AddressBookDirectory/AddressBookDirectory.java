package com.training.AddressBookDirectory;


import java.util.*;

import com.training.AddressBook.AddressBook;
import com.training.addressbookfileio.AddressBookFileIOService;

public class AddressBookDirectory {
private Map<String,AddressBook>addressBookDirectory=new HashMap<>();
public AddressBookDirectory() {
}
public AddressBookDirectory(Map<String, AddressBook> addressBookDirectory) {
	super();
	this.addressBookDirectory = addressBookDirectory;
}
public enum IOService {
	CONSOLE_IO, FILE_IO, DB_IO, REST_IO
}
public void addBooksInDirectory(Scanner sc) {
	System.out.println("Enter the Name of the Directory : ");
	String str=sc.next();
	addressBookDirectory.put(str,new AddressBook());
}
public void printDirectory(IOService ios){
	if(ios.equals(IOService.CONSOLE_IO)) {
	for (Map.Entry<String,AddressBook> entry : addressBookDirectory.entrySet()) {
	    System.out.println(entry.getKey());
	    entry.getValue().printContacts();
	}
	}
	else if(ios.equals(IOService.FILE_IO)) {
		new AddressBookFileIOService().printDirectoryInFile(addressBookDirectory);
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
	//addressBookDirectory=new AddressBookFileIOService().addContactsFromFile(addressBookDirectory,str);
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
	public void printSortedContacts(Scanner sc) {
		loop : while(true) {
			System.out.println("Enter 1 to sort by Name :");
			System.out.println("Enter 2 to sort by City :");
			System.out.println("Enter 3 to sort by State :");
			System.out.println("Enter 4 to sort by Zip :");
			System.out.println("Enter 5 to Exit :");
			String choice=sc.next();
			switch(Integer.parseInt(choice)) {
			case 1:for(Map.Entry<String,AddressBook> entry:addressBookDirectory.entrySet())
			{
				System.out.println(entry.getKey());
				entry.getValue().viewEntriesSortedByName();
			}
			break;
			case 2:for(Map.Entry<String,AddressBook> entry:addressBookDirectory.entrySet())
			{
				System.out.println(entry.getKey());
				entry.getValue().viewEntriesSortedByCity();
			}
			break;
			case 3:for(Map.Entry<String,AddressBook> entry:addressBookDirectory.entrySet())
			{
				System.out.println(entry.getKey());
				entry.getValue().viewEntriesSortedByState();
			}
			break;
			case 4:for(Map.Entry<String,AddressBook> entry:addressBookDirectory.entrySet())
			{
				System.out.println(entry.getKey());
				entry.getValue().viewEntriesSortedByZip();
			}
			break;
			case 5:break loop;
			default:System.out.println("Select From Menu : ");
			}
		}
	}
	public void readDirectory(IOService fileIo) {
		if(fileIo.equals(IOService.FILE_IO)) {
			addressBookDirectory=new AddressBookFileIOService().readAddressBooks(addressBookDirectory);
		}
	}
}
