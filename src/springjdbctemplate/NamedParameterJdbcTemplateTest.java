package springjdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class NamedParameterJdbcTemplateTest {
	
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
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;  
		//namedParameterJdbcTemplate =  
//		    new NamedParameterJdbcTemplate(dataSource);  
		namedParameterJdbcTemplate =  
		new NamedParameterJdbcTemplate(jdbcTemplate);  
		String insertSql = "insert into test(name) values(:name)";  
	    String selectSql = "select * from test where name=:name";  
	    String deleteSql = "delete from test where name=:name";  
	    Map<String, Object> paramMap = new HashMap<String, Object>();  
	    paramMap.put("name", "name5");  
	    namedParameterJdbcTemplate.update(insertSql, paramMap);  
	    final List<String> result = new ArrayList<String>();  
	    namedParameterJdbcTemplate.query(selectSql, paramMap,  
	    		new RowCallbackHandler() {  
	        		@Override  
	        		public void processRow(ResultSet rs) throws SQLException {  
	        			result.add(rs.getString("name"));  
	        		}  
	    	});      
		Assert.assertEquals(3, result.size());  
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);  
		namedParameterJdbcTemplate.update(deleteSql, paramSource);
	}
}
