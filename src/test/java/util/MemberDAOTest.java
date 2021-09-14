package util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javaTest.MemberDAO;
import vo.MemberVO;

public class MemberDAOTest {
	MemberDAO dao = new MemberDAO();
	MemberVO vo = new MemberVO();
	
	@Test
	// ** Detail 정확하게 구현 했는지 Test
	// => 존재하는 id (검색된 Row Return) (NotNull green)
	// => 없는 id (존재하지 않는 자료) (Null red) 
	public void detailTest( ) {
		//vo.setId("banana"); // green
		vo.setId("banana1234"); // red
		assertNotNull(dao.selectOne(vo));
	}
} //class
