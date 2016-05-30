package serviceImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import beans.User;
import dao.BaseDao;
import daoImpl.HibernateSessionFactory;
import daoImpl.UserDao;
import forms.UserForm;
import service.UserManager;

public class UserManagerImpl implements UserManager{

	private BaseDao dao;  
	  
    private Session session;  
  
    public UserManagerImpl() {  
        dao = new UserDao();  
    }  
  
    @Override  
    public void regUser(UserForm userForm) throws HibernateException {  
        session = HibernateSessionFactory.currentSession();  
//        dao.setSession(session);  
        // ��ȡ����  
        Transaction ts = session.beginTransaction();  
        // ����User����  
        User user = new User();  
        user.setUserName(userForm.getUserName());  
        user.setPassword(userForm.getPassword());  
        user.setGender(userForm.getGender());  
        // ����User����  
        dao.saveObject(user);  
        // �ύ����  
        ts.commit();  
        // �ر�Session  
        HibernateSessionFactory.closeSession();  
    }  

}
