package net.vitic.ddd.util;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

/**
 * https://stackoverflow.com/questions/12742826/how-do-i-know-th-id-before-saving-an-object-in-jpa
 */
public class JpaIdGenerator extends IdentityGenerator implements Configurable {

    private IdentifierGenerator defaultGenerator;

    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        //idValue will be assigned your entity id
        return defaultGenerator.generate(session, object);
    }


    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        JdbcEnvironment jdbcEnvironment = serviceRegistry.getService(JdbcEnvironment.class);
        Dialect dialect = jdbcEnvironment.getDialect();
        DefaultIdentifierGeneratorFactory dd = new DefaultIdentifierGeneratorFactory();
        dd.setDialect(dialect);
        defaultGenerator = dd.createIdentifierGenerator("sequence", type, properties);
    }
}
