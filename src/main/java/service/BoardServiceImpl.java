package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.Criteria;
import criTest.SearchCriteria;
import util.BoardDAO;
import vo.BoardVO;
import vo.PageVO;

//** interface 자동완성 
// => Alt + Shift + T  
// => 또는 마우스우클릭 PopUp Menu 의  Refactor - Extract Interface...

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardDAO dao;
	
	// ** Check_BoardList
	@Override
	public List<BoardVO> checkList(BoardVO vo) {
		return dao.checkList(vo);
	}
	
	// ** Ajax idBoardList
	@Override
	public List<BoardVO> aidBList(BoardVO vo) {
		return dao.aidBList(vo);
	}
	
	// PageList2. => ver02 : SearchPageList--------------- 
	@Override
	public int searchRowsCount(SearchCriteria cri) {
		return dao.searchRowsCount(cri);
	}
	@Override
	public List<BoardVO> searchList(SearchCriteria cri) {
		return dao.searchList(cri);
	}
	//----------------------------------------------------- 
	
	// PageList2. => ver01 : criPageList --------------------------
	@Override
	public int totalRowsCount() {
		return dao.totalRowsCount();
	} //totalRowsCount
	
	@Override
	public List<BoardVO> criPList(Criteria cri) {
		return dao.criPList(cri);
	} //criPList
	//-------------------------------------------------------
	
	// PageList1.
	@Override
	public PageVO<BoardVO> pageList(PageVO<BoardVO> pvo) {
		return dao.pageList(pvo);
	} //pageList
	
	// 답글등록
	@Override
	public int replyInsert(BoardVO vo) {
		return dao.replyInsert(vo);
	} //replyInsert
	
	@Override
	public List<BoardVO> selectList() {
		return dao.selectList();
	} //selectList
	@Override
	public BoardVO selectOne(BoardVO vo) {
		return dao.selectOne(vo);
	} //selectList
	
	// ** 조회수 증가
	@Override
	public int countUp(BoardVO vo) {
		return dao.countUp(vo);
	} //countUp
	
	@Override
	public int insert(BoardVO vo) {
		return dao.insert(vo);
	} //insert
	@Override
	public int update(BoardVO vo) {
		return dao.update(vo);
	} //update
	@Override
	public int delete(BoardVO vo) {
		return dao.delete(vo);
	} //delete

} //class
