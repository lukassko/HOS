package com.app.hos.persistance.custom;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class DateTimeUserType implements UserType {

	@Override
	public Object assemble(Serializable cached, Object value) throws HibernateException {
		if (!(value instanceof DateTime))            
			throw new UnsupportedOperationException("Can't convert " + value.getClass());  
		long timestamp = ((DateTime)value).getTimestamp();
		return new DateTime(timestamp);
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		if (!(value instanceof DateTime))            
			throw new UnsupportedOperationException("Can't convert " + value.getClass());    
		return (Serializable) value;
	}

	@Override
	public boolean equals(Object object1, Object object2) throws HibernateException {
		return (object1 == object2) || ((object1 != null) && (object2 != null) && (object1.equals(object2)));
	}

	@Override
	public int hashCode(Object object) throws HibernateException {
		return object.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		Timestamp date = rs.getTimestamp(names[0]); 
		if (date == null) {
			return null;
		}
		return new DateTime(date.getTime());
	}

	@Override
	public void nullSafeSet(PreparedStatement ps, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		if (value==null) {
			ps.setNull(index, Types.DATE);
            return;
        }
		if (!(value instanceof DateTime))
            throw new UnsupportedOperationException("Can't convert " + value.getClass());
		
		long timestamp = ((DateTime)value).getTimestamp();
		ps.setTimestamp(index, new Timestamp(timestamp));
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass() {
		return DateTime.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] {Types.TIMESTAMP};
	}

}
