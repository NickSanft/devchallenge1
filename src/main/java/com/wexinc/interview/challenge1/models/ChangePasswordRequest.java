package com.wexinc.interview.challenge1.models;

public class ChangePasswordRequest {
	private int userId;
	private String authToken;
	private String currentPassword;
	private String newPassword;
	private String verifyPassword;

	public ChangePasswordRequest(int userId, String authToken, String currentPassword, String newPassword,
			String verifyPassword) {
		super();
		this.userId = userId;
		this.authToken = authToken;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.verifyPassword = verifyPassword;
	}

	public int getUserId() {
		return userId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public String getVerifyPassword() {
		return verifyPassword;
	}
}
