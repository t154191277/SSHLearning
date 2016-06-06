package springjdbctemplate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;


public class UserJdbcDaoImpl extends SimpleJdbcDaoSupport implements IUserDao{

	private static final String INSERT_SQL = "insert into test values(:name)";
	private static final String COUNT_ALL_SQL = "select count(*) from test";
	
	@Override
	public void save(UserModel model) {
		getSimpleJdbcTemplate().update(INSERT_SQL, new BeanPropertySqlParameterSource(model));
	}

	@Override
	public int countAll() {
		return getSimpleJdbcTemplate().queryForInt(COUNT_ALL_SQL);
	}

}
