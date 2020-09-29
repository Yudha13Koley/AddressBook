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
			System.out.println("Enter 4 to search contacts in dictionary by City : ");
			System.out.println("Enter 5 to search contacts in dictionary by State : ");
			System.out.println("Enter 6 to create City Person Directory : ");
			System.out.println("Enter 7 to create State Person Directory : ");
			System.out.println("Enter 8 to exit : ");
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
			case 4:A1.searchByCity(sc);
			break;
			case 5:A1.searchByState(sc);
			break;
			case 6:A1.dirCityPerson();
			case 7:A1.dirStatePerson();
			case 8:sc.close();
				System.exit(0);
			default: System.out.println("Select From The Menu !");
			}
		}
	}
}
