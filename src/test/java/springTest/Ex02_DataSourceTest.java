package springTest;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//*** DataSourceTest
//=> pom.xml 에 <dependency> spring-jdbc 추가
//=> 인터페이스 DataSource 구현객체 DriverManagerDataSource 를 bean 등록하고 (servlet~.xml 또는 root~.xml 에)
//=> DB Connection 생성 확인

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class Ex02_DataSourceTest {
	@Autowired
	DataSource ds;
	// 계층도 확인 ( Ctrl+T )
	// => DataSource (interface)
	// -> AbstractDataSource
	// -> AbstractDriverBasedDataSource
	// -> DriverManagerDataSource 
	//    org.springframework.jdbc.datasource.DriverManagerDataSource
	@Test
	public void connectionTest() {
		try {
			Connection cn = ds.getConnection();
			System.out.println("~~ DB 연결 성공 => "+cn);
		} catch (Exception e) {
			System.out.println("connectionTest() Exception => "+e.toString());
			System.out.println("~~ DB 연결 실패 ~~");
		}
	} //connectionTest()
} //class
