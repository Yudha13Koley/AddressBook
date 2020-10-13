package com.capgemini.addressbookdirectory;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.training.AddressBook.AddressBook;
import com.training.AddressBookDirectory.AddressBookDirectory;
import com.training.AddressBookDirectory.AddressBookDirectory.IOService;
import com.training.Contact.Contact;

public class AddressBookDirectoryTest {

	@Test
	public void test() {
		List<Contact>contact=new LinkedList<>();
		contact.add(new Contact("Yudha", "Koley", "123Street", "Howrah", "West Bengal", "711102", "72777377373", "yudhsa.com"));
		contact.add(new Contact("Ram", "Koley", "123Street", "Howrah", "West Bengal", "711102", "72777377373", "yudhsa.com"));
		contact.add(new Contact("Shyam", "Koley", "123Street", "Howrah", "West Bengal", "711102", "72777377373", "yudhsa.com"));
		Map<String,AddressBook>addressBookDirectory=new HashMap<>();
		addressBookDirectory.put("Home",new AddressBook(contact));
		addressBookDirectory.put("Work",new AddressBook(contact));
		AddressBookDirectory ABD =new AddressBookDirectory(addressBookDirectory);
		ABD.printDirectory(IOService.FILE_IO);
	}

}
