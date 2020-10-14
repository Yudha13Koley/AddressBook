package com.training.addressbookjsonservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.training.AddressBook.AddressBook;
import com.training.Contact.Contact;

public class AddressBookDirJsonService {

private static final String SAMPLE_JSON_PATH = "./users.json";

public Map<String, AddressBook> readAddressBooksFromJson(Map<String, AddressBook> addressBookDirectory) {
	try {
		BufferedReader reader=new BufferedReader(new FileReader(SAMPLE_JSON_PATH));
		String line;
		Gson gson=new Gson();
		addressBookDirectory=(Map<String, AddressBook>) gson.fromJson(reader,Map.class);
		System.out.println(addressBookDirectory.get("Home").getContact());
		//while((line=reader.readLine())!=null)
		//{
		//	String str=line;
		//	List<Contact> list=null;
		//		list=(List<Contact>) gson.fromJson(reader,Contact.class);
		//		addressBookDirectory.put(str, new AddressBook(list));
		//}
	}
	catch(IOException e) {
		e.printStackTrace();
	}
	return addressBookDirectory;
}

public void printDirectoryInJson(Map<String, AddressBook> addressBookDirectory) {
	try {
		FileWriter writer=new FileWriter(SAMPLE_JSON_PATH);
		Gson gson=new Gson();
		//for(Map.Entry<String,AddressBook> entry:addressBookDirectory.entrySet()) {
			//writer.write(entry.getKey()+"\n");
			//String json=gson.toJson(entry.getValue().getContact());
			//writer.write(json+"\n");
		//}
		String str=gson.toJson(addressBookDirectory);
		writer.close();
	}
	catch(IOException e) {
		e.printStackTrace();
	}
	
}

}
