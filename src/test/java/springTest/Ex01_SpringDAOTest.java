package springTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javaTest.MemberDAO;
import vo.MemberVO;

//** DAOTest Spring Version
//=> 설정화일(~.xml) 을  사용
//	-> 테스트코드 실행시에 설정파일을 이용해서 스프링이 로딩 되도록 해줌
//	-> @RunWith(스프링 로딩) , @ContextConfiguration (설정파일 등록)

//=> IOC/DI Test
//=> 공통적으로 사용하는 MemberDAO dao 인스턴스를 전역으로 정의
//=> 자동 주입 받기 ( xml_root-context.xml , @ )

//** import 제대로 안되고 오류발생시 Alt+f5 눌러 Maven Update 한다.
//=> 메뉴 : Project 우클릭 - Maven - Update Project .. 
// ( 하기전 주의사항은 pom.xml 의  <plugin> <configuration> 의 
//   <source>1.8</source> 와 <target> Java 버전 확인 )

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class Ex01_SpringDAOTest {
	@Autowired
	MemberDAO dao; 
	// javaTest.MemberDAO , 생성은 설정화일로 (root~~.xml 이용)
	@Autowired
	MemberVO vo;   // vo.MemberVO 
	
	//test1) Detail 정확하게 구현 했는지 Test
	// => 존재하는 id (NotNull green) , 없는 id (Null red) 결과 확인 
	public void datailTest( ) {
		//1.1) DAO 자동주입 확인 
		System.out.println("** DAO 자동주입 확인 => "+dao);
		assertNotNull(dao);  // Null:red, NotNull:green
		
		//1.2) Detail 확인
		//vo.setId("banana"); // green
		vo.setId("banana1234"); // red
		assertNotNull(dao.selectOne(vo));
	}
	@Test
	//test2) Insert 구현 정확성 Test
	// => 2회 실행 ( 1회는 green, 2회부터는 red -> ID 중복되므로 )
	public void insertTest() {
		vo.setId("unitTest");
		vo.setPassword("12345!");
		vo.setName("가나다");
		vo.setLev("A");
		vo.setBirthd("1999-10-09");
		vo.setPoint(1000);
		vo.setWeight(44.33);
		vo.setRid("apple");
		vo.setUploadfile("resources/uploadImage/basicman2.jpg");
		// => 성공: 1 , 실패 : 0
		assertEquals(dao.insert(vo), 1); // true:green , false:red
	}

	
	
	
	
	
	
	

} //class
