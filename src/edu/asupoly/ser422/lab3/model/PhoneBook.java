package edu.asupoly.ser422.lab3.model;

import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_FILE;
import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_PHONEBOOK_FILES_PATH_KEY;
import static edu.asupoly.ser422.lab3.utils.Utils.DEFAULT_FILENAME;
import static edu.asupoly.ser422.lab3.utils.Utils.UNLISTED_FILENAME;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import edu.asupoly.ser422.lab3.exception.CustomException;
import edu.asupoly.ser422.lab3.listener.ApplicationInitializer;

public class PhoneBook implements Serializable {

	private static final long serialVersionUID = -2134102172191011457L;

	private Map<String, PhoneEntry> _pbook = new HashMap<String, PhoneEntry>();
	private String fname;

	private Gson gson = null;

	public PhoneBook() throws IOException, CustomException {
		this(DEFAULT_FILENAME);
	}

	public PhoneBook(String fname) throws IOException, CustomException {
		this(PhoneBook.class.getClassLoader().getResourceAsStream(fname));
	}

	public PhoneBook(InputStream is) throws IOException, CustomException {
		this(new BufferedReader(new InputStreamReader(is)));
	}

	public PhoneBook(InputStream is, String fname) throws IOException, CustomException {
		this(new BufferedReader(new InputStreamReader(is)));
		this.fname = fname;
	}

	private PhoneBook(BufferedReader br) throws IOException, CustomException {
		gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			StringBuilder sb = new StringBuilder();

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			Type listType = new TypeToken<ArrayList<PhoneEntry>>() {
			}.getType();
			List<PhoneEntry> jsonPEntries = gson.fromJson(sb.toString(), listType);
			for (PhoneEntry pEntry : jsonPEntries) {
				addEntry(pEntry.getPhone(), pEntry);
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error process phonebook");
			throw new IOException("Could not process phonebook file");
		}
	}

	public void savePhoneBook(String fname) throws CustomException {
		try {
			Properties properties = new Properties();
			properties.load(ApplicationInitializer.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
			PrintWriter pw = new PrintWriter(properties.get(CONFIG_PHONEBOOK_FILES_PATH_KEY) + "/"
					+ (fname != null ? fname : UNLISTED_FILENAME));
			pw.write(gson.toJson(_pbook.values()));
			pw.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			throw new CustomException("Error saving phone book");
		}
	}

	public void editEntry(String phone, String fname, String lname) {
		PhoneEntry pentry = _pbook.get(phone);
		pentry.changeName(fname, lname);
	}

	public void addEntry(String fname, String lname, String phone) throws CustomException {
		addEntry(phone, new PhoneEntry(fname, lname, phone));
	}

	public void addEntry(String number, PhoneEntry entry) throws CustomException {
		if (!_pbook.containsKey(number))
			_pbook.put(number, entry);
		else
			throw new CustomException("Cannot add this PhoneEntry. Phone number already exists : " + number);
	}

	public PhoneEntry removeEntry(String phone) {
		return _pbook.remove(phone);
	}

	public PhoneEntry findEntry(String phone) {
		return _pbook.get(phone);
	}

	public String[] listEntries() {
		String[] rval = new String[_pbook.size()];
		int i = 0;
		PhoneEntry nextEntry = null;
		for (Iterator<PhoneEntry> iter = _pbook.values().iterator(); iter.hasNext();) {
			nextEntry = iter.next();
			rval[i++] = nextEntry.toString();
		}
		return rval;
	}

	public Map<String, PhoneEntry> get_pbook() {
		return _pbook;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

}
