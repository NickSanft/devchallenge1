package com.wexinc.interview.challenge1.controllers;

import static com.wexinc.interview.challenge1.util.JsonUtil.json;
import static spark.Spark.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.wexinc.interview.challenge1.AuthorizationException;
import com.wexinc.interview.challenge1.models.ChangePasswordRequest;
import com.wexinc.interview.challenge1.services.AuthManager;
import com.wexinc.interview.challenge1.util.Path;

import spark.Request;
import spark.Response;
import spark.Route;

public class ChangePasswordController {
	private AuthManager authManager;
	private Logger logger;

	@Inject
	public ChangePasswordController(AuthManager authManager) {
		if (authManager == null)
			throw new IllegalArgumentException("AuthManager cannot be null");

		this.authManager = authManager;

		logger = LoggerFactory.getLogger(getClass());

		logger.info("Starting ChangePasswordController");

		post(Path.ChangePass, changePassword, json());
	}

	private Route changePassword = (Request req, Response resp) -> {
		final ChangePasswordRequest changePasswordRequest = new Gson().fromJson(req.body(),
				ChangePasswordRequest.class);

		if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getVerifyPassword())) {
			resp.status(400);
			return "New passwords do not match";
		}

		if (!authManager.validatePassword(changePasswordRequest.getUserId(),
				changePasswordRequest.getCurrentPassword())) {
			resp.status(403);
			return "Current password is not correct!";
		}

		try {
			authManager.verifyAuthToken(changePasswordRequest.getAuthToken());
		} catch (AuthorizationException e) {
			resp.status(403);
			return "Auth token was not valid!";
		}

		if (authManager.changePassword(changePasswordRequest.getUserId(), changePasswordRequest.getNewPassword())) {
			resp.status(200);
			return "Password change successful!";
		} else {
			// Not sure how this would happen :)
			resp.status(404);
			return "Password change NOT successful!";
		}
	};

}
