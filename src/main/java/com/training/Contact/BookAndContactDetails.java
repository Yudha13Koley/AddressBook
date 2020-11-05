package com.training.contact;

public class BookAndContactDetails {

	public enum BookType {
		Friend, Family, Profession
	};

	private BookType type;
	private Contact contact;
	private int typeid;
	private String bookName;

	public BookAndContactDetails(BookType type, Contact contact) {
		super();
		this.type = type;
		this.contact = contact;
		if (type.equals(BookType.Family)) {
			this.setTypeid(1);
			this.bookName = "Family";
		} else if (type.equals(BookType.Friend)) {
			this.setTypeid(2);
			this.bookName = "Friend";
		} else if (type.equals(BookType.Profession)) {
			this.setTypeid(3);
			this.bookName = "Profession";
		}
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public BookType getType() {
		return type;
	}

	public void setType(BookType type) {
		this.type = type;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

}
