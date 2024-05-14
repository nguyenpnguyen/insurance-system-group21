module org.group21.insurance {
	requires javafx.controls;
	requires jakarta.persistence;
	requires org.hibernate.orm.core;
	exports org.group21.insurance;
	exports org.group21.insurance.views;
	exports org.group21.insurance.controllers;
	exports org.group21.insurance.authentication;
	exports org.group21.insurance.utils;
	exports org.group21.insurance.models;
	opens org.group21.insurance.models;
}
