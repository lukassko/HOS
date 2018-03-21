package com.app.hos.pojo;

public class UserChallenge {

	private String salt;
	
	private String challenge;

	private String hash;
	
	public String getSalt() {
		return salt;
	}

	public UserChallenge setSalt(String salt) {
		this.salt = salt;
		return this;
	}

	public String getChallenge() {
		return challenge;
	}

	public UserChallenge setChallenge(String challenge) {
		this.challenge = challenge;
		return this;
	}

	public String getHash() {
		return hash;
	}

	public UserChallenge setHash(String hash) {
		this.hash = hash;
		return this;
	}

}
