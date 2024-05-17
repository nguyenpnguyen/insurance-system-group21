module org.group21.insurance {
	requires javafx.controls;
	requires jakarta.persistence;
	requires org.hibernate.orm.core;
	requires google.api.client;
	requires google.api.services.drive.v3.rev197;
	requires com.google.api.client;
	requires com.google.api.client.auth;
	requires com.google.api.client.json.gson;
	requires com.google.auth;
	requires com.google.auth.oauth2;
	exports org.group21.insurance;
	exports org.group21.insurance.views;
	exports org.group21.insurance.controllers;
	exports org.group21.insurance.authentication;
	exports org.group21.insurance.utils;
	exports org.group21.insurance.models;
	exports org.group21.insurance.exceptions;
	opens org.group21.insurance.models;
}
