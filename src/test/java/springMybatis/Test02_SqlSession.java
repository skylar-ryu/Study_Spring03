package springMybatis;

import static org.junit.Assert.assertNotNull;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*-context.xml" })
public class Test02_SqlSession {
	@Autowired
	private SqlSessionFactory sqlFactory ;
	// 계층도: SqlSessionFactory (interface)  
	// 		 -> FactoryBean<SqlSessionFactory> (interface) -> SqlSessionFactoryBean
	
	@Before()
	// import org.junit.Before;
	// SqlSessionFactory 생성 & 자동주입 확인 ->  sqlFactory is Not Null 
	public void sqlFactoryTest() {
		assertNotNull(sqlFactory); 
		System.out.println("\n** SqlSessionFactory => "+sqlFactory);
	}
	
	@Test
	// SqlSession -> 실제 DB 연결, Mapper의 Sql 구문을 이용해 DAO의 요청을 처리.
	// test1) 정상 의 경우 sqlSessionTest() 만 Test
	// test2) SqlSessionFactory 생성 안된 경우 Test
	// test2) @Before 적용  sqlFactoryTest() , sqlSessionTest() 모두 Test
	public void sqlSessionTest() {
		try {
			SqlSession sqlSession = sqlFactory.openSession();
			System.out.println("** SqlSession 성공 => "+sqlSession);
			// 계층도 : SqlSession (interface) -> SqlSessionTemplate
		} catch (Exception e) {
			System.out.println("sqlSessionTest Exception => "+e.toString());
		}
	} // sqlSessionTest() 
	
} // class
