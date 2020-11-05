package com.training.dbservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.training.AddressBook.AddressBook;
import com.training.contact.Contact;

public class AddressBookDirDBService {
	int counter = 0;
	PreparedStatement preparedStatement = null;

	private synchronized Connection getConnection() throws SQLException {
		counter++;
		String jdbcUrl = "jdbc:mysql://localhost:3306/address_book_service";
		String userName = "root";
		String passWord = "Yudha@123";
		Connection conn;
		System.out.println("Connecting to Database : " + jdbcUrl);
		System.out.println("Processing Thread : " + Thread.currentThread().getName()
				+ " Connecting to Database with id : " + counter);
		conn = DriverManager.getConnection(jdbcUrl, userName, passWord);
		System.out.println("Connection is successful : " + conn);
		System.out.println("Processing Thread : " + Thread.currentThread().getName() + " ID is " + counter
				+ "Connecting is successful : " + conn);
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
		String sql = "SELECT a.book_type,id,firstname,lastname,address,city,state,zip,phone_number,email,date_added FROM "
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
				int id = resultSet.getInt("id");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String address = resultSet.getString("address");
				String city = resultSet.getString("city");
				String state = resultSet.getString("state");
				String zip = resultSet.getString("zip");
				String phone_number = resultSet.getString("phone_number");
				String email = resultSet.getString("email");
				String date_added = resultSet.getDate("date_added").toString();
				String book_type = resultSet.getString("book_type");
				if (addressBookDirectory.containsKey(book_type)) {
					addressBookDirectory.get(book_type).getContact().add(new Contact(id, firstname, lastname, address,
							city, state, zip, phone_number, email, date_added));
				} else {
					addressBookDirectory.put(book_type, new AddressBook());
					addressBookDirectory.get(book_type).getContact().add(new Contact(id, firstname, lastname, address,
							city, state, zip, phone_number, email, date_added));
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
		String sql = String.format(
				"SELECT a.book_type,id,firstname,lastname,address,city,state,zip,phone_number,email,date_added FROM "
						+ "contacts b,address_book a,address_book_contacts c "
						+ "WHERE a.address_book_id=c.book_id AND c.contact_id=b.id AND "
						+ "firstname='%s' AND lastname='%s' ;",
				firstname, lastname);
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

	public Map<String, AddressBook> readAddressBooksForADateRange(Map<String, AddressBook> addressBookDirectory,
			String date) {
		String sql = String.format(
				"SELECT a.book_type,id,firstname,lastname,address,city,state,zip,phone_number,email,date_added FROM "
						+ "contacts b,address_book a,address_book_contacts c "
						+ "WHERE a.address_book_id=c.book_id AND c.contact_id=b.id AND "
						+ "date_added BETWEEN CAST('%s' AS DATE) AND DATE(NOW()) ;",
				date);
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

	public List<Contact> readAddressBooksForAColumn(Map<String, AddressBook> addressBookDirectory, String column,
			String value) {
		String sql = String.format(
				"SELECT a.book_type,id,firstname,lastname,address,city,state,zip,phone_number,email,date_added FROM "
						+ "contacts b,address_book a,address_book_contacts c "
						+ "WHERE a.address_book_id=c.book_id AND c.contact_id=b.id AND " + "%s='%s' ;",
				column, value);
		try {
			preparedStatement = getPreparedStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			addressBookDirectory = getDirectory(resultSet, addressBookDirectory);
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Contact> list = new LinkedList<>();
		for (Map.Entry<String, AddressBook> entry : addressBookDirectory.entrySet()) {
			for (Contact contact : entry.getValue().getContact()) {
				if (list.size() == 0)
					list.add(contact);
				boolean b = true;
				for (Contact con : list) {
					if (con.equalsContact(contact))
						b = false;
				}
				if (b)
					list.add(contact);
			}
		}
		return list;
	}

	public Contact addContact(int book_id, String firstname, String lastname, String address, String city, String state,
			String zip, String phone, String email, String date) {
		Connection[] connection = new Connection[] { null };
		try {
			connection[0] = this.getConnection();
			connection[0].setAutoCommit(false);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		Integer[] contact_id = new Integer[] { -1 };
		boolean[] status = { false };
		synchronized (this) {
			String sql = String.format(
					"INSERT INTO contacts(firstname,lastname,address,city,state,zip,phone_number,email,date_added) VALUES "
							+ "('%s','%s','%s','%s','%s', %s, '%s','%s','%s') ;",
					firstname, lastname, address, city, state, zip, phone, email, date);
			contact_id[0] = insertIntoContactsTableAndGetId(sql, connection[0]);

			Runnable task1 = () -> {
				this.insertIntoAddressBookContactsTable(book_id, contact_id[0], connection[0]);
				status[0] = true;
			};
			Thread thread1 = new Thread(task1);
			thread1.start();
		}
		while (status[0] == false) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			connection[0].commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null)
					connection[0].close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Contact newContact = new Contact(contact_id[0], firstname, lastname, address, city, state, zip, phone, email,
				date);
		return newContact;
	}

	private synchronized void insertIntoAddressBookContactsTable(int book_id, Integer contact_id,
			Connection connection) {
		String sqltoaddbook = String.format("INSERT INTO address_book_contacts(book_id,contact_id) VALUES (%d,%d) ;",
				book_id, contact_id);
		try {
			preparedStatement = connection.prepareStatement(sqltoaddbook);
			int rowAffected = preparedStatement.executeUpdate(sqltoaddbook, preparedStatement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	private synchronized Integer insertIntoContactsTableAndGetId(String sql, Connection connection) {
		try {
			preparedStatement = connection.prepareStatement(sql);
			int rowAffected = preparedStatement.executeUpdate(sql, preparedStatement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					return resultSet.getInt(1);
				}
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
			}
			e.printStackTrace();
		}
		return 0;
	}

}
