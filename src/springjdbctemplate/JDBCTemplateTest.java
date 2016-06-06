package springjdbctemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
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
	
//	@Test
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
	
//	@Test
	public void testPreparedStatement(){
		int count = jdbcTemplate.execute(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				return conn.prepareStatement("select count(*) from test");
			}
		}, new PreparedStatementCallback<Integer>() {

			@Override
			public Integer doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.execute();
				ResultSet rs = ps.getResultSet();
				rs.next();
				return rs.getInt(1);
			}
		});
		Assert.assertEquals(1, count);
	}
	
//	@Test
	public void testPreparedStatement2(){
		String insertSql = "insert into test values (?)";
		int count = jdbcTemplate.update(insertSql,new PreparedStatementSetter(){

			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setObject(1, "user4");
			}
		});
		Assert.assertEquals(1, count);
		String deleteSql = "delete from test where name = ?";
		count = jdbcTemplate.update(deleteSql,new Object[] {"user4"});
		Assert.assertEquals(1, count);
	}
	
//	@Test
	public void testResult1(){
		jdbcTemplate.update("insert into test values('user5')");
		String listSql = "select * from test";
		List result =  jdbcTemplate.query(listSql, new RowMapper<Map>(){

			@Override
			public Map mapRow(ResultSet rs, int i) throws SQLException {
				Map row = new HashMap();
				row.put(1, rs.getString("name"));
				return row;
			}
			
		});
		Assert.assertEquals(5, result.size());
		jdbcTemplate.update("delete from test where name = 'user5'");
	}
	
//	@Test
	public void testResult2(){
		jdbcTemplate.update("insert into test values('user5')");
		String listSql = "select * from test";
		final List result = new ArrayList<>();
		jdbcTemplate.query(listSql, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Map row = new HashMap();
				row.put(1, rs.getString("name"));
				result.add(row);
			}
		});
		Assert.assertEquals(4, result.size());
		jdbcTemplate.update("delete from test where name = 'user5'");
	}
	
//	@Test
	public void testResultSet3(){
		jdbcTemplate.update("insert into test values('user5')");
		String listSql = "select * from test";
		List result = jdbcTemplate.query(listSql, new ResultSetExtractor<List>() {

			@Override
			public List extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				List result = new ArrayList();
				int i = 0;
				while(rs.next()){
					Map row = new HashMap<>();
					row.put(++i, rs.getString("name"));
					result.add(row);
				}
				return result;
			}
		});
		Assert.assertEquals(3, result.size());
		jdbcTemplate.update("delete from test where name = 'user5'");
	}
	
	@Test
	public void testBestPractice(){
		 String[] configLocations = new String[] {  
		            "classpath:springResources.xml",  
		            "classpath:springJdbc.xml"};  
		    ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocations);  
		    IUserDao userDao = ctx.getBean(IUserDao.class);  
		    UserModel model = new UserModel();  
		    model.setName("test");  
		    userDao.save(model);  
		    Assert.assertEquals(5, userDao.countAll());  
	}
}

