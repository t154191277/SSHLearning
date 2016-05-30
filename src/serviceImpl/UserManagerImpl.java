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
        // 获取事务  
        Transaction ts = session.beginTransaction();  
        // 构造User对象  
        User user = new User();  
        user.setUserName(userForm.getUserName());  
        user.setPassword(userForm.getPassword());  
        user.setGender(userForm.getGender());  
        // 保存User对象  
        dao.saveObject(user);  
        // 提交事务  
        ts.commit();  
        // 关闭Session  
        HibernateSessionFactory.closeSession();  
    }  

}
