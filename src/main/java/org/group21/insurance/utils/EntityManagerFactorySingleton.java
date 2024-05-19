package org.group21.insurance.utils;

/**
 * @author Group 21
 */

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactorySingleton {
	
	private static EntityManagerFactory entityManagerFactory;
	
	private static final String PERSISTENCE_UNIT_NAME = "insurance";
	
	// Private constructor to prevent instantiation
	private EntityManagerFactorySingleton() {
	}
	
	public static synchronized EntityManagerFactory getInstance() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return entityManagerFactory;
	}
	
	public static void close() {
		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}
}
