package util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import criTest.Criteria;
import criTest.SearchCriteria;
import lombok.extern.log4j.Log4j;
import vo.BoardVO;
import vo.PageVO;

// ** Board CRUD
// => selectList, selectOne, insert, update, delete 

@Log4j
@Repository
public class BoardDAO {
	@Autowired
	SqlSession sqlSession;
	private static final String NS="green.mapper.BoardMapper.";
	
	// ** Check_BoardList
	public List<BoardVO> checkList(BoardVO vo) {
		return sqlSession.selectList(NS+"checkList",vo);
	}
	
	// ** Ajax idBoardList
	public List<BoardVO> aidBList(BoardVO vo) {
		return sqlSession.selectList(NS+"aidBList",vo);
	}
	
	// PageList2. => ver02 : SearchPageList---------------
	// => mapper 에 searchRowsCount, searchList 추가
	public int searchRowsCount(SearchCriteria cri) {
		return sqlSession.selectOne(NS+"searchRowsCount",cri);
	}
	public List<BoardVO> searchList(SearchCriteria cri) {
		return sqlSession.selectList(NS+"searchList",cri);
	}
	//----------------------------------------------------- 
	
	// PageList2. => ver01 : criPageList ------------------------
	public int totalRowsCount() {
		return sqlSession.selectOne(NS+"totalRowCount");
	} //totalRowsCount
	
	public List<BoardVO> criPList(Criteria cri) {
		return sqlSession.selectList(NS+"pageList",cri);
	} //criPList
	//---------------------------------------------------
	
	// ** PageList 1.
	public PageVO<BoardVO> pageList(PageVO<BoardVO> pvo) {
		// ** 전체Row수(totalRowCount)
		pvo.setTotalRowCount(sqlSession.selectOne(NS+"totalRowCount")); 
		// ** List 읽기
		pvo.setList(sqlSession.selectList(NS+"pageList",pvo));
		return pvo;
	} //pageList() 
	
	// ** 답글등록
	public int replyInsert(BoardVO vo) {
		log.info("** Step_Update Count => "+sqlSession.update(NS+"stepUpdate",vo));
		return sqlSession.insert(NS+"replyInsert",vo);
	} //replyInsert
	
	// ** selectList
	public List<BoardVO> selectList() {
		return sqlSession.selectList(NS+"selectList");
	} //selectList
	
	// ** selectOne
	public BoardVO selectOne(BoardVO vo) {
		return sqlSession.selectOne(NS+"selectOne", vo);
	} //selectOne 
	
	// ** 조회수 증가
	public int countUp(BoardVO vo) {
		return sqlSession.update(NS+"countUp",vo);
	} //countUp
	
	// ** insert (원글)
	public int insert(BoardVO vo) {
		return sqlSession.insert(NS+"insert",vo);
	} //insert
	// ** update
	public int update(BoardVO vo) {
		return sqlSession.update(NS+"update",vo);
	} //update
	// ** delete
	public int delete(BoardVO vo) {
		return sqlSession.delete(NS+"delete",vo);
	} //delete
} //class
