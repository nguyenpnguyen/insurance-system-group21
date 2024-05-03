package org.group21.insurance.daos;

import java.util.Optional;
import java.util.List;

interface Dao<T> {
	Optional<T> get(String id);
	List<T> getAll();
	void save(T t);
	void update(T t);
	void delete(T t);
}
