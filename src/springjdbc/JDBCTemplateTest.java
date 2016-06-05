package springjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JDBCTemplateTest {
	
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
	public void test(){
		String sql = "select * from test";
		jdbcTemplate.query(sql,new RowCallbackHandler(){

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Assert.assertNotNull(rs);
				String value = (String) rs.getObject("name");
				System.out.println("" + value);
			}
		});
	}
	
//	@Test
	public void testCURD(){
		insert();
		delete();
		select();
		update();
	}

	private void update() {
		jdbcTemplate.update("update test set name = 'user3' where name = ?", new Object[]{"user1"});
		Assert.assertEquals(1, jdbcTemplate.queryForInt("select count(*) from test where name = 'user3'"));
	}

	private void select() {
		jdbcTemplate.query("select * from test", new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet as) throws SQLException {
				System.out.println("name: " + as.getString("name") );
			}
		});
	}

	private void delete() {
		jdbcTemplate.update("delete from test where name = ?",new Object[]{"user2"});
		Assert.assertEquals(1, jdbcTemplate.queryForInt("select count(*) from test"));
	}

	private void insert() {
		jdbcTemplate.update("insert into test values('user2')");
		jdbcTemplate.update("insert into test values('user1')");
		Assert.assertEquals(2, jdbcTemplate.queryForInt("select count(*) from test"));
	}
}
