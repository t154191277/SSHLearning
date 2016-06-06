package springjdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CallProcedureTest {
	
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
	public void testCallableStatementCreator3() {
	    final String callProcedureSql = "{call PROCEDURE_TEST(?, ?)}";
	    List<SqlParameter> params = new ArrayList<SqlParameter>();
	    params.add(new SqlInOutParameter("inOutName", Types.VARCHAR));
	    params.add(new SqlOutParameter("outId", Types.INTEGER));
	    Map<String, Object> outValues = jdbcTemplate.call(
	      new CallableStatementCreator() {
	        @Override
	        public CallableStatement createCallableStatement(Connection conn) throws SQLException {
	          CallableStatement cstmt = conn.prepareCall(callProcedureSql);
	          cstmt.registerOutParameter(1, Types.VARCHAR);
	          cstmt.registerOutParameter(2, Types.INTEGER);
	          cstmt.setString(1, "test");
	          return cstmt;
	    }}, params);
	    Assert.assertEquals("Hello,test", outValues.get("inOutName"));
	    Assert.assertEquals(0, outValues.get("outId"));
	}
	 
}
