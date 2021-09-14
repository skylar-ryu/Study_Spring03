package util;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

//** @ 종류
//=> @Before - @Test - @After
//=> @ 적용 메서드 : non static, void 로 정의 해야 함.

public class JDBCconTest {

	// 1) non static, void Test
		public void getConnectionOracle() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String url ="jdbc:oracle:thin:@127.0.0.1:1521:XE";
				DriverManager.getConnection(url,"system","oracle");
				System.out.println("** DB Connection 성공 **");  // green
			} catch (Exception e) {
				System.out.println("** DB Connection 실패 => "+e.toString()); 
				// green -> console 메세지 확인가능
			}
		} // dbConnectionOracle
		
	
	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//String url ="jdbc:oracle:thin:@127.0.0.1:1521:1522"; // 일부러 오류값 (실패:red)
			String url ="jdbc:oracle:thin:@127.0.0.1:1521:XE"; // 연결성공 (성공:green)
			return DriverManager.getConnection(url,"system","oracle");
		} catch (Exception e) {
			System.out.println("** DB Connection 실패 => "+e.toString()); 
			return null ;
		}
	} // dbConnection 
	
	@Test
	public void connectionTest() {
		System.out.println("** Connection => "+getConnection());
		assertNotNull(getConnection()) ; // 성공:green , 실패:red
	}
} //class
