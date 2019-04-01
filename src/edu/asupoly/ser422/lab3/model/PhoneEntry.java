package edu.asupoly.ser422.lab3.model;

import java.io.Serializable;

public class PhoneEntry implements Serializable {

	private static final long serialVersionUID = -1677170407015487765L;
	private String firstname;
	private String lastname;
	private String phone;

	public PhoneEntry(String name, String lname, String phone) {
		this.firstname = name;
		this.lastname = lname;
		this.phone = phone;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void changeName(String newfname, String newlname) {
		firstname = newfname;
		// This is here to introduce artifical latency for testing purposes
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lastname = newlname;
	}

	public String toString() {
		return firstname + "\n" + lastname + "\n" + phone;
	}
}
