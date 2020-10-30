package com.capgemini.dbtests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.training.AddressBookDirectory.AddressBookDirectory;
import com.training.AddressBookDirectory.AddressBookDirectory.IOService;
import com.training.Contact.Contact;

public class AddressBookDatabaseServiceTests {

	@Test
	public void givenADatabase_whenRetrievedDataFromDatabase_returnsCountofDataReceived() {
		AddressBookDirectory ABD = new AddressBookDirectory();
		ABD.readDirectory(IOService.DB_IO);
		int n = ABD.getCountOFEntries();
		ABD.printDirectory(IOService.CONSOLE_IO);
		Assert.assertEquals(12, n);
	}

	@Test
	public void givenADatabase_whenUpdatedDataForAContact_returnsIsSyncWithDatabase() {
		AddressBookDirectory ABD = new AddressBookDirectory();
		ABD.readDirectory(IOService.DB_IO);
		ABD.updateContactInDatabase("Ram", "Khan", "89/1 dharamshala haman road");
		boolean b = ABD.isSyncWithDatabase("Ram", "Khan");
		Assert.assertTrue(b);
	}

	@Test
	public void givenADatabase_whenRetrievedForPerticularDateRange_returnsCountOfContacts() {
		AddressBookDirectory ABD = new AddressBookDirectory();
		ABD.readDirectoryForADateRange("2019-01-01");
		int n = ABD.getCountOFEntries();
		ABD.printDirectory(IOService.CONSOLE_IO);
		Assert.assertEquals(9, n);
	}

	@Test
	public void givenADatabase_whenRetrievedForACity_returnsCountOfContacts() {
		AddressBookDirectory ABD = new AddressBookDirectory();
		List<Contact> list = ABD.readDirectoryForAColumn("city", "Howrah");
		System.out.println(list);
		Assert.assertEquals(3, list.size());
	}

	@Test
	public void givenADatabase_whenRetrievedForAState_returnsCountOfContacts() {
		AddressBookDirectory ABD = new AddressBookDirectory();
		List<Contact> list = ABD.readDirectoryForAColumn("state", "West Bengal");
		System.out.println(list);
		Assert.assertEquals(4, list.size());
	}

	@Test
	public void givenADatabase_whenAddedAConatact_returnsisSyncWithDatabase() {
		AddressBookDirectory ABD = new AddressBookDirectory();
		ABD.readDirectory(IOService.DB_IO);
		ABD.addContactInDatabase(3, "Bina", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
				"7277282884", "etgsgshs@gmail.com", "2020-10-29");
		Assert.assertTrue(ABD.isSyncWithDatabase("Bina", "Kamal"));
	}

	@Test
	public void givenADatabase_whenAddedMultipleConatact_returnsisSyncWithDatabase() {
		AddressBookDirectory ABD = new AddressBookDirectory();
		ABD.readDirectory(IOService.DB_IO);
		Map<Integer, Contact> contactMap = new HashMap<>();
		contactMap.put(1, new Contact("Bina", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
				"7277282884", "etgsgshs@gmail.com", "2020-10-29"));
		contactMap.put(2, new Contact("Binayak", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
				"7277282884", "etgsgshs@gmail.com", "2020-10-29"));
		contactMap.put(3, new Contact("Patal", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
				"7277282884", "etgsgshs@gmail.com", "2020-10-29"));
		ABD.addMultipleContactsInDatabase(contactMap);
		int n = ABD.getCountOFEntries();
		ABD.printDirectory(IOService.CONSOLE_IO);
		Assert.assertEquals(15, n);

	}
}
