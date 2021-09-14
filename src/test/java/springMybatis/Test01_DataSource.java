package springMybatis;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*-context.xml" })
public class Test01_DataSource {
	@Autowired
	private DataSource ds;
	// 계층도 확인 ( Ctrl+T )
	// => DataSource (interface)
	// -> AbstractDataSource
	// -> AbstractDriverBasedDataSource
	// -> DriverManagerDataSource 
	//    org.springframework.jdbc.datasource.DriverManagerDataSource
		
	@Test
	// ds 주입성공 여부 & 생성 확인
	public void connectionTest(){
		try {
			Connection cn = ds.getConnection();
			System.out.println("** DB 연결 성공 **");
		} catch (Exception e) {
			System.out.println("** DB 연결 실패 **");
			System.out.println("Exception => "+e.toString());
		}
	} // connectionTest()
} // class
