package com.app.hos.pojo;

public class UserHash {

	private String salt;
	
	private String challenge;

	private String hash;
	
	public String getSalt() {
		return salt;
	}

	public UserHash setSalt(String salt) {
		this.salt = salt;
		return this;
	}

	public String getChallenge() {
		return challenge;
	}

	public UserHash setChallenge(String challenge) {
		this.challenge = challenge;
		return this;
	}

	public String getHash() {
		return hash;
	}

	public UserHash setHash(String hash) {
		this.hash = hash;
		return this;
	}

}
