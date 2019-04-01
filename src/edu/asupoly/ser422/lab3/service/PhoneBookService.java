package edu.asupoly.ser422.lab3.service;

import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_FILE;
import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_PHONEBOOK_FILES_PATH_KEY;
import static edu.asupoly.ser422.lab3.utils.Utils.EXTENSION;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import edu.asupoly.ser422.lab3.exception.CustomException;
import edu.asupoly.ser422.lab3.model.PhoneBook;

public class PhoneBookService {

	private static Properties properties = new Properties();
	static {
		try {
			properties.load(PhoneBookService.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PhoneBook getPhoneBook(String pbookName) throws CustomException {
		try {
			InputStream is = new FileInputStream(resolveFullPath(pbookName));
			return new PhoneBook(is);
		} catch (IOException e) {
			throw new CustomException("File with name : " + pbookName + " does not exist!");
		}
	}

	public PhoneBook deletePhoneBook(String pbookName) throws CustomException {
		PhoneBook pbook = getPhoneBook(pbookName);
		if (pbook != null && !pbook.get_pbook().isEmpty()) {
			throw new CustomException("File contain entries, it cannot be deleted");

		} else {
			String path = resolveFullPath(pbookName);
			try {
				FileUtils.forceDelete(new File(path));
			} catch (IOException e) {
				throw new CustomException(e.getMessage());
			}
		}
		return null;
	}

	private String resolveFullPath(String fileName) {
		return properties.getProperty(CONFIG_PHONEBOOK_FILES_PATH_KEY) + "/" + fileName + EXTENSION;
	}
}
