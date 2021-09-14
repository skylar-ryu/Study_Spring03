package springMybatis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*-context.xml" })
public class Test03_Mapper {
	
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private MemberVO vo;
	private static final String NS="green.mapper.MemberMapper." ;
	
	// ** Mapper의 각 sql 구문 동작 Test 
	// test1)
	public void selectOne() {
		vo.setId("unitTest");
		// 존재하는 id 사용시 해당 Row return
		// 없는 id 사용시 null return
		vo=sqlSession.selectOne(NS+"selectOne", vo);
		System.out.println(" **** "+vo);
		assertNotNull(vo);
	}
	
	// test2) 
	public void totalRowCountTest() {
		int count = sqlSession.selectOne(NS+"totalRowCount") ;
		System.out.println("** Member 전체 Record count : "+count);
	} // totalRowCountTest()

	// test3)  Insert
	public void insertTest() {
		vo.setId("junitS");
		vo.setPassword("12345!");
		vo.setName("유니트");
		vo.setLev("A");
		vo.setBirthd("1909-09-09");
		vo.setPoint(1000);
		vo.setWeight(33.44);
		vo.setRid("test");
		vo.setUploadfile("test");
		// 입력 성공 1 return
		// => 처리 결과가 1 과 같은지 비교하여 성공 / 실패
		assertEquals(sqlSession.insert(NS+"insert", vo), 1);
	} // insertTest()

	@Test
	// test4) Delete
	public void deleteTest() {
		vo.setId("unitTest");
		assertEquals(sqlSession.delete(NS+"delete", vo), 1);
	}
} // class
