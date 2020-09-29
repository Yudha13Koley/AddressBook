package com.training.main;

import java.util.*;

import com.training.AddressBook.AddressBook;

public class AddressBookMain {
	public static void main(String[] args) {
		System.out.println("Welcome To Address Book Program !");
		AddressBook A1=new AddressBook();
		Scanner sc=new Scanner(System.in);
		A1.addContact(sc);
		A1.addContact(sc);
		A1.addContact(sc);
		A1.addContact(sc);
		
		sc.close();
	
	}

}
