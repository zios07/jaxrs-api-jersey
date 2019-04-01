package edu.asupoly.ser422.lab3.model;

import static edu.asupoly.ser422.lab3.utils.Utils.DEFAULT_FILENAME;

import java.io.BufferedReader;
import java.io.FileOutputStream;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class PhoneBook implements Serializable {

	private static final long serialVersionUID = -2134102172191011457L;

	private Map<String, PhoneEntry> _pbook = new HashMap<String, PhoneEntry>();

	public PhoneBook() throws IOException {
		this(DEFAULT_FILENAME);
	}

	public PhoneBook(String fname) throws IOException {
		this(PhoneBook.class.getClassLoader().getResourceAsStream(fname));
	}

	public PhoneBook(InputStream is) throws IOException {
		this(new BufferedReader(new InputStreamReader(is)));
	}

	private PhoneBook(BufferedReader br) throws IOException {
		Gson gson = null;
		try {
			StringBuilder sb = new StringBuilder();

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			gson = new GsonBuilder().setPrettyPrinting().create();
			Type listType = new TypeToken<ArrayList<PhoneEntry>>(){}.getType();
			List<PhoneEntry> jsonPEntries = gson.fromJson(sb.toString(), listType);
			jsonPEntries.stream().forEach(pEntry -> {
				addEntry(pEntry.getPhone(), pEntry);
			});
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error process phonebook");
			throw new IOException("Could not process phonebook file");
		}
	}

	public void savePhoneBook(String fname) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(fname));
			String[] entries = listEntries();
			for (int i = 0; i < entries.length; i++)
				pw.println(entries[i]);

			pw.close();
		} catch (Exception exc) {
			exc.printStackTrace();
			System.out.println("Error saving phone book");
		}
	}

	public void editEntry(String phone, String fname, String lname) {
		PhoneEntry pentry = _pbook.get(phone);
		pentry.changeName(fname, lname);
	}

	public void addEntry(String fname, String lname, String phone) {
		addEntry(phone, new PhoneEntry(fname, lname, phone));
	}

	public void addEntry(String number, PhoneEntry entry) {
		_pbook.put(number, entry);
	}

	public PhoneEntry removeEntry(String phone) {
		return _pbook.remove(phone);
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

}
