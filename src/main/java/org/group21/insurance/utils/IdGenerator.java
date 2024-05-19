package org.group21.insurance.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * <p>
 * Class used to generate unique identifiers for entities.
 * </p>
 *
 * @author Group 21
 * @see <a href="https://www.baeldung.com/hibernate-identifiers">Baeldung</a>
 */
public class IdGenerator implements IdentifierGenerator, Configurable {
	private String prefix;
	private int length;
	
	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
		prefix = params.getProperty("prefix");
		length = Integer.parseInt(params.getProperty("length"));
	}
	
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		String query = String.format("select %s from %s",
				session.getEntityPersister(object.getClass().getName(), object)
						.getIdentifierPropertyName(),
				object.getClass().getSimpleName()
		);
		
		Stream<String> ids = session.createQuery(query).stream();
		
		long maxId = ids.map(o -> o.replaceAll("\\D", ""))
				.mapToLong(Long::parseLong)
				.max()
				.orElse(0L);
		
		String idWithoutPrefix = String.format("%0" + (length - prefix.length()) + "d", maxId + 1);
		return prefix + "-" + object.getClass().getSimpleName().toLowerCase().charAt(0) + "-" + idWithoutPrefix;
	}
}
