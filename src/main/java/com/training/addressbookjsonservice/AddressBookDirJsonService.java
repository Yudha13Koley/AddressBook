package com.training.addressbookjsonservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.training.AddressBook.AddressBook;
import com.training.AddressBookDirectory.AddressBookDirectory;
import com.training.Contact.Contact;

public class AddressBookDirJsonService {

	private static final String SAMPLE_JSON_PATH = "./users.json";

	public Map<String, AddressBook> readAddressBooksFromJson(Map<String, AddressBook> addressBookDirectory) {
		try {
			JsonReader reader = new JsonReader(new FileReader(SAMPLE_JSON_PATH));

			Gson gson = new Gson();
			Map<String, AddressBook> ab = gson.fromJson(reader, addressBookDirectory.getClass());
			return ab;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addressBookDirectory;
	}

	public void printDirectoryInJson(Map<String, AddressBook> addressBookDirectory) {
		try {
			FileWriter writer = new FileWriter(SAMPLE_JSON_PATH);
			Gson gson = new Gson();
			String str = gson.toJson(addressBookDirectory);
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
