package edu.asupoly.ser422.lab3.service;

import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_FILE;
import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_PHONEBOOK_FILES_PATH_KEY;
import static edu.asupoly.ser422.lab3.utils.Utils.DEFAULT_FILENAME;
import static edu.asupoly.ser422.lab3.utils.Utils.EXTENSION;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
			if (pbookName != null) {
				InputStream is = new FileInputStream(resolveFullPath(pbookName));
				return new PhoneBook(is);
			} else {
				return new PhoneBook(new FileInputStream(resolveFullPath(DEFAULT_FILENAME)));
			}
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
		String path = properties.getProperty(CONFIG_PHONEBOOK_FILES_PATH_KEY) + "/" + fileName;
		path += fileName.contains(EXTENSION) ? "" : EXTENSION;
		return path;
	}

	public List<PhoneBook> getPhoneBooks() throws CustomException {
		List<PhoneBook> pbooks = new ArrayList<>();
		File folder = new File(properties.getProperty(CONFIG_PHONEBOOK_FILES_PATH_KEY));
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
			if (file.isFile() && file.getName() != null && file.getName().contains(EXTENSION)) {
				try {
					pbooks.add(new PhoneBook(new FileInputStream(file), file.getName()));
				} catch (IOException | CustomException e) {
					throw new CustomException(e.getMessage());
				}
			}
		}
		return pbooks;
	}
}
