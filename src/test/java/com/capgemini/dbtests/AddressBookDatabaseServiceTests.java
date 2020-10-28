package com.capgemini.dbtests;

import org.junit.Assert;
import org.junit.Test;

import com.training.AddressBookDirectory.AddressBookDirectory;
import com.training.AddressBookDirectory.AddressBookDirectory.IOService;

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
		ABD.updateContactInDatabase("Ram", "Khan", "89/1 dharamshala road");
		boolean b = ABD.isSyncWithDatabase("Ram", "Khan");
		Assert.assertTrue(b);
	}
}
