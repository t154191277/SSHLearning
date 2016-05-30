package daoImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import dao.BaseDao;

public class UserDao implements BaseDao{
	
	private Session session;

	@Override
	public void saveObject(Object obj) throws HibernateException {
		session.save(obj);
	}


}
