package edu.asupoly.ser422.lab3.service;

import static edu.asupoly.ser422.lab3.utils.Utils.EXTENSION;
import static edu.asupoly.ser422.lab3.utils.Utils.UNLISTED_FILENAME;

import java.util.List;
import java.util.stream.Collectors;

import edu.asupoly.ser422.lab3.exception.CustomException;
import edu.asupoly.ser422.lab3.model.PhoneBook;
import edu.asupoly.ser422.lab3.model.PhoneEntry;

public class PhoneEntryService {

	private PhoneBookService pbookService = new PhoneBookService();

	public PhoneEntry getPhoneEntryByPhoneNumber(String pbookName, String phone) throws CustomException {
		PhoneBook pbook = pbookService.getPhoneBook(pbookName);
		return pbook.findEntry(phone);
	}

	public PhoneEntry createPhoneEntry(PhoneEntry pEntry) throws CustomException {
		PhoneBook pbook = pbookService.getPhoneBook(UNLISTED_FILENAME);
		pbook.addEntry(pEntry.getPhone(), pEntry);
		pbook.savePhoneBook(UNLISTED_FILENAME);
		return pEntry;
	}

	public boolean movePhoneEntry(String phoneNumber, String pbookName) throws CustomException {
		pbookName = pbookName.contains(EXTENSION) ? pbookName : pbookName + EXTENSION;
		List<PhoneBook> pbooks = pbookService.getPhoneBooks();
		PhoneEntry targetpEntry = null;
		PhoneBook targetpBook = null;
		for (PhoneBook pbook : pbooks) {
			if (pbook.getFname() != null && pbook.getFname().equals(pbookName)) {
				targetpBook = pbook;
			}
			if (pbook.get_pbook().containsKey(phoneNumber)) {
				targetpEntry = pbook.findEntry(phoneNumber);
				pbook.removeEntry(phoneNumber);
				pbook.savePhoneBook(pbook.getFname());
			}
		}

		if (targetpBook != null && targetpEntry != null) {
			targetpBook.addEntry(phoneNumber, targetpEntry);
			targetpBook.savePhoneBook(pbookName);
		} else {
			throw new CustomException("Phone number or phone book name is invalid");
		}
		return true;
	}

	public PhoneEntry updatePhoneNumber(String oldPhone, String newPhone) throws CustomException {
		List<PhoneBook> pbooks = pbookService.getPhoneBooks();
		PhoneEntry targetpEntry = null;
		for (PhoneBook pbook : pbooks) {
			if (pbook.get_pbook().containsKey(newPhone)) {
				throw new CustomException("Phone entry with phone number = " + newPhone
						+ " already exists in phone book : " + pbook.getFname());
			}
			if (pbook.get_pbook().containsKey(oldPhone)) {
				targetpEntry = pbook.findEntry(oldPhone);
				pbook.removeEntry(oldPhone);
				targetpEntry.setPhone(newPhone);
				pbook.addEntry(newPhone, targetpEntry);
				pbook.savePhoneBook(pbook.getFname());
			}
		}
		if (targetpEntry == null) {
			throw new CustomException("No phone entry found with the phone number : " + oldPhone);
		}
		return targetpEntry;
	}

	public boolean deletePhoneEntry(String phone) throws CustomException {
		List<PhoneBook> pbooks = pbookService.getPhoneBooks();
		boolean foundMatch = false;
		for (PhoneBook pbook : pbooks) {
			if (pbook.get_pbook().containsKey(phone)) {
				pbook.removeEntry(phone);
				pbook.savePhoneBook(pbook.getFname());
				foundMatch = true;
			}
		}
		if (!foundMatch)
			throw new CustomException("No phone entry found");
		return foundMatch;
	}

	public List<PhoneEntry> findByCriteria(String pbookName, String lastname, int areaCode) throws CustomException {
		PhoneBook pbook = pbookService.getPhoneBook(pbookName);
		return pbook.get_pbook().values().parallelStream().filter(pentry -> {
			return (lastname != null ? pentry.getLastname().contains(lastname) : true)
					&& Integer.parseInt(pentry.getPhone().substring(0, 3)) == areaCode;
		}).collect(Collectors.toList());

	}

}
