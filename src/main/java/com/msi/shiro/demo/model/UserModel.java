package com.msi.shiro.demo.model;

public class UserModel {

	private Long objectId;
	private String userName;
	private String lastName;
	private String firstName;

	public UserModel() {

		super();
	}

	public Long getObjectId() {

		return objectId;
	}

	public void setObjectId(Long objectId) {

		this.objectId = objectId;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getLastName() {

		return lastName;
	}

	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	public String getFirstName() {

		return firstName;
	}

	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

}
