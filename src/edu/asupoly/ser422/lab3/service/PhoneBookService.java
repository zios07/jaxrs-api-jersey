package edu.asupoly.ser422.lab3.service;

import java.io.IOException;

import edu.asupoly.ser422.lab3.model.PhoneBook;

public class PhoneBookService {

	public PhoneBook getPhoneBook(String pbookName) throws IOException {
		return new PhoneBook(pbookName);
	}

	public PhoneBook deletePhoneBook(String pbookName) {
		return null;
	}

	public PhoneBook getPhoneBook() throws IOException {
		return this.getPhoneBook(PhoneBook.DEFAULT_FILENAME);
	}

}
