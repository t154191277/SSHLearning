package action;

import service.UserManager;
import serviceImpl.UserManagerImpl;

import com.opensymphony.xwork2.ActionSupport;

import forms.UserForm;

public class RegisterAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private UserForm user;
	
	private UserManager userManager;
	
	

	public RegisterAction() {
		super();
	}

	public UserForm getUser() {
		return user;
	}

	public void setUser(UserForm user) {
		this.user = user;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String execute(){
		try{
			this.setUserManager(new UserManagerImpl());
			userManager.regUser(user);
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
		}
		return ERROR;
	}
	
}
