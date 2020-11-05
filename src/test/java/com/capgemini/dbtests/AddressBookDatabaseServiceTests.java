package com.capgemini.dbtests;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.training.AddressBookDirectory.AddressBookDirectory;
import com.training.AddressBookDirectory.AddressBookDirectory.IOService;
import com.training.contact.BookAndContactDetails;
import com.training.contact.Contact;
import com.training.contact.BookAndContactDetails.BookType;
import com.training.dbservice.AddressBookDirDBService;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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
		ABD.updateContactInDatabase("Ram", "Khan", "89/1 lindsey street");
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
		AddressBookDirDBService dbService = new AddressBookDirDBService();
		ABD.readDirectory(IOService.DB_IO);
		ABD.addContactInDatabase(dbService, 3, "Bina", "Kamal", "sadar natin laane", "Bangalore", "Karnataka", "489025",
				"7277282884", "etgsgshs@gmail.com", "2020-10-29");
		Assert.assertTrue(ABD.isSyncWithDatabase("Bina", "Kamal"));
	}

	@Test
	public void givenADatabase_whenAddedMultipleConatact_returnCountOFContacs() {
		AddressBookDirectory ABD = new AddressBookDirectory();
		ABD.readDirectory(IOService.DB_IO);
		List<BookAndContactDetails> contactAndBookList = new ArrayList<>();
		contactAndBookList
				.add(new BookAndContactDetails(BookType.Family, new Contact(0, "Bina", "Kamal", "sadar natin laane",
						"Bangalore", "Karnataka", "489025", "7277282884", "etgsgshs@gmail.com", "2020-10-29")));
		contactAndBookList
				.add(new BookAndContactDetails(BookType.Family, new Contact(0, "Binayak", "Kamal", "sadar natin laane",
						"Bangalore", "Karnataka", "489025", "7277282884", "etgsgshs@gmail.com", "2020-10-29")));
		contactAndBookList.add(
				new BookAndContactDetails(BookType.Profession, new Contact(0, "Patal", "Kamal", "sadar natin laane",
						"Bangalore", "Karnataka", "489025", "7277282884", "etgsgshs@gmail.com", "2020-10-29")));
		ABD.addMultipleContactsInDatabase(contactAndBookList);
		int n = ABD.getCountOFEntries();
		ABD.printDirectory(IOService.CONSOLE_IO);
		Assert.assertEquals(15, n);
	}

	@Test
	public void givenADatabase_whenRetrievedData_givesADBjsonFile() {
		AddressBookDirectory addressBookDirectoryService = new AddressBookDirectory();
		addressBookDirectoryService.readDirectory(IOService.DB_IO);
		addressBookDirectoryService.setNewAddressBook();
		try {
			FileWriter writer = new FileWriter("./contactDB.json");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String str = gson.toJson(addressBookDirectoryService.getNewAddressBook());
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	public Map<String, List<Contact>> getContacts() {
		List<String> booklist = Arrays.asList(new String[] { "Family", "Friend", "Profession" });
		Map<String, Boolean> statusCodes = new HashMap<>();
		Map<String, List<Contact>> directory = new HashMap<>();
		booklist.forEach(nameOfBook -> {
			statusCodes.put(nameOfBook, false);
			Response response = RestAssured.get("/" + nameOfBook);
			System.out.println("Employee Payroll entries in Json Server :" + nameOfBook + " \n" + response.asString());
			directory.put(nameOfBook, new Gson().fromJson(response.asString(), new TypeToken<List<Contact>>() {
			}.getType()));
			statusCodes.put(nameOfBook, true);
		});
		return directory;
	}

	private boolean addContactToJsonServer(Contact contact, String bookname, AddressBookDirectory dir) {
		String contactJson = new GsonBuilder().setPrettyPrinting().create().toJson(contact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-type", "application/json");
		request.body(contactJson);
		Response response = request.post("/" + bookname);
		int statusCode = response.getStatusCode();
		Contact newAddedContact = new Gson().fromJson(response.asString(), Contact.class);
		dir.getAddressBookDirectory().get(bookname).getContact().add(newAddedContact);
		dir.getNewAddressBook().get(bookname).add(newAddedContact);
		boolean result = this.isSyncWithJsonServer(newAddedContact, bookname, dir);
		return result && (statusCode == 201);
	}

	private boolean isSyncWithJsonServer(Contact newAddedContact, String bookname, AddressBookDirectory dir) {
		boolean result = false;
		for (Contact contact : dir.getAddressBookDirectory().get(bookname).getContact()) {
			if (contact.equals(newAddedContact))
				result = true;
		}
		return result;
	}

	private boolean addMultipleContactToJsonServer(List<BookAndContactDetails> contactAndBookList,
			AddressBookDirectory dir) {
		Map<Integer, Boolean> contactAdditionalStatus = new HashMap<>();
		contactAndBookList.forEach(details -> {
			contactAdditionalStatus.put(details.getContact().hashCode(), false);
		});
		contactAndBookList.forEach(contact -> {
			Runnable task = () -> {
				System.out.println("Contact Being Added : " + Thread.currentThread().getName());
				boolean status = addContactToJsonServer(contact.getContact(), contact.getBookName(), dir);
				if (status) {
					contactAdditionalStatus.put(contact.getContact().hashCode(), true);
				}
				System.out.println("Contact Added : " + Thread.currentThread().getName());
			};
			Thread thread = new Thread(task, contact.getContact().getFirstName());
			thread.start();
		});
		while (contactAdditionalStatus.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (!contactAdditionalStatus.containsValue(false))
			return true;
		else
			return false;
	}

	private boolean updateEntriesInJsonServer(String firstname, String lastname, String column, String columnvalue,
			AddressBookDirectory dir) {
		dir.updateEntriesInDirectory(firstname, lastname, column, columnvalue);
		List<BookAndContactDetails> list = dir.getContactAndBookDetailsByName(firstname, lastname);
		Map<String, Boolean> updateStatus = new HashMap<>();
		list.forEach(details -> {
			updateStatus.put(details.getContact().getFirstName(), false);
		});
		list.forEach(details -> {
			String contactJson = new Gson().toJson(details.getContact());
			RequestSpecification request = RestAssured.given();
			request.header("Content-type", "application/json");
			request.body(contactJson);
			Response response = request.put("/" + details.getBookName() + "/" + details.getContact().getId());
			boolean isSync = isSyncWithJsonServer(new Gson().fromJson(response.asString(), Contact.class),
					details.getBookName(), dir);
			if (response.getStatusCode() == 200 && isSync) {
				updateStatus.put(details.getContact().getFirstName(), true);
			}
		});
		if (!updateStatus.containsValue(false))
			return true;
		else
			return false;
	}

	private boolean deleteContactInJsonServer(String firstname, String lastname, AddressBookDirectory dir) {
		List<BookAndContactDetails> list = dir.getContactAndBookDetailsByName(firstname, lastname);
		Map<String, Boolean> deleteStatus = new HashMap<>();
		list.forEach(details -> {
			deleteStatus.put(details.getContact().getFirstName(), false);
		});
		list.forEach(details -> {
			RequestSpecification request = RestAssured.given();
			request.header("Content-type", "application/json");
			Response response = request.delete("/" + details.getBookName() + "/" + details.getContact().getId());
			if (response.getStatusCode() == 200) {
				deleteStatus.put(details.getContact().getFirstName(), true);
			}
		});
		dir.deleteContactsInDirectory(firstname, lastname);
		if (!deleteStatus.containsValue(false))
			return true;
		else
			return false;
	}

	@Test
	public void givenContactDetailsInJsonServer_whenRetrieved_shouldReturnNoOfCounts() {
		Map<String, List<Contact>> data = getContacts();
		AddressBookDirectory dir = new AddressBookDirectory();
		dir.setNewAddressBook(data);
		int entries = dir.getCountOFEntries();
		dir.printDirectory(IOService.CONSOLE_IO);
		Assert.assertEquals(12, entries);
	}

	@Test
	public void givenContactDetailsInJsonServer_whenAddedAConatct_shouldReturnNoOfCountsAndResponseCode() {
		Map<String, List<Contact>> data = getContacts();
		AddressBookDirectory dir = new AddressBookDirectory();
		dir.setNewAddressBook(data);
		Contact contact = new Contact("Raman", "Das", "sadar natin laane", "Bangalore", "Karnataka", "489025",
				"7277282884", "etgsgshs@gmail.com", "2020-10-29");
		boolean result = addContactToJsonServer(contact, "Profession", dir);
		Assert.assertEquals(true, result);
		dir.printDirectory(IOService.CONSOLE_IO);
		int entries = dir.getCountOFEntries();
		Assert.assertEquals(13, entries);
	}

	@Test
	public void givenContactDetailsInJsonServer_whenAddedMultipleConatct_shouldReturnNoOfCounts() {
		Map<String, List<Contact>> data = getContacts();
		AddressBookDirectory dir = new AddressBookDirectory();
		dir.setNewAddressBook(data);
		List<BookAndContactDetails> contactAndBookList = new ArrayList<>();
		contactAndBookList
				.add(new BookAndContactDetails(BookType.Family, new Contact(0, "Bina", "Kamal", "sadar natin laane",
						"Bangalore", "Karnataka", "489025", "7277282884", "etgsgshs@gmail.com", "2020-10-29")));
		contactAndBookList
				.add(new BookAndContactDetails(BookType.Family, new Contact(0, "Binayak", "Kamal", "sadar natin laane",
						"Bangalore", "Karnataka", "489025", "7277282884", "etgsgshs@gmail.com", "2020-10-29")));
		contactAndBookList.add(
				new BookAndContactDetails(BookType.Profession, new Contact(0, "Patal", "Kamal", "sadar natin laane",
						"Bangalore", "Karnataka", "489025", "7277282884", "etgsgshs@gmail.com", "2020-10-29")));
		boolean result = addMultipleContactToJsonServer(contactAndBookList, dir);
		Assert.assertEquals(true, result);
		dir.printDirectory(IOService.CONSOLE_IO);
		Assert.assertEquals(16, dir.getCountOFEntries());
	}

	@Test
	public void givenContactDetailsInJsonServer_whenUpdatedContacts_shouldSyncWithJsonServer() {
		Map<String, List<Contact>> data = getContacts();
		AddressBookDirectory dir = new AddressBookDirectory();
		dir.setNewAddressBook(data);
		dir.printDirectory(IOService.CONSOLE_IO);
		boolean result = updateEntriesInJsonServer("ram", "khan", "address", "11/2 sardar ballavbhai road", dir);
		dir.printDirectory(IOService.CONSOLE_IO);
		Assert.assertTrue(result);
	}

	@Test
	public void givenContactDetailsInJsonServer_whenDeletedContact_shouldSyncWithJsonServer() {
		Map<String, List<Contact>> data = getContacts();
		AddressBookDirectory dir = new AddressBookDirectory();
		dir.setNewAddressBook(data);
		dir.printDirectory(IOService.CONSOLE_IO);
		boolean result = deleteContactInJsonServer("nitin", "rana", dir);
		dir.printDirectory(IOService.CONSOLE_IO);
		Assert.assertTrue(result);
		Assert.assertEquals(9, dir.getCountOFEntries());
	}

}
