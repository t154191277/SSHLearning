package springjdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CallFunctionTest {
	
	private static DriverManagerDataSource dataSource;
	
	@Before
	public void setUpClass(){
		String url = "jdbc:mysql://localhost:3306/wmlove?useSSL=true";
		String username = "wmlove";
		String pwd = "aabb5459600";
		DriverManagerDataSource dataSource = new DriverManagerDataSource(url,username,pwd);
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	}
	
	@Test
	public void testCallableStatementCreator2() {
		JdbcTemplate mysqlJdbcTemplate = new JdbcTemplate(dataSource);
		    //2.创建自定义函数
		String createFunctionSql =
		    "CREATE FUNCTION FUNCTION_TEST(str VARCHAR(100)) " +
		     "returns INT return LENGTH(str)";
		String dropFunctionSql = "DROP FUNCTION IF EXISTS FUNCTION_TEST";
		mysqlJdbcTemplate.update(dropFunctionSql);       
		mysqlJdbcTemplate.update(createFunctionSql);
		//3.准备sql,mysql支持{?= call …}
		final String callFunctionSql = "{?= call FUNCTION_TEST(?)}";
		//4.定义参数
		List<SqlParameter> params = new ArrayList<SqlParameter>();
		params.add(new SqlOutParameter("result", Types.INTEGER));
		params.add(new SqlParameter("str", Types.VARCHAR));
		Map<String, Object> outValues = mysqlJdbcTemplate.call(
		new CallableStatementCreator() {
		    @Override
		    public CallableStatement createCallableStatement(Connection conn) throws SQLException {
		      CallableStatement cstmt = conn.prepareCall(callFunctionSql);
		      cstmt.registerOutParameter(1, Types.INTEGER);
		      cstmt.setString(2, "test");
		        return cstmt;
		    }}, params);
		   Assert.assertEquals(4, outValues.get("result"));
		}
		public DataSource getMysqlDataSource() {
		    String url = "jdbc:mysql://localhost:3306/test";
		    DriverManagerDataSource dataSource =
		        new DriverManagerDataSource(url, "root", "");     dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		    return dataSource;
	}
	 
}
