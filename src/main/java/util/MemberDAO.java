package util;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import criTest.SearchCriteria;
import vo.BoardVO;
import vo.MemberVO;
import vo.PageVO;

// ** DAO (Data Access Object)
// => CRUD

@Repository
public class MemberDAO {
	
	@Autowired // 스프링이제공 
	// @Inject : Java 에서 제공
	private SqlSession sqlSession;
	// SqlSession (Interface) -> SqlSessionTemplate (servl...xml 에 Bean 등록)
	// 스프링 컨테이너가 setSqlSessionFactory 메서드를 자동으로 호출하여 
	// 스프링 설정파일(..-context.xml)에 <bean> 등록된 SqlSessionFactoryBean 객체를 인자로 받아	
	// 부모인 	SqlSessionDaoSupport 에 setSqlSessionFactory() 메서드로 설정해줌.
	// 이렇게 함으로 SqlSessionDaoSupport 로부터 상속된 getSqlSession() 메서드를 호출하여
	// SqlSession 객체를 return 받을 수 있게됨.
	
	private static final String NS = "green.mapper.MemberMapper.";
	// => green.mapper.MemberMapper.pageList
	
	// ** Check_MemberList
	public List<MemberVO> checkList(MemberVO vo) {
		return sqlSession.selectList(NS+"checkList",vo);
	}
	
	// PageList2. => SearchPageList---------------
	// => mapper 에 searchRowsCount, searchList 추가
	public int searchRowsCount(SearchCriteria cri) {
		return sqlSession.selectOne(NS+"searchRowsCount",cri);
	}
	public List<MemberVO> searchList(SearchCriteria cri) {
		return sqlSession.selectList(NS+"searchList",cri);
	}
	//----------------------------------------------------- 
	
	// ** pageList1.
	public PageVO<MemberVO> pageList(PageVO<MemberVO> pvo) {
		// => 전체Row수(totalRowCount)
		pvo.setTotalRowCount(sqlSession.selectOne(NS+"totalRowCount"));
		// => 출력 Data 
		pvo.setList(sqlSession.selectList(NS+"pageList", pvo));
		return pvo;
	} //pageList
	
	// ** selectList
		public List<MemberVO> selectList() {
			return sqlSession.selectList(NS+"selectList");
		} //selectList

	// ** selectOne
	public MemberVO selectOne(MemberVO vo) {
		return sqlSession.selectOne(NS+"selectOne", vo);
	} //selectOne

	// ** Join : insert
	public int insert(MemberVO vo) {
		return sqlSession.insert(NS+"insert", vo);
	} //insert

	// ** update
	// => pkey 일반적으로 수정하지 않음.
	public int update(MemberVO vo) {
		return sqlSession.update(NS+"update", vo);
		
	} //update

	// ** delete
	public int delete(MemberVO vo) {
		return sqlSession.delete(NS+"delete",vo);
	} //delete
	
} //class
