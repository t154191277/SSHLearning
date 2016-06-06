package springjdbctemplate;


public interface IUserDao {
	public void save(UserModel model);
	public int countAll();
}
