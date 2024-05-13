package org.group21.insurance.controllers;

interface ControllerTest<T> {
	void create();
	void read();
	void readAll();
	void update();
	void delete();
	T createAndPersist();
}
