package com.training.main;

import java.util.*;

import com.training.AddressBookDirectory.AddressBookDirectory;

public class AddressBookMain {
	public static void main(String[] args) {
		System.out.println("Welcome To Address Book Program !");
		AddressBookDirectory A1=new AddressBookDirectory();
		Scanner sc=new Scanner(System.in);
		A1.addBooksInDirectory(sc);
		A1.addBooksInDirectory(sc);
		A1.addBooksInDirectory(sc);
		A1.printDirectory();
		A1.accessDirectory("Work", sc);
		sc.close();
	
	}

}
