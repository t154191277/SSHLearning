package springjdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SimpleJdbcTemplateTest {
	
	private static JdbcTemplate jdbcTemplate;
	
	@Before
	public void setUpClass(){
		String url = "jdbc:mysql://localhost:3306/wmlove?useSSL=true";
		String username = "wmlove";
		String pwd = "aabb5459600";
		DriverManagerDataSource dataSource = new DriverManagerDataSource(url,username,pwd);
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Test
	public void testSimpleJdbcTemplate() {
	    //还支持DataSource和NamedParameterJdbcTemplate作为构造器参数
	    SimpleJdbcTemplate simpleJdbcTemplate = new SimpleJdbcTemplate(jdbcTemplate);
	    String insertSql = "insert into test values(?)";
	    simpleJdbcTemplate.update(insertSql, "name5");
	    String selectSql = "select * from test where name=?";
	    List<Map<String, Object>> result = simpleJdbcTemplate.queryForList(selectSql, "name5");
	    Assert.assertEquals(1, result.size());    
	    RowMapper<UserModel> mapper = new UserRowMapper();      
	    List<UserModel> result2 = simpleJdbcTemplate.query(selectSql, mapper, "name5");
	    Assert.assertEquals(1, result2.size());   
	}
	
	class UserRowMapper implements RowMapper<UserModel>{

		@Override
		public UserModel mapRow(ResultSet rs, int arg1) throws SQLException {
			UserModel model = new UserModel();  
	        model.setName(rs.getString("name"));  
	        return model;
		}
		
	}
}

