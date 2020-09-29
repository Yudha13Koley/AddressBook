package com.training.AddressBook;

import java.util.*;
import com.training.Contact.Contact;

public class AddressBook {
	private List<Contact>contact=new LinkedList<>();
	public void addConatct(Scanner sc) {
		Contact c=new Contact();
		System.out.println("Enter First Name :");
		c.setFirstName(sc.next());
		System.out.println("Enter Last Name :");
		c.setLastName(sc.next());
		System.out.println("Enter City Name :");
		c.setCity(sc.next());
		System.out.println("Enter State Name :");
		c.setState(sc.next());
		System.out.println("Enter Zip :");
		c.setZip(sc.next());
		System.out.println("Enter Address :");
		c.setAddress(sc.next());
		System.out.println("Enter Phone No :");
		c.setPhoneNo(sc.next());
		System.out.println("Enter email :");
		c.setEmail(sc.next());
		contact.add(c);
		System.out.println("Contact Saved !");
		System.out.println(c);
	}
	public void printContacts() {
		Iterator<Contact> itr=contact.iterator();
		while(itr.hasNext())
		{
			System.out.println(itr.next());
		}
	}

}
