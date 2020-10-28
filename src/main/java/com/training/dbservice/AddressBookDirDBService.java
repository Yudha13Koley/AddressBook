package com.training.dbservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.training.AddressBook.AddressBook;
import com.training.Contact.Contact;

public class AddressBookDirDBService {

	PreparedStatement preparedStatement = null;

	private Connection getConnection() throws SQLException {
		String jdbcUrl = "jdbc:mysql://localhost:3306/address_book_service";
		String userName = "root";
		String passWord = "Yudha@123";
		Connection conn;
		System.out.println("Connecting to Database : " + jdbcUrl);
		conn = DriverManager.getConnection(jdbcUrl, userName, passWord);
		System.out.println("Connection is successful : " + conn);
		return conn;
	}

	private PreparedStatement getPreparedStatement(String sql) {
		try {
			Connection connection = this.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			return preparedStatement;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Map<String, AddressBook> readAddressBooks(Map<String, AddressBook> addressBookDirectory) {
		String sql = "SELECT a.book_type,firstname,lastname,address,city,state,zip,phone_number,email FROM "
				+ "contacts b,address_book a,address_book_contacts c "
				+ "WHERE a.address_book_id=c.book_id AND c.contact_id=b.id ;";
		try {
			preparedStatement = getPreparedStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			addressBookDirectory = getDirectory(resultSet, addressBookDirectory);
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookDirectory;
	}

	private Map<String, AddressBook> getDirectory(ResultSet resultSet, Map<String, AddressBook> addressBookDirectory) {
		try {
			while (resultSet.next()) {
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				String zip = resultSet.getString("zip");
				String phone_number = resultSet.getString("phone_number");
				String email = resultSet.getString("email");
				String book_type = resultSet.getString("book_type");
				if (addressBookDirectory.containsKey(book_type)) {
					addressBookDirectory.get(book_type).getContact()
							.add(new Contact(firstname, lastname, address, city, state, zip, phone_number, email));
				} else {
					addressBookDirectory.put(book_type, new AddressBook());
					addressBookDirectory.get(book_type).getContact()
							.add(new Contact(firstname, lastname, address, city, state, zip, phone_number, email));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressBookDirectory;
	}

	public int updateAddressOfContact(String firstname, String lastname, String address) {
		String sql = String.format("UPDATE contacts SET address='%s' WHERE firstname='%s' AND lastname='%s' ;", address,
				firstname, lastname);
		try {
			return getPreparedStatement(sql).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public Map<String, AddressBook> getContactFromDatabase(String firstname, String lastname) {
		String sql = String
				.format("SELECT a.book_type,firstname,lastname,address,city,state,zip,phone_number,email FROM "
						+ "contacts b,address_book a,address_book_contacts c "
						+ "WHERE a.address_book_id=c.book_id AND c.contact_id=b.id AND "
						+ "firstname='%s' AND lastname='%s' ;", firstname, lastname);
		Map<String, AddressBook> newMap = new HashMap<>();
		try {
			preparedStatement = getPreparedStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			newMap = getDirectory(resultSet, newMap);
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newMap;
	}

}
