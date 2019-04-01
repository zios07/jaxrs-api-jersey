package edu.asupoly.ser422.lab3.service;

import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_FILE;
import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_PHONEBOOK_FILES_PATH_KEY;
import static edu.asupoly.ser422.lab3.utils.Utils.DEFAULT_FILENAME;
import static edu.asupoly.ser422.lab3.utils.Utils.EXTENSION;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import edu.asupoly.ser422.lab3.model.PhoneBook;

public class PhoneBookService {

	public PhoneBook getPhoneBook(String pbookName) throws IOException {
		Properties properties = new Properties();
		try {
			properties.load(PhoneBookService.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
			String path = properties.getProperty(CONFIG_PHONEBOOK_FILES_PATH_KEY);
			path += "/" + pbookName + EXTENSION;
			InputStream is = new FileInputStream(path);
			return new PhoneBook(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new PhoneBook(pbookName);
	}

	public PhoneBook deletePhoneBook(String pbookName) {
		return null;
	}

	public PhoneBook getPhoneBook() throws IOException {
		return this.getPhoneBook(DEFAULT_FILENAME);
	}

}
