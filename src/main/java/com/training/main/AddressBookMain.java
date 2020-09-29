package com.training.main;

import java.util.*;

import com.training.AddressBookDirectory.AddressBookDirectory;

public class AddressBookMain {
	public static void main(String[] args) {
		System.out.println("Welcome To Address Book Program !");
		AddressBookDirectory A1=new AddressBookDirectory();
		Scanner sc=new Scanner(System.in);
		while(true) {
			System.out.println("Enter 1 to Add Address Book in this directory : ");
			System.out.println("Enter 2 to Access Address Book in this directory : ");
			System.out.println("Enter 3 to Print Address Books in this directory : ");
			System.out.println("Enter 4 to exit : ");
			String choice=sc.next();
			switch(Integer.parseInt(choice)) {
			case 1:A1.addBooksInDirectory(sc);
				break;
			case 2:System.out.println("Enter the Key of Address Book : ");
			    String str=sc.next();
				A1.accessDirectory(str, sc);
			    break;
			case 3:A1.printDirectory();
				break;
			case 4:sc.close();
				System.exit(0);
			default: System.out.println("Select From The Menu !");
			}
		}
	}
}
